package com.tdns.toks.api.batch.job;

import com.tdns.toks.api.domain.cruiser.client.CruiserClient;
import com.tdns.toks.api.domain.cruiser.dto.CruiserRequest;
import com.tdns.toks.core.domain.actionlog.service.SystemActionLogService;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyStatisticsJob {
    private final CruiserClient cruiserClient;
    private final UserService userService;
    private final SystemActionLogService systemActionLogService;
    private final StudyService studyService;

    public void dailyStatisticsJob() {
        var userCount = userService.countAllUsers();
        var newUserCount = userService.countNewUsers();
        var newStudyCount = studyService.countNewStudyCount();
        var apiCallCount = systemActionLogService.countApiCallCount();

        var request = new CruiserRequest(
                ":pray: *극락 알림* :pray:\n- 전체 유저수 : " + userCount + "\n- 신규 유저수 : " + newUserCount + "\n- 신규 등록 스터디 : " + newStudyCount + "\n- Api Call Count : " + apiCallCount
        );
        cruiserClient.send(request);
    }
}
