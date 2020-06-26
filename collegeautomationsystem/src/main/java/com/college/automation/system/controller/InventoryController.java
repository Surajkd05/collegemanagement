package com.college.automation.system.controller;

import com.college.automation.system.dtos.InventoryComplaintDto;
import com.college.automation.system.services.InventoryComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryComplaintService complaintService;

    @PostMapping("/register-complaint")
    public String registerComplaint(@RequestBody InventoryComplaintDto inventoryComplaintDto, HttpServletResponse response){
        String message = complaintService.addComplaint(inventoryComplaintDto);

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
