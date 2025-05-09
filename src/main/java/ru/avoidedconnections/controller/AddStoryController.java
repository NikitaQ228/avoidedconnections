package ru.avoidedconnections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.services.StoryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/addStory")
public class AddStoryController {
    private final StoryService storyService;

    @PostMapping("/new")
    public ResponseEntity<Long> createStory(@RequestBody StoryDTO storyDTO) {
        return new ResponseEntity<>(storyService.addStory(storyDTO), HttpStatus.CREATED);
    }

}
