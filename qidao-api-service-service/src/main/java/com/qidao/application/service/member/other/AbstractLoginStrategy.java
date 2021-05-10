package com.qidao.application.service.member.other;

import com.qidao.application.entity.member.Member;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.framework.util.SnowflakeIdWorker53;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象 登录 策略
 **/
public abstract class AbstractLoginStrategy implements ILoginStrategy {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SnowflakeIdWorker53 snowflakeIdWorker53;
    @Autowired
    private RedissonClient redissonClient;

    protected MemberMapper getMemberMapper() {
        return memberMapper;
    }

    protected long getId() {
        return snowflakeIdWorker53.nextId();
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    /**
     * 检查会员对象师傅为空  为空则抛出异常
     * @param member
     * @return void
     **/
    protected void checkMember(Member member){
        if(member==null){
            throw new BusinessException(BaseEnum.ACCOUNT_VERIFY_PASSWORD_ERROR);
        }
    }

}
