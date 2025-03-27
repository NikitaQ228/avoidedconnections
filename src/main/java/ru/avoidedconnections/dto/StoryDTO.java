package ru.avoidedconnections.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.avoidedconnections.model.Story;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class StoryDTO {
    private Long id;
    private String head;
    private String img;
    private String text;
    private Date date;
    private String city;
    private UserDTO author;
    private List<UserDTO> usersTag;

    public StoryDTO(Story story) {
        this.id = story.getId();
        this.head = story.getHead();
        this.img = story.getImg();
        this.text = story.getText();
        this.date = story.getDate();
        this.city = story.getCity();
        this.author = new UserDTO(story.getAuthor());
        if (story.getUsersTag() != null) {
            this.usersTag = Collections.unmodifiableList(
                    story.getUsersTag().stream()
                            .map(UserDTO::new)
                            .collect(Collectors.toList())
            );
        }
    }
}
