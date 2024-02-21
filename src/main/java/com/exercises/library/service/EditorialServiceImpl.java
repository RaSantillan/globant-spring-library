package com.exercises.library.service;

import com.exercises.library.entity.Editorial;
import com.exercises.library.exception.MyException;
import com.exercises.library.repository.BookRepository;
import com.exercises.library.repository.EditorialRepository;
import com.exercises.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialServiceImpl implements EditorialService {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private EditorialRepository editorialRepository;

    public EditorialServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, EditorialRepository editorialRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
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
    @Transactional
    public void updateEditorial(String id, String name) {
        Optional<Editorial> editorialResponse = editorialRepository.findById(id);
        if (editorialResponse.isPresent()) {
            Editorial editorial = editorialResponse.get();
            editorial.setName(name);
            editorialRepository.save(editorial);
        }
    }

    private void validateEditorialParam(String name) throws MyException {
        if (name.isEmpty()) {
            throw new MyException("Editorial Name cannot be null");
        }
    }
}
