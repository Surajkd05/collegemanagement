package com.college.automation.system.controller;

import com.college.automation.system.dtos.ComplaintViewDto;
import com.college.automation.system.dtos.InventoryComplaintDto;
import com.college.automation.system.services.InventoryComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

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

    @PatchMapping("/complaintResolved")
    public String deleteComplaint(@RequestParam("token") String token, HttpServletResponse response){

        String message = complaintService.deleteComplaint(token);

        if(!message.equals("Complaint resolved")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PatchMapping("/reopenComplaint")
    public String reOpenComplaint(@RequestParam("token") String token, HttpServletResponse response){

        String message = complaintService.reOpenComplaint(token);

        if(!message.equals("Complaint reopened")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    /*
     *
     * Get all complaints
     *
     */
    @GetMapping(path = "/all")
    public Set<ComplaintViewDto> getAllPlacementData(@RequestParam(defaultValue = "0") String page) {
        return complaintService.getAllComplaints(page);
    }

    /*
     *
     * Get details by token
     *
     */
    @GetMapping(path = "/token")
    public Set<ComplaintViewDto> getRecordsByToken(@RequestParam(value = "token") String token) {
        return complaintService.getDetailsByToken(token);
    }
}
