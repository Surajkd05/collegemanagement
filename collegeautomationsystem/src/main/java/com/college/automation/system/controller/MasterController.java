package com.college.automation.system.controller;

import com.college.automation.system.dtos.SeatDto;
import com.college.automation.system.model.DepartmentMaster;
import com.college.automation.system.model.CollegeSeatMaster;
import com.college.automation.system.repos.DepartmentMasterRepo;
import com.college.automation.system.services.CollegeSeatMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master")
public class MasterController {
	
	@Autowired
	private CollegeSeatMasterService collegeSeatMasterService;

	@Autowired
	private DepartmentMasterRepo departmentMasterRepo;

	@PostMapping("/createSeat")
	public String createSeat(@RequestBody SeatDto seatMaster) {

		return collegeSeatMasterService.createCollegeMaster(seatMaster);
	}

	@PutMapping("/updateSeat")
	public CollegeSeatMaster updateSeat(@RequestBody CollegeSeatMaster seatMaster) {

		return collegeSeatMasterService.updateCollegeMaster(seatMaster);
	}

	@GetMapping("/getSeatByRoom/{roomId}")
	public List<CollegeSeatMaster> getSeatByRoom(@PathVariable(name="roomId") Long id) {
		return collegeSeatMasterService.findSeatByRoomId(id);
	}
	
	@GetMapping("/viewSeatById/{id}")
	public CollegeSeatMaster getSeatById(@PathVariable(name="id") Long seatId) {
		return collegeSeatMasterService.findBySeatId(seatId);
	}

	@PostMapping("/createDepartmentRoom")
	public DepartmentMaster createDepartmentRoom(@RequestBody DepartmentMaster departmentMaster) {
		departmentMaster.setOccupied(0);
		try{
			departmentMaster= departmentMasterRepo.save(departmentMaster);
		}catch(Exception e){
			e.printStackTrace();
		}
		return departmentMaster;
	}

	@GetMapping("/getRoomByDepartmentId/{departmentId}")
	public List<DepartmentMaster> getRoomByDepartmentId(@PathVariable(name="departmentId") Long deptId) {
		return departmentMasterRepo.findByDeptId(deptId);
	}

	@GetMapping("/getAllProjectRooms")
	public List<DepartmentMaster> getAllDepartmentRooms(){
		
		return departmentMasterRepo.findAll();
		
	}
}
