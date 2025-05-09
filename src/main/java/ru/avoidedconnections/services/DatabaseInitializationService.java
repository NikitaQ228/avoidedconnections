package ru.avoidedconnections.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.nio.file.Paths;
import java.sql.Timestamp;

@Service
public class DatabaseInitializationService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initDatabase() throws IOException {
        if (!userService.addUser(new User("user1", "pass1")))
            return;
        userService.addUser(new User("user2", "pass2"));
        userService.addUser(new User("user3", "pass3"));
        userService.addUser(new User("user4", "pass4"));
        userService.addUser(new User("user5", "pass5"));
        //Добавление историй
        String sql = "INSERT INTO public.story (id, date, user_id, head, city, img, text) VALUES (?, ?, ?, ?, ?, ?, ?)";

        ClassPathResource resource = new ClassPathResource("static/img/metro1.jpg");
        byte[] imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        jdbcTemplate.update(sql, 1, Timestamp.valueOf("2024-03-10 08:15:00"), 1, "Агрессивный попрошайка", "Москва",
                imageBytes,
                "Метро Курская, вагон 5. Мужик лет 50 с бутылкой требует деньги, хватает за руки. Будьте осторожны!");

        resource = new ClassPathResource("static/img/bus42.jpg");
        imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        jdbcTemplate.update(sql, 2, Timestamp.valueOf("2024-03-11 18:30:00"), 2, "Пьяная компания в автобусе", "Санкт-Петербург",
                imageBytes,
                "Маршрут 42, кричали и бросали банки. Двое в камуфляжных штанах.");

        resource = new ClassPathResource("static/img/scam.jpg");
        imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        jdbcTemplate.update(sql, 3, Timestamp.valueOf("2024-03-12 12:00:00"), 3, "Лже-волонтер", "Екатеринбург",
                imageBytes,
                "У ТЦ \"Гринвич\" собирает деньги на несуществующий приют. Проверяйте документы!");

        resource = new ClassPathResource("static/img/park.jpg");
        imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        jdbcTemplate.update(sql, 4, Timestamp.valueOf("2024-02-28 20:45:00"), 4, "Наркоман у детской площадки", "Казань",
                imageBytes,
                "Сидит возле горки, что-то колет шприцом. Вызвали полицию.");

        resource = new ClassPathResource("static/img/train.jpg");
        imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        jdbcTemplate.update(sql, 5, Timestamp.valueOf("2024-03-01 07:15:00"), 5, "Воришка в электричке", "Нижний Новгород",
                imageBytes,
                "Курсирует между Дзержинском и НН. Крадет телефоны у спящих.");

        resource = new ClassPathResource("static/img/scam2.jpg");
        imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        jdbcTemplate.update(sql, 6, Timestamp.valueOf("2024-03-05 09:00:00"), 1, "Мошенник с \"упавшими деньгами\"", "Новосибирск",
                imageBytes,
                "Возле вокзала пытается развести на деньги по старой схеме.");

        resource = new ClassPathResource("static/img/gopniki.jpg");
        imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        jdbcTemplate.update(sql, 7, Timestamp.valueOf("2024-03-08 22:10:00"), 2, "Гопники у метро Пушкинская", "Ростов-на-Дону",
                imageBytes,
                "Требуют \"покурить\", преследуют до остановки.");

        resource = new ClassPathResource("static/img/park_night.jpg");
        imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        jdbcTemplate.update(sql, 8, Timestamp.valueOf("2024-03-09 15:30:00"), 3, "Бомж с ножом в парке", "Сочи",
                imageBytes,
                "Спит на лавочке, при приближении достает перочинный нож.");

        resource = new ClassPathResource("static/img/witch.jpg");
        imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        jdbcTemplate.update(sql, 9, Timestamp.valueOf("2024-02-25 19:20:00"), 4, "Навязчивая гадалка", "Владивосток",
                imageBytes,
                "У центрального рынка хватает за руки, пугает \"проклятиями\".");

        resource = new ClassPathResource("static/img/minibus.jpg");
        imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        jdbcTemplate.update(sql, 10, Timestamp.valueOf("2024-03-07 11:45:00"), 5, "Пьяный водитель маршрутки", "Калининград",
                imageBytes,
                "Маршрут 145, ехал зигзагами, орал песни. Номера А123ВС 39 RUS.");

        executeSqlScript("script.sql");
    }

    private void executeSqlScript(String fileName) throws IOException {
        String strSQL = null;
        ClassPathResource classPathResource = new ClassPathResource("script.sql");
        try {
            byte[] binaryData = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            strSQL = new String(binaryData, StandardCharsets.UTF_8);
            jdbcTemplate.execute(strSQL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
