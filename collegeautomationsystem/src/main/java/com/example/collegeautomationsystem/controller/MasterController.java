package com.example.collegeautomationsystem.controller;

import com.example.collegeautomationsystem.model.DepartmentMaster;
import com.example.collegeautomationsystem.model.CollegeSeatMaster;
import com.example.collegeautomationsystem.repos.DepartmentMasterRepository;
import com.example.collegeautomationsystem.services.CollegeSeatMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/college/master")
public class MasterController {
	
	@Autowired
	private CollegeSeatMasterService collegeSeatMasterService;

	@Autowired
	private DepartmentMasterRepository departmentMasterRepository;

	@PostMapping("/createSeat")
	public CollegeSeatMaster createSeat(@RequestBody CollegeSeatMaster seatMaster) {

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
		return departmentMasterRepository.save(departmentMaster);
	}

	@GetMapping("/getRoomByDepartmentId/{departmentId}")
	public List<DepartmentMaster> getRoomByDepartmentId(@PathVariable(name="departmentId") Long deptId) {
		return departmentMasterRepository.findByDeptId(deptId);
	}

	@GetMapping("/getAllProjectRooms")
	public List<DepartmentMaster> getAllDepartmentRooms(){
		
		return departmentMasterRepository.findAll();
		
	}
}
