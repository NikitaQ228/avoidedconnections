package ru.avoidedconnections.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.model.User;
import ru.avoidedconnections.repository.StoryRepository;
import ru.avoidedconnections.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryService {
    private final StoryRepository storyRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public StoryService(StoryRepository storyRepository, UserService userService, UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public StoryDTO addStory(StoryDTO storyDTO) {
        Story story = new Story(
                storyDTO.getId(),
                storyDTO.getHead(),
                storyDTO.getImg(),
                storyDTO.getText(),
                storyDTO.getDate(),
                storyDTO.getCity(),
                userService.getUserByDTO(storyDTO.getAuthor()),
                storyDTO.getUsersTag().stream().map(x -> userService.getUserByName(x.getName())).collect(Collectors.toList())
        );
        storyRepository.save(story);
        return new StoryDTO(story);
    }

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