package com.exercises.library.service;

import com.exercises.library.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image create(MultipartFile file);
    Image update(MultipartFile file, String id);
}
