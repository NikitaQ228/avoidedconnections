package ru.avoidedconnections.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.repository.StoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {
    private final StoryRepository storyRepository;

    public MainController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @GetMapping("/mainInfo")
    public List<StoryDTO> mainInfoPage(@RequestParam(required = false) String city,
                                       @RequestParam(required = false) String query) {
        if (city == null) {
            return storyRepository.findAllStoriesSortedByDate().stream().map(StoryDTO::new).collect(Collectors.toList());
        } else {
            if (query == null) {
                return storyRepository.findStoriesByCity(city).stream().map(StoryDTO::new).collect(Collectors.toList());
            } else {
                return storyRepository.searchStories(city, query).stream().map(StoryDTO::new).collect(Collectors.toList());
            }
        }
    }

}
