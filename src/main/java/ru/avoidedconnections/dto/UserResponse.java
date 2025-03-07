package ru.avoidedconnections.dto;

import ru.avoidedconnections.model.Story;

import java.util.List;
import java.util.Set;

public class UserResponse {
    private String name;
    private String login;
    private String icon;
    private Set<Story> stories;
    private List<Story> mysStories;
}
