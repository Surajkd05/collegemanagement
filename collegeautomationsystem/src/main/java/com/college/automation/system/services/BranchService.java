package com.college.automation.system.services;

import com.college.automation.system.dtos.BranchDto;
import com.college.automation.system.dtos.BranchViewDto;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.Branches;
import com.college.automation.system.repos.BranchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BranchService {

    @Autowired
    private BranchRepo branchRepo;

    /*
    *
    * Add branch service
    *
    */
    public String addBranch(BranchDto branchDto){
        Branches branches = new Branches();

        branches.setBranchName(branchDto.getBranchName());

        branchRepo.save(branches);

        return "Branch added successfully";
    }

    /*
    *
    * Get branch service
    *
    */

    public Set<BranchViewDto> getBranch(){
        List<Branches> branchesList =  branchRepo.findAll();

        if(!branchesList.isEmpty()){
            Set<BranchViewDto> branchViewDtos = new HashSet<>();

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
