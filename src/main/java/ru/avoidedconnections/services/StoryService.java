package ru.avoidedconnections.services;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.model.User;
import ru.avoidedconnections.repository.StoryRepository;
import ru.avoidedconnections.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
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

    @Value("${upload.path}")
    private String uploadPath;

    public StoryService(StoryRepository storyRepository, UserService userService, UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public StoryDTO deleteStory(Long storyId) {
        var storyDTO = storyRepository.findById(storyId).map(StoryDTO::new).orElseThrow(() -> new
                ObjectNotFoundException(storyId, "История "));
        storyRepository.deleteById(storyId);
        return storyDTO;
    }

    public StoryDTO addStory(StoryDTO storyDTO)  {
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
        return new StoryDTO(story);
    }

    public Story getStoryById(Long id) {
        return storyRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Story not found with id: " + id)
        );
    }

    public List<StoryDTO> getStoryByQuery(String city, String query) {
        if (city == null) {
            if (query == null) {
                return storyRepository.findAllStoriesSortedByDate().stream().map(StoryDTO::new).collect(Collectors.toList());
            } else {
                return storyRepository.findStoriesByQuery(query).stream().map(StoryDTO::new).collect(Collectors.toList());
            }
        } else {
            if (query == null) {
                return storyRepository.findStoriesByCity(city).stream().map(StoryDTO::new).collect(Collectors.toList());
            } else {
                return storyRepository.searchStories(city, query).stream().map(StoryDTO::new).collect(Collectors.toList());
            }
        }
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

    public ResponseEntity<String> uploadImage(MultipartFile image) {
        try {
            if (image.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The file is empty");
            }

            Path path = Paths.get(uploadPath + image.getOriginalFilename());
            Files.write(path, image.getBytes());
            return ResponseEntity.ok(image.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when uploading a file: " + e.getMessage());
        }
    }
}
