package ru.avoidedconnections.controller;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.UserResponse;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.model.User;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("/info/{userId}")
    public UserResponse profileInfoUser(@PathVariable(name = "userId") Long userId) {
        return null;
    }

    @GetMapping("/info")
    public UserResponse profileMyUser() {
        return null;
    }

    @PutMapping("/changeIcon")
    public void profileChangeIcon(@RequestBody String icon) {

    }
}
