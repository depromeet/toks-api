package com.tdns.toks.core.domain.quartz.component;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
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
public class JobResDto {

	private Long id;        // job id
	private JobType type;       // job 유형
	private String name;        // job 이름
	private String cronString;      // job cron
	private String cronDescription;     // job cron 설명
	@Builder.Default
	private Boolean activated = false;      // job 활성화 여부
	private Map<String, Object> params;     // job params
	private LocalDateTime nextFireAt;       // job 다음 실행 시간

	public static JobResDto from(Job job) {
		try {
			return JobResDto.builder()
				.id(job.getId())
				.type(job.getType())
				.name(job.getName())
				.cronString(job.getCronString())
				.cronDescription(job.getCronDescription())
				.activated(job.getActivated())
				.params(
					StringUtils.hasText(job.getParams()) ? new ObjectMapper().readValue(job.getParams(), Map.class) : null)
				.build();
		} catch (JsonProcessingException e) {
			throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, "Job Params이 map 아닙니다.");
		}
	}

}
