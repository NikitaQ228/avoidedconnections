package ru.avoidedconnections.repository;

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
    Optional<User> findByName(String username);

    boolean existsByName(String username);

    @Query("SELECT u.stories FROM User u WHERE u.id = :userId")
    List<Story> findStoriesByUserId(@Param("userId") Long userId);
}
