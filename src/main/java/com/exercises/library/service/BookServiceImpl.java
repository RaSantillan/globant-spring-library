package com.exercises.library.service;

import com.exercises.library.entity.Book;
import com.exercises.library.exception.MyException;
import com.exercises.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final EditorialService editorialService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, EditorialService editorialService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.editorialService = editorialService;
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

        book.setAuthor(authorService.getAuthor(authorId));
        book.setEditorial(editorialService.getEditorial(editorialId));

        bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBook(Long isbn) {
        return bookRepository.findById(isbn).orElseThrow();
    }

    @Override
    @Transactional
    public void updateBook(Long isbn, String name, Integer stock, String authorId, String editorialId) {
        Book book = this.getBook(isbn);
        book.setName(name);
        book.setStock(stock);
        book.setAuthor(authorService.getAuthor(authorId));
        book.setEditorial(editorialService.getEditorial(editorialId));
        bookRepository.save(book);
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
