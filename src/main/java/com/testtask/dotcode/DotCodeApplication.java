package com.testtask.dotcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class DotCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DotCodeApplication.class, args);
    }

}
