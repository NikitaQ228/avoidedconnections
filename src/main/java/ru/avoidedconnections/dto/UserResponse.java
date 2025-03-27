package ru.avoidedconnections.dto;

import lombok.Data;
import ru.avoidedconnections.model.Story;
import ru.avoidedconnections.model.User;

import java.util.List;
import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String icon;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.icon = user.getIcon();
    }
}
