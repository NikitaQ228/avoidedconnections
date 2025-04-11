package ru.avoidedconnections.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Найти пользователя по имени.
     * Автоматически генерируется SQL-запрос: SELECT * FROM user WHERE name = ?
     */
    Optional<User> findByName(String username);

    /**
     * Проверить, существует ли пользователь с данным именем.
     * Автоматически генерируется SQL-запрос: SELECT COUNT(*) > 0 FROM user WHERE name = ?
     */
    boolean existsByName(String username);

    /**
     * Найти все истории, связанные с конкретным пользователем.
     * Автоматически генерируется SQL-запрос на основе связи.
     */
//    List<Story> findStoriesById(Long userId);
    @EntityGraph(attributePaths = "stories")
    Optional<User> findById(@NonNull Long userId);
}

