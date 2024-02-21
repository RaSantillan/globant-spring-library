package com.exercises.library.service;

import com.exercises.library.entity.Editorial;
import com.exercises.library.exception.MyException;

import java.util.List;

public interface EditorialService {
    void createEditorial(String name) throws MyException;
    List<Editorial> getEditorials();
    void updateEditorial(String id, String name);
}
