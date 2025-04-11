package ru.avoidedconnections.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.model.Story;

import java.util.List;
import java.util.Optional;

//todo пагинация: позволяет получать истории порциями
@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    /**
     * Поиск историй по городу и запросу с учетом null значений.
     */
    default List<Story> searchStories(String city, String query) {
        if (city == null && query == null) {
            return findAll(Sort.by(Sort.Direction.DESC, "date"));
        } else if (city == null) {
            return findAllByHeadContainingIgnoreCaseOrTextContainingIgnoreCase(query, query, Sort.by(Sort.Direction.DESC, "date"));
        } else if (query == null) {
            return findAllByCity(city, Sort.by(Sort.Direction.DESC, "date"));
        } else {
            return findAllByCityAndHeadContainingIgnoreCaseOrCityAndTextContainingIgnoreCase(
                    city, query, city, query, Sort.by(Sort.Direction.DESC, "date"));
        }
    }

    /**
     * Поиск историй по городу.
     */
    List<Story> findAllByCity(String city, Sort sort);

    /**
     * Поиск историй по части текста или заголовка (без учета регистра).
     */
    List<Story> findAllByHeadContainingIgnoreCaseOrTextContainingIgnoreCase(String headQuery, String textQuery, Sort sort);

    /**
     * Поиск историй по городу и части текста или заголовка.
     */
    List<Story> findAllByCityAndHeadContainingIgnoreCaseOrCityAndTextContainingIgnoreCase(
            String city1, String headQuery, String city2, String textQuery, Sort sort);

    /**
     * Получение всех историй, отсортированных по дате.
     */
    List<Story> findAll(Sort sort);

    /**
     * Получение историй по автору.
     */
    List<Story> findByAuthorId(Long authorId);
}
