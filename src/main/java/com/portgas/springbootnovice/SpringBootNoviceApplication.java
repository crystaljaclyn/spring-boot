package com.portgas.springbootnovice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;

@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class, TransactionAutoConfiguration.class}
        )
public class SpringBootNoviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNoviceApplication.class, args);
    }

}
