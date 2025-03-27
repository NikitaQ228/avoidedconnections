package ru.avoidedconnections.controller;

import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.UserDTO;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("/info/{userId}")
    public UserDTO profileInfoUser(@PathVariable(name = "userId") Long userId) {
        return null;
    }

    @GetMapping("/info")
    public UserDTO profileMyUser() {
        return null;
    }

    @PutMapping("/changeIcon")
    public void profileChangeIcon(@RequestBody String icon) {

    }
}
