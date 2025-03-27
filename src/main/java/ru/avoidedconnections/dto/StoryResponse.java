package ru.avoidedconnections.dto;


import lombok.Data;
import ru.avoidedconnections.model.Story;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class StoryResponse {
    private Long id;
    private String head;
    private String img;
    private String text;
    private Date date;
    private String city;
    private UserResponse author;
    private List<UserResponse> usersTag;

    public StoryResponse(Story story) {
        this.id = story.getId();
        this.head = story.getHead();
        this.img = story.getImg();
        this.text = story.getText();
        this.date = story.getDate();
        this.city = story.getCity();
        this.author = new UserResponse(story.getAuthor());
        this.usersTag = Collections.unmodifiableList(
                story.getUsersTag().stream()
                        .map(UserResponse::new)
                        .collect(Collectors.toList())
        );
    }
}
