package com.tdns.toks.api.batch.scheduler;

import com.tdns.toks.api.batch.job.QuizTagJob;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizTagScheduler {
    private final QuizTagJob quizTagJob;

    /**
     * 1시간 단위로, 전체 퀴즈에 대한 태그 매핑을 최신환다.
     * - 해당 방법은 추후 변경이 필요하다
     */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void runJob() {
        quizTagJob.runEveryHourJob();
    }
}
