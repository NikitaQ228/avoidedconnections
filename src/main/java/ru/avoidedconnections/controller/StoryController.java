package ru.avoidedconnections.controller;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.model.Comment;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.repository.StoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/story")
public class StoryController {
    private final StoryRepository storyRepository;

    public StoryController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @GetMapping("/{storyId}")
    public Story storyPage(@PathVariable(name = "storyId") Long storyId) {
        return null;
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
