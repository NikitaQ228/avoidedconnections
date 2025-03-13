package ru.avoidedconnections.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class PageController {
    @GetMapping("/")
    public String getMainPage() {
        return null;
    }

    @GetMapping("/entry")
    public String getEntryPage() {
        return null;
    }

    @GetMapping("/profile")
    public String getProfilePage() {
        return null;
    }

    @GetMapping("/story")
    public String getStoryPage() {
        return null;
    }

    @GetMapping("/addStory")
    public String getAddStoryPage() {
        return null;
    }

}
