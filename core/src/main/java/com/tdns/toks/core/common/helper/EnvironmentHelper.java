package com.tdns.toks.core.common.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EnvironmentHelper {

    @Autowired
    private Environment environment;
    public boolean isLocalProfile() {
        return Arrays.asList(environment.getActiveProfiles()).contains("local");
    }

    public boolean isTestProfile() {
        return Arrays.asList(environment.getActiveProfiles()).contains("test");
    }

    public boolean isLocalTestProfile() {
        return isTestProfile() || isLocalProfile();
    }
}
