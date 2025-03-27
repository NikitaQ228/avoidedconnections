package ru.avoidedconnections.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.repository.StoryRepository;

import java.util.Optional;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    public StoryDTO getStoryById(Long id) {
        return storyRepository.findById(id).map(StoryDTO::new).orElseThrow(
                () -> new RuntimeException("Story not found with id: " + id)
        );
    }
}