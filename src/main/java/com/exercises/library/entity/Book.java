package com.exercises.library.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Book {
    @Id
    private Long ISBN;
    private String name;
    private Integer stock;
    @Temporal(TemporalType.DATE)
    private Date registeredDate;
    @ManyToOne
    private Author author;
    @ManyToOne
    private Editorial editorial;


    public Book() {}


    public Long getISBN() {
        return ISBN;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registereDate) {
        this.registeredDate = registereDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }
}
