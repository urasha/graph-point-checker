package ru.urasha.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.urasha", "ru.urasha.userservice"})
public class WebSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSpringApplication.class, args);
    }
}
