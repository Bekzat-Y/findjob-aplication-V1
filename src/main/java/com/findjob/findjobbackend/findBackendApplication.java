package com.findjob.findjobbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = "com.findjob.findjobbackend.entity")
public class findBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(findBackendApplication.class, args);
    }

    }

