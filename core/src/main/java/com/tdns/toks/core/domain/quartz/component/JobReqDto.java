package com.tdns.toks.core.domain.quartz.component;

import java.util.Map;

import org.springframework.util.ObjectUtils;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.tdns.toks.core.domain.quartz.entity.Job;
import com.tdns.toks.core.domain.quartz.type.JobType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobReqDto {

    private Long id;        // job id
    private JobType type;       // job 유형
    private String name;        // job 유형
    private String cronString;      // job cron
    private String cronDescription;     // job cron 설명
    @Builder.Default
    private Boolean activated = false;      // job 활성화 여부
    private Map<String, Object> params;     // job params

    public static Job to(JobReqDto jobDto) {
        return Job.builder()
                .type(jobDto.getType())
                .name(jobDto.getName())
                .cronString(jobDto.getCronString())
                .cronDescription(jobDto.getCronDescription())
                .activated(jobDto.getActivated())
                .service("TOKS-API")
                .params(!ObjectUtils.isEmpty(jobDto.getParams()) ? new JSONObject(jobDto.getParams()).toString() : null)
                .build();
    }

}
