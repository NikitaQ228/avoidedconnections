package ru.avoidedconnections.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.UserDTO;
import ru.avoidedconnections.repository.UserRepository;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/info/{userId}")
    public UserDTO profileInfoUser(@PathVariable(name = "userId") Long userId) {
        var user = userRepository.findById(userId);
        return user.map(UserDTO::new).orElse(null);
    }

    @GetMapping("/info")
    public UserDTO profileMyUser(HttpSession httpSession) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            var contextUser = userRepository.findByName(username);
            return contextUser.map(UserDTO::new).orElse(null);
        }
        return null;
    }

    @PutMapping("/changeIcon")
    public void profileChangeIcon(@RequestBody String icon) {

    }
}
