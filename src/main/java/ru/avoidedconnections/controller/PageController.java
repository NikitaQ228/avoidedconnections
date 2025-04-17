package ru.avoidedconnections.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String getMainPage() {
        return "/pages/main.html";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/pages/login.html";
    }

    @GetMapping("/profile")
    public String getProfilePage() {
        return "/pages/profile.html";
    }

    @GetMapping("/story")
    public String getStoryPage() {
        return "/pages/story.html";
    }

    @GetMapping("/addStory")
    public String getAddStoryPage() {
        return "/pages/addstory.html";
    }

}
