package com.tdns.toks.core.domain.quartz.service;

import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quartz.component.QuartzJob;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class QuartzService {

    public static final String DEFAULT_GROUP_NAME = "TOKS-API";
    public static final String TIMEZONE = "Asia/Seoul";

    private final Scheduler scheduler;

    public LocalDateTime addQuartzJob(String jobName, String cronString) throws SchedulerException {
        JobDetail job = newJob(QuartzJob.class).withIdentity(jobName, DEFAULT_GROUP_NAME).usingJobData("appJobName", jobName).build();
        Trigger trigger = cronTrigger(cronString);
        Date nextExecutionTime = trigger.getFireTimeAfter(new Date());
        scheduler.scheduleJob(job, trigger);
        return LocalDateTime.ofInstant(nextExecutionTime.toInstant(), ZoneId.of(TIMEZONE));
    }

    private Trigger cronTrigger(String cronString) {
        return newTrigger().withSchedule(cronSchedule(cronString)
                .withMisfireHandlingInstructionDoNothing()
                .inTimeZone(TimeZone.getTimeZone(ZoneId.of(TIMEZONE)))).build();
    }

    public Boolean checkExistQuartzJob(String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, DEFAULT_GROUP_NAME);
        return scheduler.checkExists(jobKey);
    }

    public LocalDateTime updateJob(String jobName, String cronString) throws SchedulerException {
        List<? extends Trigger> triggers = getTriggers(jobName);
        Trigger updatedTrigger = this.cronTrigger(cronString);
        Date nextExecutionTime = updatedTrigger.getFireTimeAfter(new Date());
        this.scheduler.rescheduleJob(triggers.get(0).getKey(), this.cronTrigger(cronString));
        return LocalDateTime.ofInstant(nextExecutionTime.toInstant(), ZoneId.of(TIMEZONE));
    }

    public Boolean removeJob(String jobName) throws SchedulerException {
        this.scheduler.deleteJob(new JobKey(jobName, DEFAULT_GROUP_NAME));
        return true;
    }

    public LocalDateTime getNextFireTime(String jobName) throws SchedulerException {
        List<? extends Trigger> triggers = getTriggers(jobName);
        Date fireTimeAfter = triggers.get(0).getFireTimeAfter(new Date());
        return LocalDateTime.ofInstant(fireTimeAfter.toInstant(), ZoneId.of(TIMEZONE));
    }

    private List<? extends Trigger> getTriggers(String jobName) throws SchedulerException {
        List<? extends Trigger> triggers = this.scheduler.getTriggersOfJob(new JobKey(jobName, DEFAULT_GROUP_NAME));
        if (triggers.size() != 1) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, "job 이름의 스케쥴 정보가 유효하지 않습니다.");
        }
        return triggers;
    }

    public void activateTrigger(String name) throws SchedulerException {
        this.scheduler.resumeJob(new JobKey(name, DEFAULT_GROUP_NAME));
    }

    public void pauseTrigger(String name) throws SchedulerException {
        this.scheduler.pauseJob(new JobKey(name, DEFAULT_GROUP_NAME));
    }
}
