package com.portgas.springbootnovice.config.database;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSourceProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Integer maxConnectionPool;
}
