package com.core_will_soft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GitHubUserServiceApp {
    public static void main(final String[] args) {
        SpringApplication.run(GitHubUserServiceApp.class, args);
    }
}
