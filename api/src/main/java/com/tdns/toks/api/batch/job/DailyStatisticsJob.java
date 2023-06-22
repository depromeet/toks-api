package com.tdns.toks.api.batch.job;

import com.tdns.toks.api.domain.cruiser.client.CruiserClient;
import com.tdns.toks.api.domain.cruiser.dto.CruiserRequest;
import com.tdns.toks.core.domain.actionlog.service.SystemActionLogService;
import com.tdns.toks.core.domain.study.service.TagService;
import com.tdns.toks.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyStatisticsJob {
    private final CruiserClient cruiserClient;
    private final UserService userService;
    private final SystemActionLogService systemActionLogService;
    private final TagService tagService;

    public void dailyStatisticsJob() {
        var userCount = userService.countAllUsers();
        var newUserCount = userService.countNewUsers();
        var apiCallCount = systemActionLogService.countApiCallCount();
        var tagStaticsCount = tagService.countStatics();

        var request = new CruiserRequest(
                ":pray: *극락 알림* :pray:"
                        + "\n- 전체 가입자 : " + userCount
                        + "\n- 신규 가입자 : " + newUserCount
                        + "\n- Api Call Count : " + apiCallCount
                        + "\n- Daily Tag Count : " + tagStaticsCount.getDailyCount()
                        + "\n- All Tag Count : " + tagStaticsCount.getAllCount()
        );

        cruiserClient.send(request);
    }
}
