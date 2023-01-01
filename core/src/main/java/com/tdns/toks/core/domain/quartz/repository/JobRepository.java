package com.tdns.toks.core.domain.quartz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tdns.toks.core.domain.quartz.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

}
