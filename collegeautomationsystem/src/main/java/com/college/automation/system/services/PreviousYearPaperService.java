package com.college.automation.system.services;

import com.college.automation.system.dtos.PaperDto;
import com.college.automation.system.dtos.PaperSetDto;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.Branches;
import com.college.automation.system.model.PreviousYearPapers;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.BranchRepo;
import com.college.automation.system.repos.PreviousYearPaperRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PreviousYearPaperService {

    @Autowired
    private PreviousYearPaperRepo previousYearPaperRepo;

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private MessageSource messageSource;

    public String uploadFile(MultipartFile file, Long branchId, int year, int semester, String session) throws IOException {

        PreviousYearPapers previousYearPapers = new PreviousYearPapers();

        previousYearPapers.setFileData(file.getBytes());
        previousYearPapers.setContentType(file.getContentType());
        previousYearPapers.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        previousYearPapers.setBranchId(branchId);
        previousYearPapers.setSemester(semester);
        previousYearPapers.setYear(year);
        previousYearPapers.setSession(session);

        previousYearPaperRepo.save(previousYearPapers);

        return "File uploaded successfully";
    }

    public String uploadFile1(PaperSetDto paperSetDto) throws IOException {

        PreviousYearPapers previousYearPapers = new PreviousYearPapers();

        previousYearPapers.setFileData(paperSetDto.getFile().getBytes());
        previousYearPapers.setContentType(paperSetDto.getFile().getContentType());
        previousYearPapers.setFileName(StringUtils.cleanPath(paperSetDto.getFile().getOriginalFilename()));
        previousYearPapers.setBranchId(paperSetDto.getBranchId());
        previousYearPapers.setSemester(paperSetDto.getSemester());
        previousYearPapers.setYear(paperSetDto.getYear());
        previousYearPapers.setSession(paperSetDto.getSession());

        previousYearPaperRepo.save(previousYearPapers);

        return "File uploaded successfully";
    }

    public Set<PaperDto> getFiles(Long branchId, int year, int semester){

        Optional<Branches> branches = branchRepo.findById(branchId);

        Set<PreviousYearPapers> previousYearPapers = previousYearPaperRepo.findByBranch(branchId, year, semester);

        Set<PaperDto> paperDtos = new LinkedHashSet<>();
        for(PreviousYearPapers previousYearPapers1 : previousYearPapers) {
            PaperDto paperDto = new PaperDto();

            String fileDownloadUri = messageSource.getMessage("Default.url",null, LocaleContextHolder.getLocale())+"paper/downloadFile/"+previousYearPapers1.getPaperId();


            paperDto.setBranchName(branches.get().getBranchName());
            paperDto.setCourseName(branches.get().getCourse().getCourseName());
            paperDto.setPaperId(previousYearPapers1.getPaperId());
            paperDto.setSemester(semester);
            paperDto.setSession(previousYearPapers1.getSession());
            paperDto.setYear(year);
            paperDto.setDownloadUri(fileDownloadUri);

            paperDtos.add(paperDto);
        }

        return paperDtos;
    }

    public PreviousYearPapers getFile(Long paperId) {
        Optional<PreviousYearPapers> previousYearPapers = previousYearPaperRepo.findById(paperId);
        if(previousYearPapers.isPresent()){
            return previousYearPapers.get();
        }else {
            throw new NotFoundException("File not found with id : "+ paperId);
        }
    }
}
