package com.college.automation.system.services;

import com.college.automation.system.dtos.ComplaintViewDto;
import com.college.automation.system.dtos.InventoryComplaintDto;
import com.college.automation.system.dtos.PlacementViewDto;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.InventoryComplaint;
import com.college.automation.system.model.Placement;
import com.college.automation.system.repos.InventoryComplaintRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class InventoryComplaintService {

    @Autowired
    private InventoryComplaintRepo inventoryComplaintRepo;

    @Autowired
    private SendEmail sendEmail;

    public String addComplaint(InventoryComplaintDto inventoryComplaintDto){
        StringBuilder sb = new StringBuilder();

        String token = UUID.randomUUID().toString();

        InventoryComplaint complaint = new InventoryComplaint();

        //String tokenExist = inventoryComplaintRepository.tokenExist(room,inventoryName);

        //if(null != tokenExist) {
            complaint.setToken(token);
            complaint.setComplaintBy(inventoryComplaintDto.getComplaintBy());
            complaint.setBlock(inventoryComplaintDto.getBlock());
            complaint.setInventoryName(inventoryComplaintDto.getInventoryName());
            complaint.setRoom(inventoryComplaintDto.getRoom());
            complaint.setQuantity(inventoryComplaintDto.getQuantity());
            complaint.setActive(false);

            //Testing email
            String email = "surajkd050599@gmail.com";
            complaint.setSentTo(email);

            inventoryComplaintRepo.save(complaint);

            sendEmail.sendEmail("Inventory Complaint", "Complaint for an inventory in block=" + inventoryComplaintDto.getBlock()
                    + "  in room=" + inventoryComplaintDto.getRoom()
                    + "  for inventoryName=" + inventoryComplaintDto.getInventoryName()
                    + "  and quantity=" + inventoryComplaintDto.getQuantity()
                    + "  and complaintBy=" + inventoryComplaintDto.getComplaintBy()
                    + " . After resolving the complaint, please click here : http://localhost:8080/college/invetory/complaint-resolved?token=" + token, email);

            sb.append("Complaint Successfully Registered");
       /* }else {
            sb.append("Complaint is already registered for the same. New complaint cannot be registered for" +
                    "the same unless the previous complaint for same inventory is not resolved.");
        }*/

        return sb.toString();
    }

    @Transactional
    @Modifying
    public String deleteComplaint(String token){

        StringBuilder sb = new StringBuilder();

        InventoryComplaint complaint = inventoryComplaintRepo.findByToken(token);

        if(null != complaint){
            complaint.setActive(true);

            inventoryComplaintRepo.save(complaint);

            sb.append("Complaint resolved");
        }else {
            sb.append("Complaint not exist for this token");
        }
        return sb.toString();
    }

    @Transactional
    @Modifying
    public String reOpenComplaint(String token){

        StringBuilder sb = new StringBuilder();

        InventoryComplaint complaint = inventoryComplaintRepo.findByToken(token);

        if(null != complaint){
            complaint.setActive(false);

            inventoryComplaintRepo.save(complaint);

            String email = "surajkd050599@gmail.com";
            sendEmail.sendEmail("Inventory Complaint Reopen", "Complaint for an inventory in block=" + complaint.getBlock()
                    + "  in room=" + complaint.getRoom()
                    + "  for inventoryName=" + complaint.getInventoryName()
                    + "  and quantity=" + complaint.getQuantity()
                    + "  and complaintBy=" + complaint.getComplaintBy()
                    + " . After resolving the complaint, please click here : http://localhost:8080/college/invetory/complaint-resolved?token=" + token, email);

            sb.append("Complaint reopened");
        }else {
            sb.append("Complaint not exist for this token");
        }
        return sb.toString();
    }

    /*
     *
     * Get all complaint information
     *
     */
    public Set<ComplaintViewDto> getAllComplaints(String page){

        List<InventoryComplaint> inventoryComplaints = inventoryComplaintRepo.findAll(PageRequest.of(Integer.parseInt(page),10, Sort.by("tokenId").descending())).getContent();

        if(!inventoryComplaints.isEmpty()){
            Set<ComplaintViewDto> complaintViewDtos = new LinkedHashSet<>();

            for(InventoryComplaint complaint : inventoryComplaints){
                ComplaintViewDto complaintViewDto = new ComplaintViewDto();

                complaintViewDto.setBlock(complaint.getBlock());
                complaintViewDto.setComplaintBy(complaint.getComplaintBy());
                complaintViewDto.setInventoryName(complaint.getInventoryName());
                complaintViewDto.setQuantity(complaint.getQuantity());
                complaintViewDto.setRoom(complaint.getRoom());
                complaintViewDto.setToken(complaint.getToken());
                complaintViewDto.setActive(complaint.isActive());
                complaintViewDto.setId(complaint.getTokenId());

                complaintViewDtos.add(complaintViewDto);
            }
            return complaintViewDtos;
        }else {
            throw new NotFoundException("No records found");
        }
    }

    /*
     *
     *  View complaint details by token
     *
     */
    public Set<ComplaintViewDto> getDetailsByToken(String token){

        InventoryComplaint inventoryComplaint = inventoryComplaintRepo.findByToken(token);

        Set<ComplaintViewDto> complaintViewDtos = new LinkedHashSet<>();
        if(null != inventoryComplaint){
                ComplaintViewDto complaintViewDto = new ComplaintViewDto();

                complaintViewDto.setActive(inventoryComplaint.isActive());
                complaintViewDto.setToken(inventoryComplaint.getToken());
                complaintViewDto.setRoom(inventoryComplaint.getRoom());
                complaintViewDto.setQuantity(inventoryComplaint.getQuantity());
                complaintViewDto.setInventoryName(complaintViewDto.getInventoryName());
                complaintViewDto.setBlock(inventoryComplaint.getBlock());
                complaintViewDto.setComplaintBy(inventoryComplaint.getComplaintBy());
                complaintViewDto.setId(inventoryComplaint.getTokenId());

                complaintViewDtos.add(complaintViewDto);
            return complaintViewDtos;
        }else {
            throw new NotFoundException("No record found by token");
        }
    }
}
