package com.example.collegeautomationsystem.controller;

import com.example.collegeautomationsystem.services.InventoryComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/college/inventory")
public class InventoryController {

    @Autowired
    private InventoryComplaintService complaintService;

    @PostMapping("/register-complaint")
    public String registerComplaint(@RequestParam("complaintBy") String complaintBy, @RequestParam("block") String block,
                                    @RequestParam("room") String room, @RequestParam("inventoryName") String inventoryName,
                                    @RequestParam("quantity") String quantity, HttpServletResponse response){
        String message = complaintService.addComplaint(complaintBy,block,room,inventoryName,quantity);

        if(!message.equals("Complaint Successfully Registered")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PatchMapping("/complaint-resolved")
    public String deleteComplaint(@RequestParam("token") String token, HttpServletResponse response){

        System.out.println("Token received is : "+token);
        String message = complaintService.deleteComplaint(token);

        if(!message.equals("Complaint resolved")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }
}
