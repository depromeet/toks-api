package com.tdns.toks.core.config.jpa;

import com.tdns.toks.BasePackageLocation;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan(basePackageClasses = BasePackageLocation.class)
@EnableJpaRepositories(basePackageClasses = BasePackageLocation.class)
public class JpaConfig {
}
