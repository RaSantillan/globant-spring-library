package com.exercises.library.controller;

import com.exercises.library.entity.User;
import com.exercises.library.exception.MyException;
import com.exercises.library.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public PortalController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
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
                           @RequestParam String password2, @RequestParam MultipartFile file, ModelMap modelMap) {
        try {
            userServiceImpl.registrar(nombre,email,password,password2, file);
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
    public String login(@RequestParam(required = false) String error, ModelMap modelMap) {

        if (error!=null) {
            modelMap.put("error", "Usuario o Contrasena invalido");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        User logged = (User) session.getAttribute("usuariosession");
        if (logged.getRole().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(HttpSession session, ModelMap modelMap) {
        User user = (User) session.getAttribute("usuariosession");
        modelMap.put("usuario", user);
        return "usuario_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(
            @PathVariable String id, @RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2,
            ModelMap modelMap, MultipartFile file) {
        try {
            userServiceImpl.update(id, nombre, email, password, password2, file);
            modelMap.put("exito", "Usuario actualizado correctamente");
            return "inicio.html";
        } catch (MyException e) {
            modelMap.put("error", e.getMessage());
            modelMap.put("nombre", nombre);
            modelMap.put("email", email);
            return "usuario_modificar.html";
        }
    }
}
