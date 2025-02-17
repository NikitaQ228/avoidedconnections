package ru.avoidedconnections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// http://localhost:8080/swagger-ui/index.html
@SpringBootApplication
public class AvoidedConnectionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AvoidedConnectionsApplication.class, args);
    }
}