package com.exercises.library.service;

import com.exercises.library.entity.User;
import com.exercises.library.exception.MyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    void registrar(
            String name, String email, String password, String password2, MultipartFile file
    ) throws MyException;

    void update(
            String id, String name, String email, String password, String password2, MultipartFile file
    ) throws MyException;

    User getUserById(String id);

    List<User> listarUsuarios();

    void cambiarRol(String id);
}
