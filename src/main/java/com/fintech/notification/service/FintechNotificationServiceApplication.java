package com.fintech.notification.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,SecurityAutoConfiguration.class})
public class FintechNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FintechNotificationServiceApplication.class, args);
    }

}
