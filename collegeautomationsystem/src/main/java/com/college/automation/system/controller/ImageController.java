package com.college.automation.system.controller;

import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.ImageData;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.UserRepo;
import com.college.automation.system.services.ImageService;
import com.college.automation.system.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@RestController
@RequestMapping(path = "/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping(path = "/")
    public String uploadUserImage(@RequestParam MultipartFile image) throws IOException {
        String username = userAuthenticationService.getUserName();
        System.out.println("Image retrieved is : "+image);
        return imageService.uploadImage(username, image);
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> getProfileImage() {
        String username = userAuthenticationService.getUserName();
        ImageData imageData = imageService.downloadImage(username);

        System.out.println("Image data is "+imageData);

        if(null != imageData) {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(imageData.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageData.getFileName() + "\"")
                    .body(new ByteArrayResource(imageData.getProfileImage()));
        }else {
            throw new NotFoundException("Image not found");
        }
    }
}
