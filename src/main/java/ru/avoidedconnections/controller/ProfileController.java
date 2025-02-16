package ru.avoidedconnections.controller;

import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.model.User;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("/info/{userId}")
    public User profilePage(@PathVariable(name = "userId") Long userId) {
        return null;
    }

    @GetMapping("/info")
    public User profileMyPage() {
        return null;
    }
}
