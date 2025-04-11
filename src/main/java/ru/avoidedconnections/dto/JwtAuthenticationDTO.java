package ru.avoidedconnections.dto;

import lombok.Data;

@Data
public class JwtAuthenticationDTO {
    private String accessToken;
    private String refreshToken;
}
