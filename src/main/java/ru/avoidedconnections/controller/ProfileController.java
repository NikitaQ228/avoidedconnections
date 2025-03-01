package ru.avoidedconnections.controller;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.model.User;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("/info/{userId}")
    public User profileInfoUser(@PathVariable(name = "userId") Long userId) {
        return null;
    }

    @GetMapping("/info/{userId}/stories")
    public List<Story> profileStories(@PathVariable(name = "userId") Long userId) {
        return null;
    }

    @GetMapping("/info")
    public User profileMyUser() {
        return null;
    }

    @GetMapping("/info/stories")
    public List<Story> profileMyStories() {
        return null;
    }

    @PutMapping("/changeIcon")
    public void profileChangeIcon(@RequestBody String icon) {

    }
}
