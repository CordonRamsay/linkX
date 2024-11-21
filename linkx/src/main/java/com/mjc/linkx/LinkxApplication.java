package com.mjc.linkx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LinkxApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkxApplication.class, args);
    }

}
