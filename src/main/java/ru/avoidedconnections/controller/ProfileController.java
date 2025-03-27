package ru.avoidedconnections.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.dto.UserDTO;
import ru.avoidedconnections.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    public UserService userService;

    @GetMapping("/user/{userId}")
    public UserDTO profileInfoUser(@PathVariable(name = "userId") Long userId) {
        return userService.getUserInfo(userId);
    }

    @GetMapping("/user")
    public UserDTO profileMyUser() {
        return userService.getUserInfo();
    }

    @GetMapping("/story/{userId}")
    public List<StoryDTO> getStoriesByAuthor(@PathVariable(name = "userId") Long userId) {
        return null;
    }

    @GetMapping("/story")
    public List<StoryDTO> getMyStories() {
        return null;
    }

    @GetMapping("/storyTag/{userId}")
    public List<StoryDTO> getStoriesTag(@PathVariable(name = "userId") Long userId) {
        return null;
    }

    @GetMapping("/storyTag")
    public List<StoryDTO> getStoriesMyTag() {
        return null;
    }

    @PutMapping("/changeIcon")
    public void profileChangeIcon(@RequestBody String icon) {

    }
}
