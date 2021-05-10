package com.qidao.application.config.interceptor;

import cn.hutool.core.collection.CollUtil;
import com.qidao.application.entity.config.Canal;
import com.qidao.application.entity.config.CanalExample;
import com.qidao.application.mapper.config.CanalMapper;
import com.qidao.application.model.common.VerifyMatch;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 版本校验拦截器
 **/
@Slf4j
@Component("checkVersion")
public class CheckVersion {

    @Autowired
    @Qualifier("versionRedisson")
    private RedissonClient versionRedisson;
    @Autowired
    private CanalMapper canalMapper;

    private final String redisKey = "CANAL::VERSION::MAP::%s";

    public CheckVersion() {
        log.info("VersionInterceptor -> 版本拦截器初始化");
    }

    /**
     * 检查版本是否正确
     *
     * @param version
     * @param canal
     * @param machine
     * @return void
     **/
    public void checkVersion(String version, String canal, String machine) {
        String key = String.format(redisKey, canal);
        RMap<String, Byte> map = versionRedisson.getMap(key);
        if (!map.isExists()) {
            map = setVersionToRedis(version, canal, machine, map, key);
        }

        if (!map.containsKey(version)) {
            throw new BusinessException(BaseEnum.VERSION_ERROR);
        }
    }

    /**
     * 设置 版本信息 到 redis中
     *
     * @param version
     * @param canal
     * @param machine
     * @param map     redisson map
     */
    private RMap<String, Byte> setVersionToRedis(String version, String canal, String machine, RMap<String, Byte> map, String key) {
        RLock lock = versionRedisson.getLock(key + "::LOCK");
        try {
            //5秒  后锁自动解除
            lock.lock(5L, TimeUnit.SECONDS);
            map = versionRedisson.getMap(key);
            if (map.isExists()) {
                return map;
            }

            CanalExample example = new CanalExample();
            example.createCriteria()
                    .andNameEqualTo(canal)
                    //1-预发布 2-已发布
                    .andStatusIn(Arrays.asList(1,2))
                    .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
            List<Canal> canals = canalMapper.selectByExample(example);
            //后台没有版本信息 缓存 null map 30分钟
            if (CollUtil.isEmpty(canals)) {
                map.put("test", new Byte("0"));
                map.expire(30L, TimeUnit.MINUTES);
                return map;
            }

            //按版本号排序倒序
            CollUtil.sort(canals, (one, two) ->
                    VerifyMatch.compareVersion(two.getVersion(), one.getVersion()) //1 代表左邊大
            );

            for (Canal canal1 : canals) {
                map.put(canal1.getVersion(), canal1.getForceFlag());
                //强制更新 0-否 1-是
                if (canal1.getForceFlag().equals(ConstantEnum.DELETED.getByte())) {
                    break;
                }
            }
            //设置 过期时间 为 8小时
            map.expire(8L, TimeUnit.HOURS);
        } finally {
            lock.unlock();
        }
        return map;
    }

}
