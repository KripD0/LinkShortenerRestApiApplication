package com.example.linkshortenerrestapiapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LinkShortenerRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkShortenerRestApiApplication.class, args);
    }

}
