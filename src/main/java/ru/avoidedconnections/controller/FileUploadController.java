package ru.avoidedconnections.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// UNUSED
@RestController
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.url.prefix:/uploads}")
    private String urlPrefix;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                logger.warn("Попытка загрузить пустой файл");
                return ResponseEntity.badRequest().body(Map.of("error", "Файл пустой"));
            }

            // Создаем директорию, если она не существует
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
                logger.info("Создана директория для загрузок: {}", uploadPath);
            }

            // Генерируем уникальное имя файла
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                originalFilename = "file"; // Дефолтное имя если оригинальное пустое
            }

            String fileExtension = "";
            int lastDotIndex = originalFilename.lastIndexOf('.');
            if (lastDotIndex >= 0) {
                fileExtension = originalFilename.substring(lastDotIndex);
                originalFilename = originalFilename.substring(0, lastDotIndex);
            }

            // Безопасное имя файла: timestamp + случайный UUID + исходное имя + расширение
            String safeFileName = System.currentTimeMillis() + "_" +
                    UUID.randomUUID().toString().substring(0, 8) + "_" +
                    originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_") +
                    fileExtension;

            // Полный путь к файлу
            Path targetLocation = Paths.get(uploadPath).resolve(safeFileName);

            // Сохраняем файл
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Файл успешно сохранен: {}", targetLocation);

            // URL к файлу для фронтенда
            String fileUrl = urlPrefix + "/" + safeFileName;

            // Формируем ответ
            Map<String, String> response = new HashMap<>();
            response.put("fileName", safeFileName);
            response.put("fileUrl", fileUrl);
            response.put("originalFileName", file.getOriginalFilename());

            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            logger.error("Ошибка при сохранении файла: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Не удалось сохранить файл: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
