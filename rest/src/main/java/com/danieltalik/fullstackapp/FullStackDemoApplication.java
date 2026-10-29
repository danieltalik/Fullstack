package com.danieltalik.fullstackapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class FullStackDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullStackDemoApplication.class, args);
    }

    /** Used to work out ages; tests can swap in a fixed clock. */
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
