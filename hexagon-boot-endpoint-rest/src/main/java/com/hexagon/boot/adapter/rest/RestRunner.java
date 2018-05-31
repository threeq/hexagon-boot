package com.hexagon.boot.adapter.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hexagon.boot"})
@EnableJpaRepositories("com.hexagon.boot.adapter.repository")
@EntityScan("com.hexagon.boot.adapter.repository")
public class RestRunner {
    public static void main(String[] args) {
        SpringApplication.run(RestRunner.class);
    }
}
