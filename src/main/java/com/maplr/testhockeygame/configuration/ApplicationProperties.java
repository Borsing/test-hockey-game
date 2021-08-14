package com.maplr.testhockeygame.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicationProperties {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.application.version}")
    private String version;

    @Value("${spring.application.description}")
    private String description;
}
