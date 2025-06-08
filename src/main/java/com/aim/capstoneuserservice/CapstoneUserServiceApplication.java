package com.aim.capstoneuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CapstoneUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapstoneUserServiceApplication.class, args);
    }

}
