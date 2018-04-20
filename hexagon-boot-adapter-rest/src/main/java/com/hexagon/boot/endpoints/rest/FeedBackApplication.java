package com.hexagon.boot.endpoints.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hexagon.boot.endpoints"})
@EnableJpaRepositories("com.hexagon.boot.endpoints.repository")
@EntityScan("com.hexagon.boot.endpoints.repository")
public class FeedBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeedBackApplication.class);
    }
}
