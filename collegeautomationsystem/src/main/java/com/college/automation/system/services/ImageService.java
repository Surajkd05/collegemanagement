package com.college.automation.system.services;

import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.ImageData;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.ImageRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private UserRepo userRepo;

    public String uploadImage(String username, MultipartFile image) throws IOException {

        User user = userRepo.findByEmail(username);

        ImageData imageData = imageRepo.findImageByUserId(user.getUserId());

        if(null != imageData){
            imageRepo.deleteById(imageData.getId());
        }

        boolean isValid = (image.getContentType().equals("image/png") || image.getContentType().equals("image/jpeg")
                || image.getContentType().equals("image/jpg") || image.getContentType().equals("image/bmp"));
        if (!isValid) {
            throw new BadRequestException("Image uploaded should be {.png, .bmp,.jpeg,.jpg}");
        }
        ImageData userImageData = new ImageData();

        System.out.println("Image uploaded is " + image.getContentType());
        userImageData.setProfileImage(image.getBytes());
        userImageData.setContentType(image.getContentType());
        userImageData.setFileName(StringUtils.cleanPath(image.getOriginalFilename()));
        userImageData.setUserId(user.getUserId());
        imageRepo.save(userImageData);

        return "Image uploaded";
    }

    public ImageData downloadImage(String username) {
        User user = userRepo.findByEmail(username);
        ImageData userImage = imageRepo.findImageByUserId(user.getUserId());

        if (null != userImage) {
            return userImage;
        } else {
            throw new NotFoundException("File not found with id " + user.getUserId());
        }
    }
}
