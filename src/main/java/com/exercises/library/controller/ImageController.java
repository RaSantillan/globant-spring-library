package com.exercises.library.controller;

import com.exercises.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class ImageController {
    private final UserService userService;

    @Autowired
    public ImageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte []> userImage(@PathVariable String id) {
        byte[] image = userService.getUserById(id).getImage().getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
