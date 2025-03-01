package ru.avoidedconnections.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", length = 10)
    private String name;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "icon")
    private String icon;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_tag",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "story_id"))
    private Set<Story> stories  = new HashSet<>();

    public User(Long id, String name, String email, String password, Set<Story> stories) {
        this.id = id;
        this.name = name;
        this.login = email;
        this.password = password;
        this.stories = stories;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Set getStories() {
        return stories;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.login = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStories(Set<Story> stories) {
        this.stories = stories;
    }
}
