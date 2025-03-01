package ru.avoidedconnections.controller;

import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.model.User;

@RestController
@RequestMapping("/addStory")
public class AddStoryController {

    @PostMapping("/new")
    public void createStory(@RequestBody Story story) {

    }

    @GetMapping("/userTag")
    public User addUserTag(@RequestBody String login) {
        return null;
    }
    //todo получение пользователя по логину? Как будет происходить тегирование?

}
