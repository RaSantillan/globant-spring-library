package com.exercises.library.service;

import com.exercises.library.entity.Book;
import com.exercises.library.exception.MyException;

import java.util.List;

public interface BookService {
    void createBook(Long isbn, String name, Integer stock, String authorId, String editorialId) throws MyException;
    List<Book> getBooks();
    void updateBook(Long isbn, String name, Integer stock, String authorId, String editorialId);
}
