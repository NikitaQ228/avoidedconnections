package ru.avoidedconnections.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.avoidedconnections.dto.StoryDTO;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "writer_id", nullable = false)
    private User writer;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Story story;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    public Comment(User writer, Story storyObj, Date now, String commentText) {
        this.writer = writer;
        this.story = storyObj;
        this.date = now;
        this.text = commentText;
    }
}
