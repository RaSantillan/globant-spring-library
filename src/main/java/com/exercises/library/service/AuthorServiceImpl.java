package com.exercises.library.service;

import com.exercises.library.entity.Author;
import com.exercises.library.exception.MyException;
import com.exercises.library.repository.AuthorRepository;
import com.exercises.library.repository.BookRepository;
import com.exercises.library.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, EditorialRepository editorialRepository) {
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
    public Author getAuthorById(String id) {
        return authorRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void updateAuthor(String id, String name) {
        Optional<Author> authorResponse = authorRepository.findById(id);
        if (authorResponse.isPresent()) {
            Author author = authorResponse.get();
            author.setName(name);
            authorRepository.save(author);
        }
    }

    private void validateAuthorParam(String name) throws MyException {
        if (name.isEmpty()) {
            throw new MyException("Author name cannot be null");
        }
    }
}
