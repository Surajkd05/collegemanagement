package com.example.collegeautomationsystem.controller;

import com.example.collegeautomationsystem.dtos.CustomSeatProjection;
import com.example.collegeautomationsystem.dtos.SeatOccupancy;
import com.example.collegeautomationsystem.model.CollegeSeatMaster;
import com.example.collegeautomationsystem.model.SeatsMapped;
import com.example.collegeautomationsystem.repos.SeatAllocationRepository;
import com.example.collegeautomationsystem.services.SeatAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/college/seatAllocation")
public class SeatController {
	
	@Autowired
	private SeatAllocationService seatAllocationService;
	
	@Autowired
	private SeatAllocationRepository seatAllocationRepository;
	
	@PostMapping("/allocateSeat")
	public String allocateSeat(@RequestBody SeatsMapped seatMapped) {
		
		return seatAllocationService.allocateSeat(seatMapped);
				
	}
	
	@GetMapping("/getAllSeats/{roomId}")
	public List<CustomSeatProjection> getAllSeats(@PathVariable(name="roomId") Long roomId) {
		
		return seatAllocationService.getAllSeats(roomId);
				
	}
	
	@GetMapping("/getSeatRoomOccupancy/{roomId}")
	public SeatOccupancy getSeatRoomOccupancy(@PathVariable(name="roomId") Long roomId) {
		
		return seatAllocationService.getOccupancyDataForRoom(roomId);
				
	}
	
	@PostMapping("/deallocateSeat")
	public CollegeSeatMaster deAllocateSeat(@RequestBody SeatsMapped seatMapped) {
		return seatAllocationService.deallocateSeat(seatMapped.getEmpId());
	}
	

	@GetMapping("/getSeatInfo/{empId}")
	public SeatsMapped isAllocated(@PathVariable(name="empId") String empId) {
		return seatAllocationRepository.findByEmpId(empId);
	}

}
