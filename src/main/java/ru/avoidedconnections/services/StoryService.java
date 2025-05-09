package ru.avoidedconnections.services;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.dto.StoryResponse;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.model.User;
import ru.avoidedconnections.repository.StoryRepository;
import ru.avoidedconnections.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
public class StoryService {
    private final StoryRepository storyRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public StoryService(StoryRepository storyRepository, UserService userService, UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public StoryDTO deleteStory(Long storyId) {
        var storyDTO = storyRepository.findById(storyId).map(StoryDTO::new).orElseThrow(
                () -> new ObjectNotFoundException(storyId, "История "));
        storyRepository.deleteById(storyId);
        return storyDTO;
    }

    public Long addStory(StoryDTO storyDTO)  {
        Story story = new Story(
                storyDTO.getId(),
                storyDTO.getHead(),
                storyDTO.getImg(),
                storyDTO.getText(),
                new Date(),
                storyDTO.getCity(),
                userService.getCurrentUser(),
                storyDTO.getUsersTag().stream().map(x -> userService.getUserByName(x.getName())).collect(Collectors.toList())
        );
        storyRepository.save(story);
        return story.getId();
    }

    public Story getStoryById(Long id) {
        return storyRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(id, "История ")
        );
    }

    public List<StoryResponse> getStoryByQuery(String city, String query) {
        return storyRepository.searchStories(city, query).stream().map(StoryResponse::new).collect(Collectors.toList());
    }

    public List<StoryResponse> getListStoryByAuthor(Long id) {
        return storyRepository.findByAuthorId(id).stream()
                .map(StoryResponse::new).toList();
    }

    public List<StoryResponse> getListMyStory() {
        return storyRepository.findByAuthorId(userService.getCurrentUser().getId()).stream()
                .map(StoryResponse::new).toList();
    }

    public List<StoryResponse> getListStoryTag(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> storyRepository.findByUsersTagId(value.getId()).stream()
                .map(StoryResponse::new)
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    public List<StoryResponse> getListMyStoryTag() {
        Optional<User> user = userRepository.findById(userService.getCurrentUser().getId());
        return user.map(value -> storyRepository.findByUsersTagId(value.getId()).stream()
                .map(StoryResponse::new)
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    public ResponseEntity<byte[]> getImage(Long id) {
        // Получаем историю по id
        Optional<Story> optionalStory = storyRepository.findById(id);

        if (optionalStory.isEmpty() || optionalStory.get().getImg() == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] image = optionalStory.get().getImg();
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
