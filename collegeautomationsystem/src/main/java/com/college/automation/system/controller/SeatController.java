package com.college.automation.system.controller;

import com.college.automation.system.dtos.CustomSeatProjection;
import com.college.automation.system.dtos.SeatOccupancy;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.CollegeSeatMaster;
import com.college.automation.system.model.ImageData;
import com.college.automation.system.model.SeatsMapped;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.SeatAllocationRepo;
import com.college.automation.system.services.ImageService;
import com.college.automation.system.services.SeatAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/seatAllocation")
public class SeatController {
	
	@Autowired
	private SeatAllocationService seatAllocationService;
	
	@Autowired
	private SeatAllocationRepo seatAllocationRepo;

	@Autowired
	private ImageService imageService;

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
	
	@GetMapping("/deallocateSeat")
	public CollegeSeatMaster deAllocateSeat(@RequestParam(value = "seatId") Long seatId) {
		return seatAllocationService.deallocateSeat(seatId);
	}
	

	@GetMapping("/getSeatInfo")
	public User isAllocated(@RequestParam(name="seatId") Long seatId) {
		return seatAllocationService.getSeatInfo(seatId);
	}

	@GetMapping(path = "/image")
	public ResponseEntity<?> getProfileImage(@RequestParam(name="seatId") Long seatId) {

		ImageData imageData = imageService.downloadImage(seatAllocationService.getSeatInfo(seatId).getEmail());

		if(null != imageData) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(imageData.getContentType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageData.getFileName() + "\"")
					.body(new ByteArrayResource(imageData.getProfileImage()));
		}else {
			throw new NotFoundException("Image not found");
		}
	}
}
