package ru.avoidedconnections.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/home")
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

    @GetMapping("/addHistory")
    public String getAddHistoryPage() {
        return null;
    }

}
