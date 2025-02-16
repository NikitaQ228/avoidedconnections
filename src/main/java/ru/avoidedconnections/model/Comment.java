package ru.avoidedconnections.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    public Comment(Long id, User writer, Story story, String text) {
        this.id = id;
        this.writer = writer;
        this.story = story;
        this.text = text;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public Story getHistory() {
        return story;
    }

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public void setText(String text) {
        this.text = text;
    }
}
