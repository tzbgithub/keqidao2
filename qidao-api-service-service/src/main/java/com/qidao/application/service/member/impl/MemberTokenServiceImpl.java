package com.qidao.application.service.member.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.token.MemberToken;
import com.qidao.application.entity.member.token.MemberTokenExample;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.member.token.MemberTokenMapper;
import com.qidao.application.model.common.QiDaoEncodeUtil;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.log.LogBehaveMemberErrorEnum;
import com.qidao.application.model.member.MemberExceptionEnum;
import com.qidao.application.model.member.sign.MemberInfoDTO;
import com.qidao.application.model.member.token.GeneratorAccessTokenReq;
import com.qidao.application.model.member.token.GeneratorRefreshTokenDTO;
import com.qidao.application.model.member.token.TokenConstantEnum;
import com.qidao.application.service.member.MemberTokenService;
import com.qidao.application.service.redis.MemberRedissonService;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Autuan.Yu
 */
@Service
@Slf4j
public class MemberTokenServiceImpl implements MemberTokenService {
    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private MemberTokenMapper memberTokenMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private QiDaoEncodeUtil qiDaoEncodeUtil;
    @Value("${token.refresh.expire}")
    private Integer expireDay;
    @Value("${token.refresh.frequent}")
    private Integer frequentNum;
    @Value("${token.access.expire}")
    private Integer accessExpire;
    @Resource
    private MemberRedissonService memberRedissonService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generatorRefreshToken(GeneratorRefreshTokenDTO req) {
        log.info("MemberTokenServiceImpl -> generatorRefreshToken -> start -> req -> {}", req);

        if (req.getLastUseTime() == null) {
            req.setLastUseTime(LocalDateTime.now());
        }
        LocalDateTime now = req.getLastUseTime();
        if (req.getRandomInt() == null) {
            req.setRandomInt(RandomUtil.randomInt());
        }

        Long memberId = req.getMemberId();

        Member member = memberMapper.selectByPrimaryKey(memberId);
        if (member == null) {
            throw new BusinessException(LogBehaveMemberErrorEnum.USER_LOGBEHAVEMEMBER_ERROR);
        }

        String refreshToken = qiDaoEncodeUtil.desEncode(JSONUtil.toJsonStr(req));

        MemberToken tokenBean = MemberToken.builder()
                .id(snowflakeIdWorker53.nextId())
                .memberId(memberId)
                .refreshToken(refreshToken)
                .lastUseTime(now)
                .build();

        // 同一个refresh token只能有一个是可用的
        RMap<String, String> refreshTokenMap = redissonClient.getMap(TokenConstantEnum.REFRESH_TOKEN_KEY.getValue());
        refreshTokenMap.put(refreshToken, now.format(DatePattern.NORM_DATETIME_FORMATTER));

        MemberToken delBean = MemberToken.builder()
                .delFlag(ConstantEnum.DELETED.getByte())
                .build();
        MemberTokenExample example = new MemberTokenExample();
        example.createCriteria()
                .andMemberIdEqualTo(req.getMemberId())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        //todo 并发修改memberToken为删除状态过慢
        memberTokenMapper.updateByExampleSelective(delBean, example);
        memberTokenMapper.insertSelective(tokenBean);

        // member 信息

        // 生成 refresh token
        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
        BeanUtils.copyProperties(member, memberInfoDTO);
        RBucket<String> bucket = memberRedissonService.getMemberLoginId(memberId);
        bucket.set(JSONUtil.toJsonStr(memberInfoDTO), 120L, TimeUnit.MINUTES);

        log.info("MemberTokenServiceImpl -> generatorRefreshToken -> end -> refreshToken -> {}", refreshToken);
        return refreshToken;
    }

    @Override
    public String generatorRefreshTokenNotToDB(GeneratorRefreshTokenDTO req) {
        LocalDateTime now = LocalDateTime.now();
        req.setRandomInt(RandomUtil.randomInt());
        req.setLastUseTime(now);
        return qiDaoEncodeUtil.desEncode(JSONUtil.toJsonStr(req));
    }

