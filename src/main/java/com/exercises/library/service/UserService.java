package com.exercises.library.service;

import com.exercises.library.entity.User;
import com.exercises.library.enumeration.Role;
import com.exercises.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registrar(String name, String email, String password, String password2) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
