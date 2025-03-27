package ru.avoidedconnections.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.StoryResponse;
import ru.avoidedconnections.model.Comment;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.services.StoryService;
import ru.avoidedconnections.repository.StoryRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/story")
public class StoryController {
    @Autowired
    private StoryService storyService;

    private final StoryRepository storyRepository;

    public StoryController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @GetMapping("/{storyId}")
    public StoryResponse storyInfo(@PathVariable(name = "storyId") Long storyId) {
        return storyService.getStoryById(storyId);
    }

    @GetMapping("/{storyId}/comment")
    public List<Comment> storyComment(@PathVariable(name = "storyId") Long storyId) {
        return null;
    }

    @PostMapping("/{storyId}/addComment")
    public void storyAddComment(@PathVariable Long storyId,
                                @RequestBody String commentText) {

    }

    @DeleteMapping("/{storyId}/delete")
    public void storyDelete(@PathVariable(name = "storyId") Long storyId) {

    }

    // Добавлено для теста
    @GetMapping
    public List<Story> getAll() {
        return storyRepository.findAll();
    }
}
