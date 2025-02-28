package ru.avoidedconnections.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "story")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "head", length = 30)
    private String head;

    @Column(name = "img")
    private String img;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "date")
    private Date date;

    @Column(name = "city")
    private String city;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany(mappedBy = "stories")
    private Set<User> usersTag  = new HashSet<>();

    public Story(Long id, String head, String text, Date date, String city, User author, Set<User> usersTag) {
        this.id = id;
        this.head = head;
        this.text = text;
        this.date = date;
        this.city = city;
        this.author = author;
        this.usersTag = usersTag;
    }

    public Story() {
    }

    public String getHead() {
        return head;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public User getAuthor() {
        return author;
    }

    public Long getId() {
        return id;
    }

    public Set<User> getUsersTag() {
        return usersTag;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsersTag(Set<User> usersTag) {
        this.usersTag = usersTag;
    }
}
