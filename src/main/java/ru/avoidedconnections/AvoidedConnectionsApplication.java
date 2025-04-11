package ru.avoidedconnections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.avoidedconnections.model.Comment;
import ru.avoidedconnections.repository.CommentRepository;

// http://localhost:8080/swagger-ui/index.html
@SpringBootApplication
@EnableScheduling
public class AvoidedConnectionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AvoidedConnectionsApplication.class, args);
    }
}