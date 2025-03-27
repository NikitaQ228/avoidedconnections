package ru.avoidedconnections.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.avoidedconnections.model.User;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String icon;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.icon = user.getIcon();
    }
}
