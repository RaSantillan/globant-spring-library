package com.exercises.library.service;

import com.exercises.library.entity.Book;
import com.exercises.library.entity.Transaction;
import com.exercises.library.entity.User;
import com.exercises.library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, BookService bookService, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    @Override
    public void create(Book book, User user, LocalDateTime dateTime, String comments) {
        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setUser(user);
        transaction.setDateTime(dateTime);
        transaction.setComments(comments);
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getById(Long id) {
        return transactionRepository.findById(id).orElseThrow();
    }

    @Override
    public Transaction update(Long id, Long bookId, String userId, Boolean isDone, String comments) {
        Transaction foundTransaction = getById(id);
        foundTransaction.setBook(bookService.getBook(bookId));
        foundTransaction.setUser(userService.getUserById(userId));
        foundTransaction.setDone(isDone);
        foundTransaction.setComments(comments);
        return foundTransaction;
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> filterByParameter(Long bookId, String userId, Boolean isDone, String comments) {
        return getAll().stream()
                .filter(transaction -> (bookId == null || transaction.getBook().equals(bookService.getBook(bookId))))
                .filter(transaction -> (userId == null || transaction.getUser().equals(userService.getUserById(userId))))
                .filter(transaction -> (isDone == null || transaction.isDone() == isDone))
                .filter(transaction -> (transaction.getComments().equals(comments)))
                .collect(Collectors.toList());
    }
}
