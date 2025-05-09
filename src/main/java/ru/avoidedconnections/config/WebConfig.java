package ru.avoidedconnections.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // для всех эндпоинтов
                .allowedOrigins("http://localhost:8081") // фронтенд
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // методы
                .allowedHeaders("Authorization", "Content-Type", "Accept") // разрешённые заголовки
                .allowCredentials(true) // разрешить куки и авторизацию
                .maxAge(3600); // кеширование preflight на 1 час
    }
}

