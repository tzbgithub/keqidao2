package com.qidao.application.service.msg.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.msg.*;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.msg.CustomMsgMapper;
import com.qidao.application.mapper.msg.MsgMapper;
import com.qidao.application.mapper.msg.MsgRecordMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.page.PageUtil;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.config.SelectConfigVo;
import com.qidao.application.model.config.SelectGetByTypeReq;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.log.MsgNotifyReq;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.msg.*;
import com.qidao.application.model.msg.enums.MsgConstantEnum;
import com.qidao.application.model.msg.enums.MsgExceptionEnum;
import com.qidao.application.model.msg.enums.MsgSendTypeEnum;
import com.qidao.application.service.config.SelectConfigService;
import com.qidao.application.service.msg.MsgSendService;
import com.qidao.application.service.msg.MsgService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("msgService")
@Slf4j
public class MsgServiceImpl implements MsgService {

    @Resource
    private MsgMapper msgMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Resource
    private SelectConfigService selectConfigService;

    @Resource
    private MsgRecordMapper msgRecordMapper;

    @Autowired
    @Qualifier("PhoneMsgSendImpl")
    private MsgSendService phoneMsgSendImpl;

    @Autowired
    @Qualifier("QidaoNoticeMsgSendImpl")
    private MsgSendService noticeService;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private CustomMsgMapper customMsgMapper;

    /**
     * 记录到数据库中 msgId
     */
    @Value("${msg.sms.id}")
    private Long msgId;

    /**
     * 短信发送 24小时上限
     */
    @Value("${msg.sms.day.limit}")
    private Integer dayLimit;

    /**
     * 新增消息
     *
     * @param req
     */
    @Override
    public void insert(MsgInsertReq req) {
        log.info("MsgServiceImpl -> inset -> start -> param:{}", req);
        Msg msg = new Msg();
        BeanUtils.copyProperties(req, msg);
        msg.setId(snowflakeIdWorker53.nextId());
        msg.setDelFlag(ConstantEnum.NOT_DEL.getByte());
        msg.setStatus(MsgConstantEnum.STATUS_TRUE.getCode());
        msgMapper.insertSelective(msg);
        log.info("MsgServiceImpl -> inset -> end");
    }

    /**
     * 删除消息
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        log.info("MsgServiceImpl -> delete -> start -> param:{}", id);
        MsgExample msgExample = new MsgExample();
        msgExample.createCriteria()
                .andIdEqualTo(id)
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Msg msg = msgMapper.selectOneByExample(msgExample);
        log.info("MsgServiceImpl -> delete -> msg == null :{}", msg == null);
        if (msg == null) {
            log.info("MsgServiceImpl -> delete -> error:{}", MsgExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MsgExceptionEnum.DELETE_TRUE);
        }
        msg.setDelFlag(ConstantEnum.DELETED.getByte());
        msgMapper.updateByPrimaryKeySelective(msg);
        log.info("MsgServiceImpl -> delete -> end");
    }

    /**
     * 修改消息
     *
     * @param req
     */
    @Override
    public void update(MsgUpdateReq req) {
        log.info("MsgServiceImpl -> update -> start -> param:{}", req);
        MsgExample msgExample = new MsgExample();
        msgExample.createCriteria()
                .andIdEqualTo(req.getId())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Msg msg = msgMapper.selectOneByExample(msgExample);
        log.info("MsgServiceImpl -> update -> msg ==null :{}", msg == null);
        if (msg == null) {
            log.error("MsgServiceImpl -> update -> error :{}", MsgExceptionEnum.DELETE_TRUE.getMessage());
            throw new BusinessException(MsgExceptionEnum.DELETE_TRUE);
        }
        BeanUtils.copyProperties(req, msg);
        msgMapper.updateByExampleSelective(msg, msgExample);
        log.info("MsgServiceImpl -> update -> end");
    }

