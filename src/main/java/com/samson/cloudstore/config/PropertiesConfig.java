package com.samson.cloudstore.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ AppCorsProperties.class })
public class PropertiesConfig { }
