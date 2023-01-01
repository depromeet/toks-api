package com.tdns.toks.core.domain.quartz.repository;

import static com.tdns.toks.core.domain.quartz.entity.QJob.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tdns.toks.core.domain.quartz.entity.Job;
import com.tdns.toks.core.domain.quartz.type.JobType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomJobRepositoryImpl implements CustomJobRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<Job> retrieveJobById(Long id) {
		return Optional.ofNullable(
			jpaQueryFactory.selectFrom(job)
				.where(
					job.id.eq(id),
					job.deleted.eq(false)
				)
				.fetchFirst()
		);
	}

	@Override
	public Optional<Job> retrieveJobByName(String name) {
		return Optional.ofNullable(
			jpaQueryFactory.selectFrom(job)
				.where(
					job.name.eq(name),
					job.deleted.eq(false)
				)
				.fetchFirst()
		);
	}

	@Override
	public Boolean existsJobByType(JobType jobType) {
		return jpaQueryFactory
			.select(job.id)
			.from(job)
			.where(
				job.type.eq(jobType),
				job.deleted.eq(false)
			)
			.fetchFirst() != null;
	}

	@Override
	public Optional<Job> retrieveJobForDelete(Long id) {
		return Optional.ofNullable(
			jpaQueryFactory
				.selectFrom(job)
				.where(
					job.id.eq(id),
					job.deleted.eq(true)
				)
				.fetchFirst()
		);
	}
}
