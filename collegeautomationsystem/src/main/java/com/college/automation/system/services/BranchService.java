package com.college.automation.system.services;

import com.college.automation.system.dtos.BranchDto;
import com.college.automation.system.dtos.BranchViewDto;
import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.Branches;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.BranchRepo;
import com.college.automation.system.repos.CourseRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

            return "Branch added successfully";
        }else {
            throw new BadRequestException("You are not authorized to add branch");
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
            throw new NotFoundException("No branch found");
        }
    }
}
