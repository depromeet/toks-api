package com.tdns.toks.core.domain.quartz.component;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.tdns.toks.core.domain.quartz.service.JobService;
import com.tdns.toks.core.domain.quartz.type.JobResultType;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.quartz.enabled")
public class QuartzJob implements Job {

    private String appJobName;

    private final JobService jobService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("#### Scheduler :  {} is executed.", appJobName);
        JobResultType jobResultType = jobService.executeJob(appJobName, null);
        if (jobResultType == JobResultType.SUCCESS) {
            log.info("#### Scheduler :  {} is successfully terminated.", appJobName);
        } else {
            log.error("#### Scheduler :  There was a problem running the job, {}. The job result status is {}.", appJobName, jobResultType);
        }
    }

}
