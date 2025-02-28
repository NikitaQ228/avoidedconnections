package ru.avoidedconnections.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.avoidedconnections.model.Story;

@RestController
@RequestMapping("/addStory")
public class AddStoryController {

    @PostMapping("/new")
    public void createStory(@RequestBody Story story) {

    }

    //todo получение пользователя по почте? Как будет происходить тегирование?

}
