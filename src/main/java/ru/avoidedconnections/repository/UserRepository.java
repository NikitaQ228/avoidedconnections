package ru.avoidedconnections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avoidedconnections.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
