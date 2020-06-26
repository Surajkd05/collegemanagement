package com.college.automation.system.services;

import com.college.automation.system.dtos.CustomSeatProjection;
import com.college.automation.system.dtos.SeatOccupancy;
import com.college.automation.system.model.CollegeSeatMaster;
import com.college.automation.system.model.SeatsMapped;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.SeatAllocationRepo;
import com.college.automation.system.repos.CollegeSeatMasterRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
			sb.append(" Unable to allocate seat ");
		}
		if (seatMasterRepository.findBySeatId(seatMapped.getSeatId()).isOccupied() && check) {
			sb.append("Seat Allocated to the Employee Name: ").append(seatMapped.getEmpName()).append(" Is ")
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
		return userRepo.findByUserId(seatsMapped.getEmpId());
	}
}
