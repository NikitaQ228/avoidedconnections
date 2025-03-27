package ru.avoidedconnections.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.model.User;
import ru.avoidedconnections.repository.StoryRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserService userService;

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
}
