package com.exercises.library.controller;

import com.exercises.library.entity.User;
import com.exercises.library.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<User> users = userServiceImpl.listarUsuarios();
        modelo.addAttribute("usuarios", users);

        return "usuario_list";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id){
        userServiceImpl.cambiarRol(id);

        return "redirect:/admin/usuarios";
    }
}
