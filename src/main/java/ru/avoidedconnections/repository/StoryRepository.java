package ru.avoidedconnections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avoidedconnections.model.Story;

import java.util.List;
import java.util.Optional;

//todo пагинация: позволяет получать истории порциями
@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    // findById(Long id) реализован в интерфейсе CrudRepository

    @Query("SELECT s FROM Story s WHERE (:city IS NULL OR s.city LIKE :city) "
            + "AND (:query IS NULL OR (LOWER(s.head) LIKE LOWER(CONCAT('%', :query, '%')) "
            + "OR LOWER(s.text) LIKE LOWER(CONCAT('%', :query, '%')))) ORDER BY s.date DESC")
    List<Story> searchStories(@Param("city") String city, @Param("query") String query);

    @Query("SELECT s FROM Story s WHERE s.city = :city ORDER BY s.date DESC")
    List<Story> findStoriesByCity(@Param("city") String city);

    @Query("SELECT s FROM Story s ORDER BY s.date DESC")
    List<Story> findAllStoriesSortedByDate();

    List<Story> findByAuthorId(Long authorId);
}
