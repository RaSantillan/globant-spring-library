package com.exercises.library.service;

import com.exercises.library.entity.Book;
import com.exercises.library.entity.Transaction;
import com.exercises.library.entity.User;
import com.exercises.library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
        return null;
    }

    @Override
    public Transaction getById(Long id) {
        return null;
    }

    @Override
    public Transaction update(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Transaction> filterByParameter(Book book, User user, LocalDateTime dateTime, String comments) {
        return null;
    }
}
