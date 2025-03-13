package ru.avoidedconnections.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.avoidedconnections.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class DatabaseInitializationService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initDatabase() {
        if (!userService.addUser(new User("user1", "pass1")))
            return;
        userService.addUser(new User("user2", "pass2"));
        userService.addUser(new User("user3", "pass3"));
        userService.addUser(new User("user4", "pass4"));
        userService.addUser(new User("user5", "pass5"));
        executeSqlScript("script.sql");
    }

    private void executeSqlScript(String fileName) {
        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            String sql = new String(Files.readAllBytes(file.toPath()));
            jdbcTemplate.execute(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
