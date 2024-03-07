package com.exercises.library.service;

import com.exercises.library.entity.Book;
import com.exercises.library.entity.Transaction;
import com.exercises.library.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    void create(Book book, User user, LocalDateTime dateTime, String comments);
    List<Transaction> getAll();
    Transaction getById(Long id);
    Transaction update(Long id);
    void delete(Long id);
    List<Transaction> filterByBook(Book book);
    List<Transaction> filterByUser(User user);
    List<Transaction> filterByDateTime(LocalDateTime dateTime);
    List<Transaction> filterByDone(Boolean isDone);
    List<Transaction> filterByComments(String comments);
}
