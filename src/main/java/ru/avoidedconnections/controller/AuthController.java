package ru.avoidedconnections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.avoidedconnections.dto.JwtAuthenticationDTO;
import ru.avoidedconnections.dto.RefreshTokenDTO;
import ru.avoidedconnections.dto.UserCredentialsDTO;
import ru.avoidedconnections.model.User;
import ru.avoidedconnections.security.jwt.JwtService;
import ru.avoidedconnections.services.UserService;

import javax.naming.AuthenticationException;
import java.time.Instant;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationDTO> signIn(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        try {
            JwtAuthenticationDTO jwtAuthenticationDTO = userService.signIn(userCredentialsDTO);
            return ResponseEntity.ok(jwtAuthenticationDTO);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/refresh")
    public JwtAuthenticationDTO refresh(@RequestBody RefreshTokenDTO refreshTokenDTO) throws Exception {
        return userService.refreshToken(refreshTokenDTO);
    }

    @PostMapping("/signUp")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        if (userService.addUser(user))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/logout")
    public ModelAndView logout(@RequestBody String token) {
        Instant expirationInstant = jwtService.getExpirationFromToken(token).toInstant();
        jwtService.addTokenToBlacklist(token, expirationInstant);
        return new ModelAndView("redirect:/login");
    }
}
