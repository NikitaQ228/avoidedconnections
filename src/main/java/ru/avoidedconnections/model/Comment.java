package ru.avoidedconnections.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

    @Column(name = "date")
    private Date date;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

}
