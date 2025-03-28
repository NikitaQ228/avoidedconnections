package ru.avoidedconnections.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.repository.StoryRepository;
import ru.avoidedconnections.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public StoryDTO getStoryById(Long id) {
        return storyRepository.findById(id).map(StoryDTO::new).orElseThrow(
                () -> new RuntimeException("Story not found with id: " + id)
        );
    }

    public List<StoryDTO> getListStoryByAuthor(Long id) {
        return Collections.unmodifiableList(
                storyRepository.findByAuthorId(id).stream()
                        .map(StoryDTO::new)
                        .collect(Collectors.toList())
        );
    }

    public List<StoryDTO> getListMyStory() {
        return Collections.unmodifiableList(
                storyRepository.findByAuthorId(userService.getCurrentUser().getId()).stream()
                        .map(StoryDTO::new)
                        .collect(Collectors.toList())
        );
    }

    public List<StoryDTO> getListStoryTag(Long id) {
        return Collections.unmodifiableList(
                        userRepository.findStoriesByUserId(id).stream()
                        .map(StoryDTO::new)
                        .collect(Collectors.toList())
        );
    }

    public List<StoryDTO> getListMyStoryTag() {
        return Collections.unmodifiableList(
                userRepository.findStoriesByUserId(userService.getCurrentUser().getId()).stream()
                        .map(StoryDTO::new)
                        .collect(Collectors.toList())
        );
    }
}