package ru.avoidedconnections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.ChangePasswordDTO;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.dto.StoryResponse;
import ru.avoidedconnections.dto.UserDTO;
import ru.avoidedconnections.services.StoryService;
import ru.avoidedconnections.services.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {
    public final UserService userService;
    public final StoryService storyService;

    @GetMapping("/user/{userId}")
    public UserDTO profileInfoUser(@PathVariable(name = "userId") Long userId) {
        return userService.getUserInfo(userId);
    }

    @GetMapping("/user")
    public UserDTO profileMyUser() {
        return userService.getUserInfo();
    }

    @GetMapping("/users")
    public List<UserDTO> profileUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/story/{userId}")
    public List<StoryResponse> getStoriesByAuthor(@PathVariable(name = "userId") Long userId) {
        return storyService.getListStoryByAuthor(userId);
    }

    @GetMapping("/story")
    public List<StoryResponse> getMyStories() {
        return storyService.getListMyStory();
    }

    @GetMapping("/storyTag/{userId}")
    public List<StoryResponse> getStoriesTag(@PathVariable(name = "userId") Long userId) {
        return storyService.getListStoryTag(userId);
    }

    @GetMapping("/storyTag")
    public List<StoryResponse> getStoriesMyTag() {
        return storyService.getListMyStoryTag();
    }

    @PutMapping("/changeIcon")
    public void profileChangeIcon(@RequestBody String icon) {
        userService.changeIcon(icon);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return userService.changePassword(changePasswordDTO);
    }

}
