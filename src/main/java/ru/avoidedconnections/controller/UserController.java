package ru.avoidedconnections.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.model.User;
import ru.avoidedconnections.services.UserService;

@RestController
@RequestMapping("/entry")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        if (userService.addUser(user))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
