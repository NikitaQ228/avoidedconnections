package ru.avoidedconnections.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.avoidedconnections.model.User;
import ru.avoidedconnections.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean addUser(User user) {
        if (repository.findByName(user.getName()).isPresent())
            return false;
        user.setIcon("1.png");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return true;
    }

}
