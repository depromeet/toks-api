package com.tdns.toks.api.batch.scheduler;

import com.tdns.toks.api.batch.job.DailyStatisticsJob;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyStatisticsScheduler {
    private final DailyStatisticsJob dailyStatisticsJob;

    @Scheduled(cron = "0 0 9 * * *")
    public void dailyStatisticsJob() {
        dailyStatisticsJob.dailyStatisticsJob();
    }
}
