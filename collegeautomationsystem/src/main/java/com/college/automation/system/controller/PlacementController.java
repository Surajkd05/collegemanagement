package com.college.automation.system.controller;

import com.college.automation.system.dtos.PlacementDetailsDto;
import com.college.automation.system.dtos.PlacementDto;
import com.college.automation.system.dtos.PlacementViewDto;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.ImageData;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.UserRepo;
import com.college.automation.system.services.ImageService;
import com.college.automation.system.services.PlacementService;
import com.college.automation.system.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/placement")
public class PlacementController {

    @Autowired
    private PlacementService placementService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ImageService imageService;

    /*
     *
     * Add Placement Record Controller
     *
     */
    @PostMapping(path = "/")
    public String addPlacementData(@RequestBody PlacementDto placementDto) {
        String username = userAuthenticationService.getUserName();
        return placementService.addPlacementData(placementDto, username);
    }

    /*
     *
     * Get all details
     *
     */
    @GetMapping(path = "/all")
    public Set<PlacementViewDto> getAllPlacementData(@RequestParam(defaultValue = "0") String page) {
        return placementService.getPlacementDetails(page);
    }

    /*
     *
     * Get details by branch
     *
     */
    @GetMapping(path = "/branch")
    public Set<PlacementViewDto> getAllRecordsByBranch(@RequestParam(value = "branchId") Long branchId, @RequestParam(defaultValue = "0") String page) {
        return placementService.getPlacementDetailsByBranch(branchId, page);
    }

    /*
     *
     * Get dtails by placement id
     *
     */
    @GetMapping(path = "/placement")
    public PlacementDetailsDto getRecordsByPlacementId(@RequestParam(value = "placementId") Long placementId) {
        return placementService.getPlacementDetailsByPlacementId(placementId);
    }

    /*
     *
     * Get details by student name
     *
     */
    @GetMapping(path = "/student")
    public Set<PlacementViewDto> getRecordsByStudentName(@RequestParam(value = "studentName") String studentName, @RequestParam(defaultValue = "0") String page) {
        return placementService.getDetailsByStudentName(studentName, page);
    }

    @GetMapping(path = "/image")
    public ResponseEntity<?> getStudentProfileImage(@RequestParam(value = "userId") Long userId) {

        User user = userRepo.findByUserId(userId);
        ImageData imageData = imageService.downloadImage(user.getEmail());

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
