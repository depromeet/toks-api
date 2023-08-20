package com.tdns.toks.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "fab.form")
public class FabProperties {
    private String title;
    private String description1;
    private String description2;
}
