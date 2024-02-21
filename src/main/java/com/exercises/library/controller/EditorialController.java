package com.exercises.library.controller;

import com.exercises.library.entity.Editorial;
import com.exercises.library.exception.MyException;
import com.exercises.library.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/editorial")
public class EditorialController {
    @Autowired
    EditorialService editorialService;

    @GetMapping("/registrar")
    public String register() {
        return "editorial_form.html";
    }

    @PostMapping("/registro/")
    public String registro(@RequestParam String nombre, ModelMap modelMap) {
        try {
            editorialService.createEditorial(nombre);
            System.out.println(nombre);
            modelMap.put("exito", "Editorial has been created");
        } catch (MyException e) {
            modelMap.put("error", "Error creating editorial");
            Logger.getLogger(EditorialController.class.getName()).log(Level.SEVERE, null, e);
            return "editorial_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelMap) {
        List<Editorial> editoriales = editorialService.getEditorials();
        modelMap.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }
}
