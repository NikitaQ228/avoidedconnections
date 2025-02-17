package ru.avoidedconnections.controller;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.model.User;

@RestController
@RequestMapping("/entry")
public class UserController {

    @PostMapping("/addUser")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        return null;
    }

    //todo аунтификация

}
