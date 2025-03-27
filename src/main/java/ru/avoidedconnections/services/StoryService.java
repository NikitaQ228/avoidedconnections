package ru.avoidedconnections.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avoidedconnections.dto.StoryResponse;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.repository.StoryRepository;

import java.util.Optional;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    public StoryResponse getStoryById(Long id) {
        Optional<Story> story =  storyRepository.findById(id);
        return story.map(StoryResponse::new).orElseThrow(
                () -> new RuntimeException("Story not found with id: " + id)
        );
    }
}
