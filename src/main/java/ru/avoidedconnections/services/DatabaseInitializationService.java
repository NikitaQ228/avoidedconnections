package ru.avoidedconnections.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import ru.avoidedconnections.model.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class DatabaseInitializationService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    ResourceLoader resourceLoader;

    @PostConstruct
    public void initDatabase() throws IOException {
        if (!userService.addUser(new User("user1", "pass1")))
            return;
        userService.addUser(new User("user2", "pass2"));
        userService.addUser(new User("user3", "pass3"));
        userService.addUser(new User("user4", "pass4"));
        userService.addUser(new User("user5", "pass5"));
        executeSqlScript("script.sql");
    }

    private void executeSqlScript(String fileName) throws IOException {
        String strSQL = null;
        ClassPathResource classPathResource = new ClassPathResource("script.sql");
        try {
            byte[] binaryData = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            strSQL = new String(binaryData, "Windows-1251");
            jdbcTemplate.execute(strSQL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
