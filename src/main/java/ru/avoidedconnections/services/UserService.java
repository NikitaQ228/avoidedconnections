package ru.avoidedconnections.services;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.avoidedconnections.dto.*;
import ru.avoidedconnections.repository.JwtBlacklistRepository;
import ru.avoidedconnections.security.CustomUserDetails;
import ru.avoidedconnections.model.User;
import ru.avoidedconnections.repository.UserRepository;
import ru.avoidedconnections.security.jwt.JwtService;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtBlacklistRepository jwtBlacklistRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public JwtAuthenticationDTO signIn(UserCredentialsDTO userCredentialsDTO) throws AuthenticationException {
        User user = findByCredentials(userCredentialsDTO);
        return jwtService.generateAuthToken(user.getName());
    }

    public JwtAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws AuthenticationException, UsernameNotFoundException {
        String refreshToken = refreshTokenDTO.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken) && jwtBlacklistRepository.findByToken(refreshToken).isEmpty()) {
            User user = findByName(jwtService.getNameFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getName(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    public boolean addUser(User user) {
        if (userRepository.existsByName(user.getName()))
            return false;
        user.setIcon("1.png");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public User getUserByName(String name) {
        return userRepository
                .findByName(name)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with name: " + name)
                );
    }

    public User getUserByDTO(UserDTO userDTO) {
        return getUserByName(userDTO.getName());
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }

    public UserDTO getUserInfo() {
        return new UserDTO(this.getCurrentUser());
    }

    public UserDTO getUserInfo(Long id) {
        return userRepository.findById(id).map(UserDTO::new).orElseThrow(
                () -> new RuntimeException("User not found with id: " + id)
        );
    }

    public void changeIcon(String icon) {
        User user = getCurrentUser();
        user.setIcon(icon);
        userRepository.save(user);
    }

    private User findByCredentials(UserCredentialsDTO userCredentialsDTO) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByName(userCredentialsDTO.getName());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDTO.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Name or password is not correct");
    }

    private User findByName(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException(name + " not found"));
    }

    public ResponseEntity<HttpStatus> changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = getCurrentUser();
        if (passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }
}
