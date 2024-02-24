package com.exercises.library.service;

import com.exercises.library.entity.Book;
import com.exercises.library.exception.MyException;
import com.exercises.library.repository.AuthorRepository;
import com.exercises.library.repository.BookRepository;
import com.exercises.library.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final EditorialRepository editorialRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, EditorialRepository editorialRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.editorialRepository = editorialRepository;
    }

    @Override
    @Transactional
    public void createBook(Long isbn, String name, Integer stock, String authorId, String editorialId)
            throws MyException {
        validateBookParam(isbn, name, stock, authorId, editorialId);

        Book book = new Book();
        book.setISBN(isbn);
        book.setName(name);
        book.setStock(stock);
        book.setRegisteredDate(new Date());

        book.setAuthor(authorRepository.findById(authorId).orElseThrow());
        book.setEditorial(editorialRepository.findById(editorialId).orElseThrow());

        bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void updateBook(Long isbn, String name, Integer stock, String authorId, String editorialId) {
        Optional<Book> bookResponse = bookRepository.findById(isbn);
        if (bookResponse.isPresent()) {
            Book book = bookResponse.get();
            book.setName(name);
            book.setStock(stock);
            book.setAuthor(authorRepository.findById(authorId).orElseThrow());
            book.setEditorial(editorialRepository.findById(editorialId).orElseThrow());
            bookRepository.save(book);
        }

    }

    private void validateBookParam(Long isbn, String name, Integer stock, String authorId, String editorialId)
    throws MyException {
        if (isbn == null) {
            throw new MyException("ISBN cannot be null");
        }
        if (name.isEmpty()) {
            throw new MyException("Name cannot be null");
        }
        if (stock == null) {
            throw new MyException("Stock cannot be null");
        }
        if (authorId == null) {
            throw new MyException("AuthorId cannot be null");
        }
        if (editorialId == null) {
            throw new MyException("EditorialId cannot be null");
        }
    }
}
