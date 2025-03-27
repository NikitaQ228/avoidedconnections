package ru.avoidedconnections.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
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

    @ManyToMany(mappedBy = "stories", fetch = FetchType.LAZY)
    private List<User> usersTag;

}
