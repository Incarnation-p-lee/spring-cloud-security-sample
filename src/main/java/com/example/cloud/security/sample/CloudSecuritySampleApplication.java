package com.example.cloud.security.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@RestController
@EnableResourceServer
public class CloudSecuritySampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudSecuritySampleApplication.class, args);
    }

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/test")
    public String test() {
        return "greeting";
    }
}
