package com.tdns.toks.api.batch.job;

import com.tdns.toks.api.domain.cruiser.client.CruiserClient;
import com.tdns.toks.api.domain.cruiser.dto.CruiserRequest;
import com.tdns.toks.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyStatisticsJob {
    private final CruiserClient cruiserClient;
    private final UserService userService;

    public void dailyStatisticsJob() {
        var userCount = userService.countAllUsers();

        var request = new CruiserRequest(
                ":pray: *1시간 단위 극락 알림* :pray:\n- 현재 유저수 : " + userCount
        );
        cruiserClient.send(request);
    }
}
