package com.college.automation.system.services;

import com.college.automation.system.dtos.CompanyDetailsDto;
import com.college.automation.system.dtos.PlacementDetailsDto;
import com.college.automation.system.dtos.PlacementDto;
import com.college.automation.system.dtos.PlacementViewDto;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.Branches;
import com.college.automation.system.model.Placement;
import com.college.automation.system.model.PlacementData;
import com.college.automation.system.model.Student;
import com.college.automation.system.repos.BranchRepo;
import com.college.automation.system.repos.PlacementDataRepo;
import com.college.automation.system.repos.PlacementRepo;
import com.college.automation.system.repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlacementService {

    @Autowired
    private PlacementRepo placementRepo;

    @Autowired
    private PlacementDataRepo placementDataRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private BranchRepo branchRepo;

    /*
     *
     * addPlacement Data
     *
     */
    public String addPlacementData(PlacementDto placementDto, String username) {

        Optional<Student> student = Optional.ofNullable(studentRepo.findByEmail(username));

        StringBuilder sb = new StringBuilder();

        if (student.isPresent()) {
            Optional<Branches> branches = branchRepo.findById(placementDto.getBranchId());

            if (branches.isPresent()) {

                Placement placementExist = placementRepo.findByUserId(student.get().getUserId());

                if (null != placementExist) {
                    PlacementData placementData = new PlacementData();
                    placementData.setCompanyName(placementDto.getCompanyName());
                    placementData.setSalary(placementDto.getSalary());
                    placementData.setPlacement(placementExist);
                    placementData.setPlacedDate(placementDto.getPlacedDate());

                    placementDataRepo.save(placementData);

                    placementExist.setCompanies(placementExist.getCompanies() + 1);

                    placementRepo.save(placementExist);

                    sb.append("Placement record added successfully");
                }else{
                    Placement placement = new Placement();

                    placement.setCompanies(1);
                    placement.setBranches(branches.get());
                    placement.setStudent(student.get());
                    placement.setStudentName(student.get().getFirstName() + " " + student.get().getLastName());

                    Placement addedPlacement = placementRepo.save(placement);

                    PlacementData placementData = new PlacementData();

                    placementData.setPlacement(addedPlacement);
                    placementData.setCompanyName(placementDto.getCompanyName());
                    placementData.setSalary(placementDto.getSalary());
                    placementData.setPlacedDate(placementDto.getPlacedDate());

                    placementDataRepo.save(placementData);

                    sb.append("Placement record added successfully");
                }

            } else {
                throw new NotFoundException("Branch not found");
            }
        } else {
            throw new NotFoundException("User not found");
        }
        return sb.toString();
    }

    /*
    *
    * Get placement information
    *
    */
    public Set<PlacementViewDto> getPlacementDetails(String page){

        List<Placement> placementList = placementRepo.findAll(PageRequest.of(Integer.parseInt(page),10, Sort.by("placementId").descending())).getContent();

        if(!placementList.isEmpty()){
            Set<PlacementViewDto> placementViewDtos = new LinkedHashSet<>();

            for(Placement placement : placementList){
                PlacementViewDto placementViewDto = new PlacementViewDto();

                placementViewDto.setBranchName(placement.getBranches().getBranchName());
                placementViewDto.setCourseName(placement.getBranches().getCourse().getCourseName());
                placementViewDto.setCompanies(placement.getCompanies());
                placementViewDto.setPlacementId(placement.getPlacementId());
                placementViewDto.setStudentName(placement.getStudentName());
                placementViewDto.setStudentId(placement.getStudent().getUserId());
                placementViewDto.setBranchId(placement.getBranches().getBranchId());

                placementViewDtos.add(placementViewDto);
            }
            return placementViewDtos;
        }else {
            throw new NotFoundException("No records found");
        }
    }

    /*
    *
    *  Get placement by branch
    *
    */
    public Set<PlacementViewDto> getPlacementDetailsByBranch(Long branchId, String page){

        List<Placement> placementList = placementRepo.findAllByBranch(branchId,  PageRequest.of(Integer.parseInt(page),10, Sort.by("placement_id").descending()));

        if(!placementList.isEmpty()){
            Set<PlacementViewDto> placementViewDtos = new LinkedHashSet<>();

            for(Placement placement : placementList){
                PlacementViewDto placementViewDto = new PlacementViewDto();

                placementViewDto.setBranchName(placement.getBranches().getBranchName());
                placementViewDto.setCourseName(placement.getBranches().getCourse().getCourseName());
                placementViewDto.setCompanies(placement.getCompanies());
                placementViewDto.setPlacementId(placement.getPlacementId());
                placementViewDto.setStudentName(placement.getStudentName());
                placementViewDto.setStudentId(placement.getStudent().getUserId());

                placementViewDtos.add(placementViewDto);
            }
            return placementViewDtos;
        }else {
            throw new NotFoundException("No records found");
        }
    }

    /*
    *
    * View placement details of student by placement id
    *
    */

    public PlacementDetailsDto getPlacementDetailsByPlacementId(Long placementId){

        Optional<Placement> placement = placementRepo.findById(placementId);

        if(placement.isPresent()){
            Set<PlacementData> placementDataSet = placementDataRepo.findAllByPlacementId(placement.get().getPlacementId());

            if(null != placementDataSet){
                Set<CompanyDetailsDto> companyDetailsDtos = new LinkedHashSet<>();

                for(PlacementData placementData : placementDataSet){
                    CompanyDetailsDto companyDetailsDto = new CompanyDetailsDto();

                    companyDetailsDto.setCompanyName(placementData.getCompanyName());
                    companyDetailsDto.setDataId(placementData.getDataId());
                    companyDetailsDto.setSalary(placementData.getSalary());

                    companyDetailsDtos.add(companyDetailsDto);
                }

                PlacementDetailsDto placementDetailsDto = new PlacementDetailsDto();

                placementDetailsDto.setMobileNo(placement.get().getStudent().getMobileNo());
                placementDetailsDto.setEmail(placement.get().getStudent().getEmail());
                placementDetailsDto.setStudentName(placement.get().getStudentName());
                placementDetailsDto.setCompanyDetailsDtos(companyDetailsDtos);

                return placementDetailsDto;
            }else {
                throw new NotFoundException("No records found");
            }
        }else {
            throw new NotFoundException("Placement record not found");
        }
    }

    /*
    *
    *  View placement details by student name
    *
    */
    public Set<PlacementViewDto> getDetailsByStudentName(String studentName, String page){

        List<Placement> placementList = placementRepo.findByStudentName(studentName, PageRequest.of(Integer.parseInt(page),10, Sort.by("placementId").descending()));

        if(!placementList.isEmpty()){
            Set<PlacementViewDto> placementViewDtos = new LinkedHashSet<>();

            for(Placement placement : placementList){
                PlacementViewDto placementViewDto = new PlacementViewDto();

                placementViewDto.setBranchName(placement.getBranches().getBranchName());
                placementViewDto.setCompanies(placement.getCompanies());
                placementViewDto.setCourseName(placement.getBranches().getCourse().getCourseName());
                placementViewDto.setPlacementId(placement.getPlacementId());
                placementViewDto.setStudentName(placement.getStudentName());
                placementViewDto.setStudentId(placement.getStudent().getUserId());
                placementViewDto.setBranchId(placement.getBranches().getBranchId());

                placementViewDtos.add(placementViewDto);
            }
            return placementViewDtos;
        }else {
            throw new NotFoundException("No student record found by name");
        }
    }
}
