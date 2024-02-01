package com.exercises.library.controller;

import com.exercises.library.exception.MyException;
import com.exercises.library.repository.UserRepository;
import com.exercises.library.service.UserService;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalController {
    private final UserService userService;

    @Autowired
    public PortalController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
                           @RequestParam String password2, ModelMap modelMap) {
        try {
            userService.registrar(nombre,email,password,password2);
            modelMap.put("exito", "Usuario registrado correctamente");
            return "index.html";
        } catch (MyException e) {
            modelMap.put("error", e.getMessage());
            modelMap.put("nombre", nombre);
            modelMap.put("email", email);
            modelMap.put("password", password);
            modelMap.put("password2", password2);
            return "registro.html";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
}
