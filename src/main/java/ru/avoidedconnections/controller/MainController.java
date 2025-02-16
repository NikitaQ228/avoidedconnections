package ru.avoidedconnections.controller;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.avoidedconnections.model.Story;

import java.util.List;

@RestController
@RequestMapping("/home")
public class MainController {

    @GetMapping("/mainInfo")
    public Pair<String, List<Story>> mainInfoPage() {
        return null;
    }

    //todo Фильтры и поиск
    //todo чем отличаются get и put?

}
