package ru.avoidedconnections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avoidedconnections.model.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

}
