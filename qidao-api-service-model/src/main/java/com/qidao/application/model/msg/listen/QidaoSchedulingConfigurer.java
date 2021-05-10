package com.qidao.application.model.msg.listen;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.ObjectListener;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryExpiredListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

/**
 * 企岛 倒计时 监听
 * @author Autuan.Yu
 */

@Component
@Slf4j
public class QidaoSchedulingConfigurer implements SchedulingConfigurer{
    private ScheduledTaskRegistrar taskRegistrar;
    private Map<Long,ScheduledFuture> scheduledFutureMap = new HashMap<>();

    public void addTask(Long runId,Runnable runnable, LocalDateTime startTime) {
        Date date = Date.from( startTime.atZone( ZoneId.systemDefault()).toInstant());
        addTask(runId,runnable,date);
    }

    public void addTask(Long runId,Runnable runnable, Date startTime) {
        ScheduledFuture schedule = taskRegistrar.getScheduler().schedule(runnable, startTime);
        scheduledFutureMap.put(runId, schedule);
    }

    public void cancel(Long runId){
        ScheduledFuture schedule = scheduledFutureMap.get(runId);
        if(null != schedule) {
            schedule.cancel(false);
            scheduledFutureMap.remove(runId);
        }
    }

    public void clear(){
        List<Long> list = scheduledFutureMap.keySet().stream().limit(1000).collect(Collectors.toList());
        log.info("QidaoMsgSchedulingConfigurer -> clear -> list -> size -> {}",list.size());
        for(Long key : list) {
            boolean done = scheduledFutureMap.get(key).isDone();
            if(done) {
                scheduledFutureMap.remove(key);
            }
        }
    }

    public Integer size(){
        int size = scheduledFutureMap.size();
        log.info("QidaoMsgSchedulingConfigurer -> size -> {}",size);
        return size;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        this.taskRegistrar = scheduledTaskRegistrar;
    }
}
