package com.qidao.application.service.redis;

import com.qidao.application.entity.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 会员缓存服务
 **/
@Service
@Slf4j
public class MemberRedissonService {

    @Resource(name = "memberRedisson")
    private RedissonClient memberRedisson;

    @Resource
    private RedissonClient redissonClient;

    private final String MEMBER_LOGIN_ID = "member::login::id::%s";
    private final String MEMBER_AFTER_ID = "member::after::id::%s";
    private final String GET_MEMBER_INVITE_KEY_ID = "member::invite%s::id::%s";
    /**
     * 第一次登录  异步数据未保存到db时，需要使用  redis的标识
     */
    private final String GET_MEMBER_FIRST_LOGIN_ID = "member::first::login::id::%s";

    public RBucket<String> getMemberFirstLoginId(Long memberId){
        return memberRedisson.getBucket(String.format(GET_MEMBER_FIRST_LOGIN_ID,memberId));
    }

    public RBucket<String> getMemberLoginId(Long memberId){
        return memberRedisson.getBucket(String.format(MEMBER_LOGIN_ID,memberId));
    }

    public RBucket<Member> getMemberAfterId(Long memberId){
        return redissonClient.getBucket(String.format(MEMBER_AFTER_ID,memberId));
    }

    public RBucket<String> getMemberInviteKeyId(String key,Long memberId){
        return redissonClient.getBucket(String.format(GET_MEMBER_INVITE_KEY_ID,key,memberId));
    }

}