    @Override
    public String generatorAccessToken(GeneratorAccessTokenReq req) {
        log.info("MemberTokenServiceImpl -> generatorAccessToken -> start -> req -> {}", req);
        String refreshToken = req.getRefreshToken();
        LocalDateTime now = LocalDateTime.now();

        GeneratorRefreshTokenDTO oldRefreshTokenInfo = JSONUtil.toBean(qiDaoEncodeUtil.desDecrypt(refreshToken), GeneratorRefreshTokenDTO.class);

        log.info("MemberTokenServiceImpl -> generatorAccessToken -> refreshTokenInfo -> {}", oldRefreshTokenInfo);

        Long memberId = oldRefreshTokenInfo.getMemberId();

        boolean exists = memberRedissonService.getMemberFirstLoginId(memberId).isExists();
        if (!exists) {
            MemberTokenExample example = new MemberTokenExample().limit(frequentNum + 1);

            example.createCriteria()
                    .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                    .andMemberIdEqualTo(memberId);
            example.setOrderByClause("create_time desc");
            List<MemberToken> list = memberTokenMapper.selectByExample(example);

            boolean able = CollUtil.isNotEmpty(list);
            log.info("MemberTokenServiceImpl -> generatorAccessToken -> able -> {}", able);
            // 如果不存在
            if (!able) {
                log.warn("MemberTokenServiceImpl -> generatorAccessToken -> able -> REFRESH TOKEN 已过期 ");
                throw new BusinessException(BaseEnum.TOKEN_EXPIRED);
            }
            // 最近一小时登录次数不能大于上限
            boolean countBool = list.stream().filter(item -> item.getCreateTime().isAfter(now.minusHours(1)))
                    .count() > frequentNum;
            log.info("MemberTokenServiceImpl -> generatorAccessToken -> countBool -> {}", countBool);
            if (countBool) {
                log.warn("MemberTokenServiceImpl -> generatorAccessToken -> able -> REFRESH TOKEN 登录过于频繁");
                throw new BusinessException(BaseEnum.FREQUENT);
            }
        }


        // 将该加密串MD5后所得值作为key，加密串作为值存入redis
        String str = memberId + now.toString() + RandomUtil.randomInt();
        String accessToken = SecureUtil.md5(str);

        RBucket<Object> accessTokenBucket = redissonClient.getBucket(accessToken);
        accessTokenBucket.set(refreshToken, accessExpire, TimeUnit.MINUTES);

        // 将用户基本信息保存到redis中
        Member member = memberMapper.selectByPrimaryKey(memberId);
        if (null == member) {
            throw new BusinessException(MemberExceptionEnum.DELETE_TRUE);
        }
        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
        BeanUtils.copyProperties(member, memberInfoDTO);
        RBucket<String> memberInfoBucket = memberRedissonService.getMemberLoginId(memberId);
        memberInfoBucket.set(JSONUtil.toJsonStr(memberInfoDTO), 2, TimeUnit.HOURS);

        log.info("MemberTokenServiceImpl -> generatorAccessToken -> end -> accessToken -> {}", accessToken);
        return accessToken;
    }

    @Override
    public void updateTokenToDb() {
        RMap<String, String> refreshTokenMap = redissonClient.getMap(TokenConstantEnum.REFRESH_TOKEN_KEY.getValue());
        // 不存在或值为空 直接结束
        boolean isEmpty = !refreshTokenMap.isExists() || refreshTokenMap.isEmpty();
        log.info("MemberTokenServiceImpl -> updateTokenToDb -> isEmpty -> {}", isEmpty);
        if (isEmpty) {
            return;
        }
        log.info("MemberTokenServiceImpl -> updateTokenToDb -> refreshTokenMap size -> {}", refreshTokenMap.size());
        MemberTokenExample example = new MemberTokenExample();
        // 更新 refresh token 最近使用时间
        refreshTokenMap.forEach((refreshToken, lastUseTimeStr) -> {
            MemberToken updateBean = MemberToken.builder()
                    .lastUseTime(LocalDateTime.parse(lastUseTimeStr, DatePattern.NORM_DATETIME_FORMATTER))
                    .build();

            example.clear();
            example.createCriteria()
                    .andRefreshTokenEqualTo(refreshToken);
            memberTokenMapper.updateByExampleSelective(updateBean, example);
        });

        refreshTokenMap.clear();
    }

    @Override
    public void logicDelTokenScheduled() {
        MemberTokenExample example = new MemberTokenExample();
        example.createCriteria()
                // 时间为：配置文件配置
                .andLastUseTimeLessThan(LocalDateTime.now().minusDays(expireDay))
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        MemberToken bean = MemberToken.builder()
                .delFlag(ConstantEnum.DELETED.getByte())
                .build();
        memberTokenMapper.updateByExampleSelective(bean, example);
    }

}
