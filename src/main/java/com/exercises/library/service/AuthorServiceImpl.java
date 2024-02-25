package com.exercises.library.service;

import com.exercises.library.entity.Author;
import com.exercises.library.exception.MyException;
import com.exercises.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public void createAuthor(String name) throws MyException {
        validateAuthorParam(name);

        Author author = new Author();
        author.setName(name);
        authorRepository.save(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthor(String id) {
        return authorRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void updateAuthor(String id, String name) {
        Author author = authorRepository.findById(id).orElseThrow();
        author.setName(name);
        authorRepository.save(author);
    }

    private void validateAuthorParam(String name) throws MyException {
        if (name.isEmpty()) {
            throw new MyException("Author name cannot be null");
        }
    }
}
