package com.exercises.library.controller;

import com.exercises.library.entity.Author;
import com.exercises.library.entity.Book;
import com.exercises.library.entity.Editorial;
import com.exercises.library.exception.MyException;
import com.exercises.library.service.AuthorServiceImpl;
import com.exercises.library.service.BookServiceImpl;
import com.exercises.library.service.EditorialServiceImpl;
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
@RequestMapping("/libro")
public class BookController {
    @Autowired
    BookServiceImpl bookServiceImpl;
    @Autowired
    AuthorServiceImpl authorServiceImpl;
    @Autowired
    EditorialServiceImpl editorialServiceImpl;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelMap) {
        List<Author> authors = authorServiceImpl.getAuthors();
        List<Editorial> editorials = editorialServiceImpl.getEditorials();

        modelMap.addAttribute("autores", authors);
        modelMap.addAttribute("editoriales", editorials);

        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
                           @RequestParam(required = false) Integer ejemplares,
                           @RequestParam String idAutor, @RequestParam String idEditorial,
                           ModelMap modelMap) {
        try {
            bookServiceImpl.createBook(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelMap.put("exito", "The book was successfully created");
        } catch (MyException e) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, e);
            modelMap.put("error", e.getMessage());
            return "libro_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelMap) {
        List<Book> libros = bookServiceImpl.getBooks();
        modelMap.addAttribute("libros", libros);
        return "libro_list.html";
    }
}
