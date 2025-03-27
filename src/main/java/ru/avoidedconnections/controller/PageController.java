package ru.avoidedconnections.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping
    public String getMainPage() {
        return "main.html";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
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
