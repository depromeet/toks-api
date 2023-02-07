package com.tdns.toks.api.config.cruiser;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

@Data
@Configuration
@ConfigurationProperties(prefix = "monitor.slack")
public class CruiserProperties {
    @NotBlank
    private String cruiser;
}
