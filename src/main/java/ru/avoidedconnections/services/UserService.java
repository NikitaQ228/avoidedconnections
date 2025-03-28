package ru.avoidedconnections.services;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.avoidedconnections.dto.UserDTO;
import ru.avoidedconnections.config.MyUserDetails;
import ru.avoidedconnections.model.User;
import ru.avoidedconnections.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean addUser(User user) {
        if (userRepository.findByName(user.getName()).isPresent())
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
                        () -> new ObjectNotFoundException(Optional.ofNullable(name), "User not found with name: ")
                );
    }

    public User getUserByDTO(UserDTO userDTO) {
        return getUserByName(userDTO.getName());
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
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

}
