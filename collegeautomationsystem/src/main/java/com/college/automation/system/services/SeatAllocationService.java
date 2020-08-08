package com.college.automation.system.services;

import com.college.automation.system.dtos.CustomSeatProjection;
import com.college.automation.system.dtos.SeatOccupancy;
import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.model.*;
import com.college.automation.system.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeatAllocationService {

	@Autowired
	private SeatAllocationRepo seatAllocationRepo;

	@Autowired
	private SeatOccupancy seatOccupancy;

	@Autowired
	private CollegeSeatMasterRepo seatMasterRepository;

	private SeatsMapped seatsMapped;
	
	private CollegeSeatMaster seatMaster;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private MessageSource messageSource;

	public String allocateSeat(SeatsMapped seatMapped) {

		StringBuilder sb = new StringBuilder();
		boolean check = false;
		CollegeSeatMaster seatMaster = seatMasterRepository.findBySeatId(seatMapped.getSeatId());

		Optional<User> user = userRepo.findById(seatMapped.getEmpId());
		if (!seatMaster.isOccupied()) {
			seatMapped.setEmpName(user.get().getFirstName() + " " + user.get().getLastName());
			seatMapped.setCreatedAt(new Date());
			seatsMapped = seatAllocationRepo.save(seatMapped);
			check = true;
		}
		if (null != seatsMapped) {

			seatMaster.setUpdatedAt(seatMapped.getCreatedAt());
			seatMaster.setOccupied(true);
			seatMasterRepository.save(seatMaster);

		} else {
			throw new BadRequestException(messageSource.getMessage("Seat.NotAllocated", null, LocaleContextHolder.getLocale()));
		}
		if (seatMasterRepository.findBySeatId(seatMapped.getSeatId()).isOccupied() && check) {
			sb.append(messageSource.getMessage("Seat.Allocated", null, LocaleContextHolder.getLocale()) +" ").append(seatMapped.getEmpName()).append(" Is ")
					.append(seatMaster.getSeatCode());
		}
		return sb.toString();
	}

	public List<CustomSeatProjection> getAllSeats(Long roomId) {
		return seatAllocationRepo.findSeatAndEmployee(roomId);
	}

	public SeatOccupancy getOccupancyDataForRoom(Long roomId) {
		int occupiedSeats = seatMasterRepository.findOccupiedSeats(roomId);
		int totalSeats = seatMasterRepository.findTotalSeats(roomId);
		int vacantSeats = totalSeats - occupiedSeats;

		seatOccupancy.setTotalSeats(totalSeats);
		seatOccupancy.setOccupiedSeats(occupiedSeats);
		seatOccupancy.setUnOccupiedSeats(vacantSeats);

		return seatOccupancy;
	}

	public CollegeSeatMaster deallocateSeat(Long seatId) {
		seatsMapped= seatAllocationRepo.findBySeatId(seatId);
		seatAllocationRepo.delete(seatsMapped);
		seatMaster=seatMasterRepository.findBySeatId(seatId);
		seatMaster.setOccupied(false);
		seatMaster=seatMasterRepository.save(seatMaster);
		return seatMaster;
	}

	public User getSeatInfo(Long seatId) {
		seatsMapped = seatAllocationRepo.findBySeatId(seatId);
		System.out.println("Seats mapped is : "+seatsMapped);
		return employeeRepo.findByUserId(seatsMapped.getEmpId());
	}

	public Set<Employee> getEmployeeByBranch(Long branchId){

		Optional<Branches> branches = branchRepo.findById(branchId);
		List<Employee> employees = new ArrayList<>();
		try{
		 employees = employeeRepo.findByBranches(branches.get());
		}catch (Exception e){
			e.printStackTrace();
		}
		Set<Employee> employeeSet = new LinkedHashSet<>();
		for(Employee employee : employees){
			SeatsMapped seatsMapped = seatAllocationRepo.findByEmpId(employee.getUserId());
			if(null == seatsMapped){
				employeeSet.add(employee);
			}
		}
		return employeeSet;
	}
}
