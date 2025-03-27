package ru.avoidedconnections.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.avoidedconnections.model.Comment;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.model.User;

import java.util.Date;

@Data
@NoArgsConstructor
public class CommentDTO {
    private UserDTO writer;
    private Long storyId;
    private Date date;
    private String text;

    public CommentDTO(Comment comment) {
        this.writer = new UserDTO(comment.getWriter());
        this.storyId = comment.getStory().getId();
        this.date = comment.getDate();
        this.text = comment.getText();
    }
}
