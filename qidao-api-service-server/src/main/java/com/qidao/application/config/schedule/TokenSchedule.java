package com.qidao.application.config.schedule;

import com.qidao.application.service.member.MemberTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Autuan.Yu
 */
@Slf4j
@Component
public class TokenSchedule {
    @Autowired
    MemberTokenService memberTokenService;
    /**
     * 定期将token最近使用时间保存到DB : 30分钟
     */
    @Scheduled(fixedDelay = 1000 * 60 * 30)
    private void saveTokenUpdateToDb(){
        try{
            memberTokenService.updateTokenToDb();
        } catch (Exception e){
            log.error("saveTokenUpdateToDb error",e);
        }
    }

    /**
     * 逻辑删除长时间未用的refresh_token
     * 执行频率 : 1周1次  每周一凌晨二点执行
     */
    @Scheduled(cron="0 0 2 ? * 2")
    private void logicDelRefreshToken(){
        try {
            memberTokenService.logicDelTokenScheduled();
        } catch (Exception e) {
            log.error("logicDelRefreshToken error",e);
        }
    }
}