    /**
     * 查询消息
     *
     * @param req
     * @return
     */
    @Override
    public ServicePage<List<MsgPageRes>> getMsg(MsgPageReq req) {
        log.info("MsgServiceImpl -> getMsg -> start -> param:{}", req);
        MsgExample msgExample = new MsgExample();
        MsgExample.Criteria criteria = msgExample.createCriteria();
        log.info("MsgServiceImpl -> getMsg -> req.getStatus() != null :{}", req.getStatus() != null);
        if (req.getStatus() != null) {
            criteria.andStatusEqualTo(req.getStatus());
        }
        Boolean titleIsEmpty = req.getKeyword() != null && !req.getKeyword().equals("");
        log.info("MsgServiceImpl -> getMsg -> titleIsEmpty:{}", titleIsEmpty);
        if (titleIsEmpty) {
            criteria.andTitleLike("%" + req.getKeyword() + "%");
        }
        log.info("MsgServiceImpl -> getMsg -> req.getType() != null :{}", req.getType() != null);
        if (req.getType() != null) {
            criteria.andTypeEqualTo(req.getType());
        }
        log.info("MsgServiceImpl -> getMsg -> req.getMenuId() != null :{}", req.getMenuId() != null);
        if (req.getMenuId() != null) {
            criteria.andMenuIdEqualTo(req.getMenuId());
        }
        criteria.andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Page<Msg> page = PageUtil.start(req, () -> msgMapper.selectByExampleWithBLOBs(msgExample));
        ServicePage<List<MsgPageRes>> res = new ServicePage<>();
        BeanUtils.copyProperties(page, res);
        List<Msg> result = page.getResult();
        List<MsgPageRes> msgPageRes = new ArrayList<>();
        log.info("MsgServiceImpl -> getMsg -> result != null :{}", CollUtil.isNotEmpty(result));
        if (CollUtil.isNotEmpty(result)) {
            result.forEach(msg -> {
                MsgPageRes msgPageRes1 = new MsgPageRes();
                BeanUtils.copyProperties(msg, msgPageRes1);
                msgPageRes.add(msgPageRes1);
            });
            res.setResult(msgPageRes);
        }
        log.info("MsgServiceImpl -> getMsg -> end -> return :{}", res);
        return res;
    }

    /**
     * 根据type查询select_config
     *
     * @param req
     * @return
     */
    @Override
    public ServicePage<List<SelectConfigVo>> getSelectByType(SelectGetByTypeReq req) {
        log.info("MsgServiceImpl -> getSelectByType -> start -> param:{}", req);
        ServicePage<List<SelectConfigVo>> res =
                selectConfigService.getSelectByType(req);
        log.info("MsgServiceImpl -> getSelectByType -> end -> return :{}", res);
        return res;
    }

