package com.college.automation.system.services;

import com.college.automation.system.dtos.InventoryComplaintDto;
import com.college.automation.system.model.InventoryComplaint;
import com.college.automation.system.repos.InventoryComplaintRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

            //Testing email
            String email = "surajkd050599@gmail.com";
            complaint.setSentTo(email);

            inventoryComplaintRepo.save(complaint);

            sendEmail.sendEmail("Inventory Complaint", "Complaint for an inventory in block=" + inventoryComplaintDto.getBlock()
                    + "  in room=" + inventoryComplaintDto.getRoom()
                    + "  for inventoryName=" + inventoryComplaintDto.getInventoryName()
                    + "  and quantity=" + inventoryComplaintDto.getQuantity()
                    + "  and complaintBy=" + inventoryComplaintDto.getComplaintBy()
                    + " . After resolving the complaint, please click here : " + " token=" + token, email);
//                    "http://localhost:8080/college/invetory/complaint-resolved?token=" + token, email);

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

        System.out.println("Data comes from table is : "+complaint);

        if(null != complaint){
            inventoryComplaintRepo.deleteByToken(token);

            sb.append("Complaint resolved");
        }else {
            sb.append("Complaint not exist for this token");
        }
        return sb.toString();
    }
}
