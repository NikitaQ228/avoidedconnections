package ru.avoidedconnections.controller;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.avoidedconnections.model.Story;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @GetMapping("/mainInfo")
    public List<Story> mainInfoPage(@RequestParam(required = false) String city,
                                    @RequestParam(required = false) String query) {
        return null;
    }

}
