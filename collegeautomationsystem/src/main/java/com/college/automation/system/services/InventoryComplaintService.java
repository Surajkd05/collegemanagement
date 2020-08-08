package com.college.automation.system.services;

import com.college.automation.system.dtos.ComplaintViewDto;
import com.college.automation.system.dtos.InventoryComplaintDto;
import com.college.automation.system.dtos.PlacementViewDto;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.InventoryComplaint;
import com.college.automation.system.model.Placement;
import com.college.automation.system.repos.InventoryComplaintRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @Autowired
    private MessageSource messageSource;

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
            String email = messageSource.getMessage("Admin.email", null, LocaleContextHolder.getLocale());
            complaint.setSentTo(email);

            inventoryComplaintRepo.save(complaint);

            sendEmail.sendEmail(messageSource.getMessage("Inventory.complaint", null, LocaleContextHolder.getLocale()),
                    messageSource.getMessage("Complaint.block", null, LocaleContextHolder.getLocale()) + inventoryComplaintDto.getBlock()
                    + " " + messageSource.getMessage("Complaint.room", null, LocaleContextHolder.getLocale()) + inventoryComplaintDto.getRoom()
                    + " " + messageSource.getMessage("Complaint.inventoryName", null, LocaleContextHolder.getLocale()) + inventoryComplaintDto.getInventoryName()
                    + " " + messageSource.getMessage("Complaint.quantity", null, LocaleContextHolder.getLocale()) + inventoryComplaintDto.getQuantity()
                    + " " + messageSource.getMessage("Complaint.complaintBy", null, LocaleContextHolder.getLocale()) + inventoryComplaintDto.getComplaintBy()
                    + " . " + messageSource.getMessage("Complaint.message", null, LocaleContextHolder.getLocale()) + token, email);

            sb.append(messageSource.getMessage("Complaint.registered", null, LocaleContextHolder.getLocale()));
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

            sb.append(messageSource.getMessage("Complaint.resolved", null, LocaleContextHolder.getLocale()));
        }else {
            sb.append(messageSource.getMessage("NotFound.token", null, LocaleContextHolder.getLocale()));
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

            String email = messageSource.getMessage("Admin.email", null, LocaleContextHolder.getLocale());
            sendEmail.sendEmail(messageSource.getMessage("Complaint.reopen", null, LocaleContextHolder.getLocale()), messageSource.getMessage("Complaint.block", null, LocaleContextHolder.getLocale()) + complaint.getBlock()
                    + " "+ messageSource.getMessage("Complaint.room", null, LocaleContextHolder.getLocale()) + complaint.getRoom()
                    + " "+ messageSource.getMessage("Complaint.inventoryName", null, LocaleContextHolder.getLocale()) + complaint.getInventoryName()
                    + " "+ messageSource.getMessage("Complaint.quantity", null, LocaleContextHolder.getLocale()) + complaint.getQuantity()
                    + " "+ messageSource.getMessage("Complaint.complaintBy", null, LocaleContextHolder.getLocale()) + complaint.getComplaintBy()
                    + " . " + messageSource.getMessage("Complaint.message", null, LocaleContextHolder.getLocale())  + token, email);

            sb.append(messageSource.getMessage("Complaint.reopen", null, LocaleContextHolder.getLocale()));
        }else {
            sb.append(messageSource.getMessage("NotFound.token", null, LocaleContextHolder.getLocale()));
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
            throw new NotFoundException(messageSource.getMessage("NotFound.record", null, LocaleContextHolder.getLocale()));
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
            throw new NotFoundException(messageSource.getMessage("NotFound.record", null, LocaleContextHolder.getLocale()));
        }
    }
}
