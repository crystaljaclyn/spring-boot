package com.portgas.springbootnovice.config.database;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DatasourceType {
    DBType dbType();
}
