package ru.avoidedconnections;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// http://localhost:8080/swagger-ui/index.html
@SpringBootApplication
@EnableScheduling
public class AvoidedConnectionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AvoidedConnectionsApplication.class, args);
    }
}