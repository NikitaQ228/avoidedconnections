package ru.avoidedconnections.dto;


import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class StoryResponse {
    private Long id;
    private String head;
    private String img;
    private String text;
    private Date date;
    private String city;
    private UserResponse author;
    private Set<UserResponse> usersTag;
}
