package com.college.automation.system.services;

import com.college.automation.system.dtos.InterviewTipDto;
import com.college.automation.system.dtos.InterviewTipViewDto;
import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.Branches;
import com.college.automation.system.model.InterviewTips;
import com.college.automation.system.repos.BranchRepo;
import com.college.automation.system.repos.InterviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InterviewService {

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private InterviewRepo interviewRepo;

    /*
     *
     *  Add tip by branch
     *
     */
    public String addInterviewByBranch(Long branchId, InterviewTipDto interviewTipDto){
        Optional<Branches> branch = branchRepo.findById(branchId);

        if(branch.isPresent()){
            InterviewTips interviewTips = new InterviewTips();

            interviewTips.setTip(interviewTipDto.getTip());
            interviewTips.setBranches(branch.get());

            interviewRepo.save(interviewTips);

            return "Tip added successfully";
        }else {
            throw new NotFoundException("Branch not found");
        }
    }

    /*
     *
     * Get tips by branch
     *
     */

    public Set<InterviewTipViewDto> getInterviewTipsByBranch(Long branchId, String  page, String size, String sortBy){

        List<InterviewTips> interviewTipsList = interviewRepo.findAllByBranch(branchId, PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(sortBy).descending()));

        if(!interviewTipsList.isEmpty()){
            Set<InterviewTipViewDto> interviewTipViewDtos = new LinkedHashSet<>();
            for(InterviewTips interviewTips : interviewTipsList){
                InterviewTipViewDto interviewTipViewDto = new InterviewTipViewDto();
                interviewTipViewDto.setTip(interviewTips.getTip());
                interviewTipViewDto.setId(interviewTips.getTipId());
                interviewTipViewDtos.add(interviewTipViewDto);
            }

            return interviewTipViewDtos;
        }else {
            throw new BadRequestException("There is no tips available for this branch");
        }
    }
}
