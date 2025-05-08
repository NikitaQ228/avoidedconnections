package ru.avoidedconnections.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.nio.file.Paths;

// UNUSED
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Конвертируем в абсолютный путь с file: протоколом и убеждаемся, что слеш в конце есть
        String path = Paths.get(uploadDir).toAbsolutePath().toString();
        if (!path.endsWith(File.separator)) path += File.separator;
        
        registry.addResourceHandler("/"+ uploadDir +"/**")
                .addResourceLocations("file:" + path)
                .setCachePeriod(0)  // Отключаем кэширование для динамических файлов
                .resourceChain(false); // Отключаем цепочку ресурсов для прямого доступа
    }
}