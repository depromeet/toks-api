package com.tdns.toks.core.domain.quartz.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quartz.component.JobReqDto;
import com.tdns.toks.core.domain.quartz.component.JobResDto;
import com.tdns.toks.core.domain.quartz.entity.Job;
import com.tdns.toks.core.domain.quartz.repository.CustomJobRepository;
import com.tdns.toks.core.domain.quartz.repository.JobRepository;
import com.tdns.toks.core.domain.quartz.type.JobResultType;
import com.tdns.toks.core.domain.quartz.type.JobType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JobService {

    private final QuartzService quartzService;
    private final JobRepository jobRepository;
    private final CustomJobRepository customJobRepository;

    @Transactional(readOnly = true)
    public JobResDto getJob(Long id) {
        Job dbJob = getJobById(id);
        JobResDto response = JobResDto.from(dbJob);
        try {
            if (Boolean.TRUE.equals(dbJob.getActivated())) {
                LocalDateTime nextFireTime = quartzService.getNextFireTime(dbJob.getName());
                response.setNextFireAt(nextFireTime);
            }
        } catch (Exception e) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR);
        }
        return response;
    }

    @Transactional(readOnly = true)
    public Job getJobById(Long id) {
        return customJobRepository.retrieveJobById(id)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, "존재하지 않는 job입니다."));
    }

    public JobResDto addJob(JobReqDto jobDto) {
        throwInvalidParameter(jobDto.getType(), "type");
        throwInvalidParameter(jobDto.getName(), "name");
        throwInvalidParameter(jobDto.getCronString(), "cronString");
        throwInvalidParameter(jobDto.getActivated(), "activated");

        String name = jobDto.getName();
        Job job;
        LocalDateTime nextFireTime;
        try {
            if (quartzService.checkExistQuartzJob(name)) {
                throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, "이미 존재하는 Job 입니다.");
            }
            boolean isValid = customJobRepository.existsJobByType(jobDto.getType());
            if (Boolean.TRUE.equals(isValid)) {
                throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, "이미 존재하는 Job type 입니다.");
            }
            job = jobRepository.save(JobReqDto.to(jobDto));

            nextFireTime = quartzService.addQuartzJob(name, jobDto.getCronString());
            boolean activated = job.getActivated();
            if (activated) {
                quartzService.activateTrigger(job.getName());
            } else {
                quartzService.pauseTrigger(job.getName());
            }
        } catch (Exception e) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR);
        }

        JobResDto response = JobResDto.from(job);
        response.setNextFireAt(nextFireTime);
        return response;
    }

    @Transactional(readOnly = true)
    public Job getJobByName(String name) {
        return customJobRepository.retrieveJobByName(name)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, "존재하지 않는 job입니다."));
    }

    public JobResultType executeJob(String jobName, Map<String, Object> params) {
        throwInvalidParameter(jobName, "jobName");

        JobType type;
        LocalDateTime now = LocalDateTime.now();
        Job dbJob = getJobByName(jobName);

        String paramStr;
        // try {
        //     Map<String, Object> dbParams = new HashMap<>();
            // if (StringUtils.hasText(dbJob.getParams())) {
            //     dbParams = new ObjectMapper().readValue(dbJob.(), Map.class);
            // }
            //
            // if (!ObjectUtils.isEmpty(params)) {
            //     dbParams.putAll(params);
            // }
            // params = dbParams;
            paramStr = new JSONObject(params).toString();
        // } catch (JsonProcessingException e) {
        //     // throw new GowidException(ResultCode.INVALID_PARAMETER, "Job Params이 map 아닙니다.");
        // }

        try {
            // 실제 Job 실행
            type = dbJob.getType();
            switch (type) {
                // case TEST:
                //     테스트 메서드 작성 필요
                //     break;
                default:
                    return JobResultType.FAIL;
            }
        } catch (Exception e) {
            log.error("Job 실행 시 에러가 발생하였습니다. message : {}", e.getMessage(), e);
            return JobResultType.FAIL;
        }
        // 성공 시 성공 히스토리 남김
        // return JobResultType.SUCCESS;
    }

    public JobResDto updateJob(JobReqDto jobReqDto) {
        Job dbJob = getJobById(jobReqDto.getId());

        try {
            if (StringUtils.hasText(jobReqDto.getCronString()) && !jobReqDto.getCronString().equals(dbJob.getCronString())) {
                quartzService.updateJob(dbJob.getName(), jobReqDto.getCronString());
            }
            if (StringUtils.hasText(jobReqDto.getCronString())) {
                dbJob.setCronString(jobReqDto.getCronString());
            }
            if (StringUtils.hasText(jobReqDto.getCronDescription())) {
                dbJob.setCronDescription(jobReqDto.getCronDescription());
            }
            if (jobReqDto.getActivated() != null) {
                dbJob.setActivated(jobReqDto.getActivated());
            }
            if (jobReqDto.getParams() != null) {
                dbJob.setParams(new JSONObject(jobReqDto.getParams()).toString());
            }
            boolean activated = jobReqDto.getActivated();
            if (activated) {
                quartzService.activateTrigger(dbJob.getName());
            } else {
                quartzService.pauseTrigger(dbJob.getName());
            }
        } catch (Exception e) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR);
        }
        return JobResDto.from(dbJob);
    }

    public JobResDto updateJobActivation(JobReqDto jobReqDto) {
        // throwInvalidParameter(jobReqDto.getActivated(), "activated");

        Job dbJob = getJobById(jobReqDto.getId());
        if (jobReqDto.getActivated().equals(dbJob.getActivated())) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, "이전 상태와 동일합니다.");
        }

        if (StringUtils.hasText(jobReqDto.getCronString())) {
            dbJob.setCronString(jobReqDto.getCronString());
        }
        if (StringUtils.hasText(jobReqDto.getCronDescription())) {
            dbJob.setCronDescription(jobReqDto.getCronDescription());
        }
        if (jobReqDto.getActivated() != null) {
            dbJob.setActivated(jobReqDto.getActivated());
        }

        try {
            boolean activated = dbJob.getActivated();
            if (activated) {
                quartzService.activateTrigger(dbJob.getName());
            } else {
                quartzService.pauseTrigger(dbJob.getName());
            }
        } catch (Exception e) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR);
        }

        return JobResDto.from(dbJob);
    }

    public Boolean softDeleteJob(Long jobId) {
        Job dbJob = getJobById(jobId);
        if(Boolean.TRUE.equals(dbJob.getDeleted())) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, "이미 삭제된 Job입니다.");
        }

        try {
            quartzService.removeJob(dbJob.getName());
            dbJob.setDeleted(true);
        } catch (Exception e) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR);
        }
        return true;
    }

    public Boolean hardDeleteJob(Long jobId) {
        Job dbJob = customJobRepository.retrieveJobForDelete(jobId)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, "존재하지 않는 job 입니다."));
        if(Boolean.FALSE.equals(dbJob.getDeleted())) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, "soft 삭제가 우선되어야 합니다.");
        }
        jobRepository.delete(dbJob);
        return true;
    }

    private void throwInvalidParameter(Object fieldValue, String fieldName) {
        if (fieldValue == null) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, fieldName + " 필드는 필수 값입니다.");
        }
    }
}
