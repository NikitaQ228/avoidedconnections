package ru.avoidedconnections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.avoidedconnections.dto.StoryDTO;
import ru.avoidedconnections.dto.StoryResponse;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.repository.StoryRepository;
import ru.avoidedconnections.services.StoryService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MainController {
    private final StoryService storyService;


    @GetMapping("/mainInfo")
    public List<StoryResponse> mainInfoPage(@RequestParam(required = false) String city,
                                            @RequestParam(required = false) String query) {
        return storyService.getStoryByQuery(city, query);
    }

}
