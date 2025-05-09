package ru.avoidedconnections.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.avoidedconnections.model.Story;

import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class StoryResponse {
    private Long id;
    private String head;
    private String text;
    private Date date;
    private String city;

    public StoryResponse(Story story) {
        this.id = story.getId();
        this.head = story.getHead();
        this.text = story.getText();
        this.date = story.getDate();
        this.city = story.getCity();
    }
}
