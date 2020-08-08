package com.college.automation.system.services;

import com.college.automation.system.dtos.BranchDto;
import com.college.automation.system.dtos.BranchViewDto;
import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.Branches;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.BranchRepo;
import com.college.automation.system.repos.UserRepo;
import com.college.automation.system.repos.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class BranchService {

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MessageSource messageSource;
  
    @Autowired
    private CourseRepo courseRepo;

    /*
    *
    * Add branch service
    *
    */
    public String addBranch(String username,BranchDto branchDto){
        User admin = userRepo.findByEmail(username);
        if(null != admin){
            Branches branches = new Branches();

            branches.setBranchName(branchDto.getBranchName());
            branches.setCourse(courseRepo.findById(branchDto.getCourseId()).get());

            branchRepo.save(branches);

            return messageSource.getMessage("Branch.added", null, LocaleContextHolder.getLocale());
        }else {
            throw new BadRequestException(messageSource.getMessage("Not.authorized", null, LocaleContextHolder.getLocale()));
        }
    }

    /*
    *
    * Get branch service
    *
    */

    public Set<BranchViewDto> getBranch(Long courseId){
        List<Branches> branchesList =  branchRepo.findAllBranchesByCourse(courseId);

        if(!branchesList.isEmpty()){
            Set<BranchViewDto> branchViewDtos = new LinkedHashSet<>();

            for(Branches branches : branchesList){
                BranchViewDto branchViewDto = new BranchViewDto();

                branchViewDto.setBranchId(branches.getBranchId());
                branchViewDto.setBranchName(branches.getBranchName());

                branchViewDtos.add(branchViewDto);
            }
            return branchViewDtos;
        }else {
            throw new NotFoundException(messageSource.getMessage("NotFound.branch", null, LocaleContextHolder.getLocale()));
        }
    }
}
