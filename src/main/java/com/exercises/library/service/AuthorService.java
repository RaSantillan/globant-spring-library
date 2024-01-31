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
import java.util.UUID;

@Service
public class AuthorService {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private EditorialRepository editorialRepository;

    @Autowired
    public AuthorService(BookRepository bookRepository, AuthorRepository authorRepository, EditorialRepository editorialRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.editorialRepository = editorialRepository;
    }

    @Transactional
    public void createAuthor(String name) throws MyException {
        validateAuthorParam(name);
        Author author = new Author();
        author.setName(name);

        authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Transactional
    public void updateAuthor(String id, String name) {
        Optional<Author> authorResponse = authorRepository.findById(id);
        if (authorResponse.isPresent()) {
            Author author = authorResponse.get();
            author.setName(name);
            authorRepository.save(author);
        }
    }

    public Author getAuthorById(String id) {
        Optional<Author> authorResponse = authorRepository.findById(id);
        return authorResponse.get();
    }

    private void validateAuthorParam(String name) throws MyException {
        if (name.isEmpty()) {
            throw new MyException("Author Name cannot be null");
        }
    }
}