    @Override
    public void smsSend(SmsSendReq smsSendReq) {
        log.info("MsgServiceImpl -> smsSend -> start -> param -> {} ", smsSendReq);

        LocalDateTime now = LocalDateTime.now();
        String phone = smsSendReq.getPhone();

        // 频繁发送校验
        RList<String> list = redissonClient.getList("sms::send::record::" + phone);
        log.info("MsgServiceImpl -> smsSend -> list -> size -> {}", list.size());
        // 已有发送记录：进入次数校验
        if (CollUtil.isNotEmpty(list)) {
            if (list.size() > dayLimit) {
                throw new BusinessException(MsgExceptionEnum.LIMIT);
            }
            MsgCacheDO msgCacheDO = JSONUtil.toBean(list.get(list.size() - 1), MsgCacheDO.class);
            LocalDateTime recentTime = LocalDateTime.parse(msgCacheDO.getSendTime(), DatePattern.NORM_DATETIME_FORMATTER);
            log.info("MsgServiceImpl -> smsSend -> list -> last -> {}  recentTime -> {}", msgCacheDO, recentTime);
            if (now.isBefore(recentTime.plusMinutes(1))) {
                throw new BusinessException(MsgExceptionEnum.FREQUENT);
            }
        }

        // 记录 record
        long recordId = snowflakeIdWorker53.nextId();
        MsgRecord msgRecord = MsgRecord.builder()
                .id(recordId)
                .delFlag(ConstantEnum.NOT_DEL.getByte())
                .status(MsgConstantEnum.STATUS_TRUE.getCode())
                .memberId(Long.parseLong(smsSendReq.getPhone()))
                .sendTime(LocalDateTime.now())
                .msgId(msgId)
                .build();
        msgRecordMapper.insertSelective(msgRecord);

        // 发送
        phoneMsgSendImpl.send(MsgSendDTO.builder()
//                .id(recordId)
                .receivers(Collections.singletonList(smsSendReq.getPhone()))
                .contentType(MsgSendTypeEnum.CODE.getVal())
                .build());

        MsgCacheDO msgCache = MsgCacheDO.builder()
                .id(snowflakeIdWorker53.nextId())
                .sendTime(LocalDateTime.now().format(DatePattern.NORM_DATETIME_FORMATTER))
                .build();
        list.add(JSONUtil.toJsonStr(msgCache));

        // 过期时间
        if (list.size() < 2) {
            list.expire(1, TimeUnit.DAYS);
        }

        log.info("MsgServiceImpl -> smsSend -> end");
    }

    @Override
    public boolean ableSendMsg(Long sendToMsgMemberId) {
        log.info("MsgServiceImpl -> ableSendMsg -> start -> sendToMsgMemberId -> {} ", sendToMsgMemberId);
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andPushStatusEqualTo(MemberEnum.PUSH_STATUS_OPEN.getValue())
                .andLevelGreaterThanOrEqualTo(MemberEnum.LEVEL_ORDINARY.getValue());

        Member member = memberMapper.selectOneByExample(memberExample);
        boolean able = null != member;
        log.info("MsgServiceImpl -> ableSendMsg -> end -> sendToMsgMemberId -> {} able -> {}", sendToMsgMemberId, able);
        return able;
    }

    @Override
    public ServicePage<List<MemberVipMsgPageRes>> getMemberVipMsg(MemberVipMsgPageReq req) {
        log.info("MsgServiceImpl -> getMemberVipMsg -> start -> param -> {}", req);
        ServicePage<List<MemberVipMsgPageRes>> res = new ServicePage<>();
        Page<MemberVipMsg> page = PageUtil.start(req, () -> customMsgMapper.getMemberVipMsg(req.getMemberId()));
        BeanUtils.copyProperties(page, res);
        List<MemberVipMsgPageRes> pageResList = new ArrayList<>();
        page.getResult().forEach(memberVipMsg -> {
            MemberVipMsgPageRes memberVipMsgPageRes = new MemberVipMsgPageRes();
            BeanUtils.copyProperties(memberVipMsg, memberVipMsgPageRes);
            pageResList.add(memberVipMsgPageRes);
        });
        res.setResult(pageResList);
        log.info("MsgServiceImpl -> getMemberVipMsg -> end -> return -> {} ", res);
        return res;
    }

    @Override
    public void emptyVipMsg(BaseIdQuery id) {
        log.info("MsgServiceImpl -> emptyVipMsg -> start -> param -> {}", id);
        MsgRecordExample example = new MsgRecordExample();
        example.createCriteria().andMemberIdEqualTo(id.getId()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        msgRecordMapper.updateByExampleSelective(MsgRecord.builder().delFlag(ConstantEnum.DELETED.getByte()).build(), example);
        log.info("MsgServiceImpl -> emptyVipMsg -> end");
    }

    @Override
    public void notifyPush(MsgNotifyReq req) {
        noticeService.send(MsgSendDTO.builder()
                .contentType(0)
                .id(1002001L)
                .receivers(Arrays.asList(req.getAlias()))
                .contents(Arrays.asList(req.getContent()))
                .title(Arrays.asList(req.getTitle()))
                .build());
    }
}
