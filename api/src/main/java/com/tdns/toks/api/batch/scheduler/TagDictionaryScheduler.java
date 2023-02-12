package com.tdns.toks.api.batch.scheduler;

import com.tdns.toks.api.batch.job.TagDictionaryJob;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagDictionaryScheduler {
    private final TagDictionaryJob tagDictionaryJob;

    /**
     * 30초 단위로 Tag 사전 배치 잡 시작
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void runJob() {
        tagDictionaryJob.run();
    }
}
