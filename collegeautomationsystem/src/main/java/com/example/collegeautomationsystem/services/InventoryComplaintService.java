package com.example.collegeautomationsystem.services;

import com.example.collegeautomationsystem.model.InventoryComplaint;
import com.example.collegeautomationsystem.repos.InventoryComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryComplaintService {

    @Autowired
    private InventoryComplaintRepository inventoryComplaintRepository;

    @Autowired
    private SendEmail sendEmail;

    public String addComplaint(String complaintBy, String block, String room, String inventoryName, String quantity){
        StringBuilder sb = new StringBuilder();

        String token = UUID.randomUUID().toString();

        InventoryComplaint complaint = new InventoryComplaint();

        //String tokenExist = inventoryComplaintRepository.tokenExist(room,inventoryName);

        //if(null != tokenExist) {
            complaint.setToken(token);
            complaint.setComplaintBy(complaintBy);
            complaint.setBlock(block);
            complaint.setInventoryName(inventoryName);
            complaint.setRoom(room);
            complaint.setQuantity(quantity);

            //Testing email
            String email = "surajkd050599@gmail.com";
            complaint.setSentTo(email);

            inventoryComplaintRepository.save(complaint);

            sendEmail.sendEmail("Inventory Complaint", "Complaint for an inventory in block=" + block
                    + "  in room=" + room
                    + "  for inventoryName=" + inventoryName
                    + "  and quantity=" + quantity
                    + "  and complaintBy=" + complaintBy
                    + " . After resolving the complaint, please click here : " +
                    "http://localhost:8080/college/invetory/complaint-resolved/" + token, email);

            sb.append("Complaint Successfully Registered");
       /* }else {
            sb.append("Complaint is already registered for the same. New complaint cannot be registered for" +
                    "the same unless the previous complaint for same inventory is not resolved.");
        }*/

        return sb.toString();
    }

    public String deleteComplaint(String token){

        StringBuilder sb = new StringBuilder();

        InventoryComplaint complaint = inventoryComplaintRepository.findByToken(token);

        System.out.println("Data comes from table is : "+complaint);

        if(null != complaint){
            inventoryComplaintRepository.deleteByToken(token);

            sb.append("Complaint resolved");
        }else {
            sb.append("Complaint not exist for this token");
        }
        return sb.toString();
    }
}
