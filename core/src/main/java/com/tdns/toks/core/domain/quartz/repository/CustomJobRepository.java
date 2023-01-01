package com.tdns.toks.core.domain.quartz.repository;

import java.util.Optional;

import com.tdns.toks.core.domain.quartz.entity.Job;
import com.tdns.toks.core.domain.quartz.type.JobType;

public interface CustomJobRepository {

	Optional<Job> retrieveJobById(Long id);

	Optional<Job> retrieveJobByName(String name);

	Boolean existsJobByType(JobType jobType);

	Optional<Job> retrieveJobForDelete(Long id);

}
