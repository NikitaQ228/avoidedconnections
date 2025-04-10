package ru.avoidedconnections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avoidedconnections.dto.JwtAuthenticationDTO;
import ru.avoidedconnections.dto.RefreshTokenDTO;
import ru.avoidedconnections.dto.UserCredentialsDTO;
import ru.avoidedconnections.model.User;
import ru.avoidedconnections.services.UserService;

import javax.naming.AuthenticationException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationDTO> signIn(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        try {
            JwtAuthenticationDTO jwtAuthenticationDTO = userService.signIn(userCredentialsDTO);
            return ResponseEntity.ok(jwtAuthenticationDTO);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed" + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public JwtAuthenticationDTO refresh(@RequestBody RefreshTokenDTO refreshTokenDTO) throws Exception {
        return userService.refreshToken(refreshTokenDTO);
    }


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        if (userService.addUser(user))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
