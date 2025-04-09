package com.goblin.openchatservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class OpenChatServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenChatServiceApplication.class, args);
    }

}
