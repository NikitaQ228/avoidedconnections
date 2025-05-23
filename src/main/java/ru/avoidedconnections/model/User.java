package ru.avoidedconnections.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.avoidedconnections.dto.UserDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(mappedBy = "usersTag", fetch = FetchType.LAZY)
    private List<Story> stories;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
