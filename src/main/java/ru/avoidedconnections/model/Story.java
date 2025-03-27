package ru.avoidedconnections.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;
import ru.avoidedconnections.dto.StoryDTO;

import java.util.*;

@Data
@Entity
@Builder
@Table(name = "story")
@NoArgsConstructor
@AllArgsConstructor
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "head", length = 30)
    private String head;

    @Column(name = "img")
    private String img;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "city")
    private String city;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

//    @ManyToMany(mappedBy = "stories", fetch = FetchType.LAZY)
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_tag",  // Имя связующей таблицы
            joinColumns = @JoinColumn(name = "story_id"),  // Внешний ключ для Story
            inverseJoinColumns = @JoinColumn(name = "user_id")  // Внешний ключ для User
    )
    private List<User> usersTag = new ArrayList<>();

    public Story(Long id, String head, String img, String text, Date date, String city, User author) {
        this.id = id;
        this.head = head;
        this.img = img;
        this.text = text;
        this.date = date;
        this.city = city;
        this.author = author;
    }
}
