package com.college.automation.system.controller;

import com.college.automation.system.dtos.PaperDto;
import com.college.automation.system.dtos.PaperSetDto;
import com.college.automation.system.model.PreviousYearPapers;
import com.college.automation.system.services.PreviousYearPaperService;
import com.college.automation.system.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping(path = "/paper")
public class PaperController {

    @Autowired
    private PreviousYearPaperService previousYearPaperService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam MultipartFile file, @RequestParam("branchId") Long branchId, @RequestParam("year") int year, @RequestParam("semester") int semester, @RequestParam("session") String session) throws IOException {
        return previousYearPaperService.uploadFile(file,branchId,year, semester,session);
    }

    @PostMapping("/")
    public String uploadFile1(@RequestBody PaperSetDto paperSetDto) throws IOException {
        return previousYearPaperService.uploadFile1(paperSetDto);
    }

    @GetMapping("/getFile")
    public Set<PaperDto> getFiles(@RequestParam("branchId") Long branchId, @RequestParam("year") int year, @RequestParam("semester") int semester){
        return previousYearPaperService.getFiles(branchId, year, semester);
    }

   /* @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }*/

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable Long fileId) {
        // Load file from database
        PreviousYearPapers previousYearPapers = previousYearPaperService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(previousYearPapers.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + previousYearPapers.getFileName() + "\"")
                .body(new ByteArrayResource(previousYearPapers.getFileData()));
    }
}
