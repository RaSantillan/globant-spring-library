package com.exercises.library.controller;

import com.exercises.library.entity.Author;
import com.exercises.library.exception.MyException;
import com.exercises.library.service.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/autor")
public class AuthorController {
    @Autowired
    AuthorServiceImpl authorServiceImpl;

    @GetMapping("/registrar")
    public String register() {
        return "autor_form.html";
    }

    @PostMapping("/registro/")
    public String registro(@RequestParam String nombre, ModelMap modelMap) {
        try {
            authorServiceImpl.createAuthor(nombre);
            System.out.println(nombre);
            modelMap.put("exito", "Author has been created");
        } catch (MyException e) {
            modelMap.put("error", "Error creating the author");
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, e);
            return "autor_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelMap) {
        List<Author> autores = authorServiceImpl.getAuthors();
        modelMap.addAttribute("autores", autores);
        return "autor_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelMap) {
        modelMap.put("autor", authorServiceImpl.getAuthor(id));
        return "autor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelMap) {
        try {
            authorServiceImpl.updateAuthor(id, nombre);
            return  "redirect:../lista";
        } catch (Exception e) {
            modelMap.put("error", e.getMessage());
            return "autor_modificar.html";
        }

    }
}
