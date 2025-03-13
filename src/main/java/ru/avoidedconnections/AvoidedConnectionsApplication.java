package ru.avoidedconnections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.avoidedconnections.model.Comment;
import ru.avoidedconnections.repository.CommentRepository;

// http://localhost:8080/swagger-ui/index.html
@SpringBootApplication
public class AvoidedConnectionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AvoidedConnectionsApplication.class, args);
    }
}