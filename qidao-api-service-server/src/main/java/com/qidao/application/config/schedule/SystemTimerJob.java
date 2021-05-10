package com.qidao.application.config.schedule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.qidao.application.entity.config.LogOperExample;
import com.qidao.application.entity.log.LogMemberLoginExample;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.msg.MsgRecordExample;
import com.qidao.application.mapper.config.LogOperMapper;
import com.qidao.application.mapper.log.LogMemberLoginMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.msg.MsgRecordMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.sign.MemberInfoDTO;
import com.qidao.application.model.msg.listen.QidaoSchedulingConfigurer;
import com.qidao.application.service.dynamic.dynamic.DynamicService;
import com.qidao.application.service.redis.MemberRedissonService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
@Slf4j
public class SystemTimerJob {
    private  static  final int SCAN_LOG_TIME = 60;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private LogMemberLoginMapper logMemberLoginMapper;
    @Autowired
    private LogOperMapper logOperMapper;
    @Autowired
    private MsgRecordMapper msgRecordMapper;
    @Autowired
    private DynamicService dynamicService;
    private LocalDateTime lastTime;

    @Resource
    private MemberRedissonService memberRedissonService;
    @Resource
    private QidaoSchedulingConfigurer qidaoSchedulingConfigurer;

    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(fixedDelay = 5 * 1000 *60)
    public    void vipExpiration() {
        LocalDateTime now = LocalDateTime.now();
        log.info("SystemTimerJob -> vipExpiration -> now -> {}", now);

        LocalDateTime endTime = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);;
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andDelFlagEqualTo(
                ConstantEnum.NOT_DEL.getByte()).andLevelEqualTo(MemberEnum.LEVEL_VIP.getValue()).andVipEndTimeLessThan(endTime);
        List<Member> members = memberMapper.selectByExample(memberExample);
        if(CollUtil.isEmpty(members)){
            log.info("--- SystemTimerJob ---> vipExpiration ----> 没有可扫描的会员");
            return;
        }
        log.info("--- SystemTimerJob ---> vipExpiration ----> 开始扫描会员 扫描个数{}-----",members.size());
        memberExample.clear();
        members.forEach(x->{
            LocalDateTime vipEndTime = x.getVipEndTime();
            if(vipEndTime!=null){
                if(now.isAfter(vipEndTime)){
                    log.info("--- SystemTimerJob ---> vipExpiration ----> 当前时间在会员过期之前 过期该用户vip -----");
                    x.setLevel(0);
                    memberExample.createCriteria().andIdEqualTo(x.getId());
                    memberMapper.updateByExampleSelective(x,memberExample);
                    memberExample.clear();
                    //修改缓存
                    MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
                    BeanUtils.copyProperties(x , memberInfoDTO);
                    RBucket<String> bucket = memberRedissonService.getMemberLoginId(memberInfoDTO.getId());
                    bucket.set(JSONUtil.toJsonStr(memberInfoDTO),120L, TimeUnit.MINUTES);
                }else {
                    log.info("--- SystemTimerJob ---> vipExpiration ----> 当前时间在会员过期之后 过期该用户vip -----");
                    Runnable sendRunnable = () -> {
                        RBucket<Member> memberBucket = memberRedissonService.getMemberAfterId(x.getId());
                        if(memberBucket.isExists()){
                            log.info("--- SystemTimerJob ---> vipExpiration ----> 重复驳回 -----");
                            return;
                        }
                        x.setLevel(0);
                        memberExample.createCriteria().andIdEqualTo(x.getId());
                        memberMapper.updateByExampleSelective(x,memberExample);
                        memberExample.clear();
                        //修改缓存
                        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
                        log.info("--- SystemTimerJob ---> vipExpiration ----> 当前用户在{}过期 -----",x.getVipEndTime());
                        BeanUtils.copyProperties(x , memberInfoDTO);
                        RBucket<String> bucket = memberRedissonService.getMemberLoginId(memberInfoDTO.getId());
                        bucket.set(JSONUtil.toJsonStr(memberInfoDTO),120L, TimeUnit.MINUTES);
                        memberBucket.set(x,5, TimeUnit.HOURS);
                    };
                    log.info("--- SystemTimerJob ---> vipExpiration ----> 延迟到{}开始执行 -----",x.getVipEndTime());
                    qidaoSchedulingConfigurer.addTask(x.getId(), sendRunnable, x.getVipEndTime());
                }
            }
        });
        log.info("--- SystemTimerJob ---> vipExpiration ----> 会员扫描结束 -----");
    }

    @Scheduled(cron = "0 0 0 ? * MON")
    public    void deleteLog() {
        log.info("--- SystemTimerJob ---> deleteLog ----> 开始扫描日志 -----");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusDays(-SCAN_LOG_TIME);
        LogMemberLoginExample logMemberLoginExample = new LogMemberLoginExample();
        logMemberLoginExample.createCriteria().andCreateTimeLessThanOrEqualTo(localDateTime);
        int logMemberSize = logMemberLoginMapper.deleteByExample(logMemberLoginExample);
        MsgRecordExample msgRecordExample = new MsgRecordExample();
        msgRecordExample.createCriteria().andCreateTimeLessThanOrEqualTo(localDateTime);
        int msgSize = msgRecordMapper.deleteByExample(msgRecordExample);
        LogOperExample logOperExample = new LogOperExample();
        logOperExample.createCriteria().andOperTimeLessThanOrEqualTo(localDateTime);
        int logPoerSize = logOperMapper.deleteByExample(logOperExample);
        log.info("SystemTimerJob --->  deleteLog  ---用户日志删除数量{}  ---  信息记录删除数量{} --- 日志拦截删除数量{} ",logMemberSize,msgSize,logPoerSize);
        log.info("--- SystemTimerJob ---> deleteLog ----> 删除日志结束 -----");
    }


    /**
     * todo 使用事件监听机制，不使用定时器 (Autuan)[3.16.start]
     * 刷新用于推荐的已存动态
     */
    @Scheduled(fixedDelay = 1000 * 60 * 20)
    public void refreshDynamicCache(){
        log.info("SystemTimerJob -> refreshDynamicCache -> start -> lastTime -> {}",lastTime);
        dynamicService.refreshRecommendCache(lastTime);
        lastTime = LocalDateTime.now();
        log.info("SystemTimerJob -> refreshDynamicCache -> end");
    }
}
