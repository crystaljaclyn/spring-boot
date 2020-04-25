package com.portgas.springbootnovice.config;


import com.portgas.springbootnovice.config.datasource.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConfigProperties {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public Map<String,DataSourceProperties> dataSourceProperties() {
        return new HashMap<>();
    }
}
