package batch.job;

import com.tdns.toks.core.common.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableSchedulerLock(defaultLockAtMostFor = "PT10S")
@RequiredArgsConstructor
public class SlackJob {

    @Value(value = "${slack.channel.user-alert}")
    private final String userAlertChannel;
    private final SlackService slackService;

    @Scheduled(cron = "0 0 * * * *")
    @SchedulerLock(name="SchedulerLock", lockAtMostFor = "PT10S", lockAtLeastFor = "PT10S")
    public void UserServiceScheduler() {
        // 유저서비스 증감 데이터 조회
        String userData = "";
        slackService.slackSendMessage(userAlertChannel, userData);
    }
}
