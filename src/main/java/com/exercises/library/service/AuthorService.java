package com.exercises.library.service;

import com.exercises.library.entity.Author;
import com.exercises.library.exception.MyException;

import java.util.List;

public interface AuthorService {
    void createAuthor(String name) throws MyException;
    List<Author> getAuthors();
    Author getAuthor(String id);
    void updateAuthor(String id, String name);
}
