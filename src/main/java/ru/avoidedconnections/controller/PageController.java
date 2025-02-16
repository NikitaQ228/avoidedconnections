package ru.avoidedconnections.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String getMainPage() {
        return "main.html";
    }

    @GetMapping("/entry")
    public String getEntryPage() {
        return "entry.html";
    }

    @GetMapping("/profile")
    public String getProfilePage() {
        return "profile.html";
    }

    @GetMapping("/story")
    public String getStoryPage() {
        return "Story.html";
    }

    @GetMapping("/addHistory")
    public String getAddHistoryPage() {
        return "addHistory.html";
    }

}
