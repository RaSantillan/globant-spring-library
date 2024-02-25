package com.exercises.library.service;

import com.exercises.library.entity.Editorial;
import com.exercises.library.exception.MyException;
import com.exercises.library.repository.EditorialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EditorialServiceImpl implements EditorialService {
    private final EditorialRepository editorialRepository;

    public EditorialServiceImpl(EditorialRepository editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

    @Override
    @Transactional
    public void createEditorial(String name) throws MyException {
        validateEditorialParam(name);
        Editorial editorial = new Editorial();
        editorial.setName(name);

        editorialRepository.save(editorial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Editorial> getEditorials() {
        return editorialRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Editorial getEditorial(String id) {
        return editorialRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void updateEditorial(String id, String name) {
        Editorial editorial = editorialRepository.findById(id).orElseThrow();
        editorial.setName(name);
        editorialRepository.save(editorial);
    }

    private void validateEditorialParam(String name) throws MyException {
        if (name.isEmpty()) {
            throw new MyException("Editorial name cannot be null");
        }
    }
}
