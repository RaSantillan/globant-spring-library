package com.exercises.library.service;

import com.exercises.library.entity.Image;
import com.exercises.library.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image save(MultipartFile file) {
        if (file != null) {
            try {
                Image image = new Image();
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());
                return imageRepository.save(image);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Image update(MultipartFile file, String id) {
        if (file != null) {
            try {

                Image image = new Image();

                if (id != null) {
                    Optional<Image> response = imageRepository.findById(id);

                    if (response.isPresent()) {
                        image = response.get();
                    }
                }

                image.setMime(file.getContentType());

                image.setName(file.getName());

                image.setContent(file.getBytes());

                return imageRepository.save(image);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
