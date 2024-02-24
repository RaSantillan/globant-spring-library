package com.exercises.library.repository;

import com.exercises.library.entity.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditorialRepository extends JpaRepository<Editorial, String> {
}
