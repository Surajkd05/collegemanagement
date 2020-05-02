package com.example.collegeautomationsystem.services;

import com.example.collegeautomationsystem.dtos.CustomSeatProjection;
import com.example.collegeautomationsystem.dtos.SeatOccupancy;
import com.example.collegeautomationsystem.model.CollegeSeatMaster;
import com.example.collegeautomationsystem.model.SeatsMapped;
import com.example.collegeautomationsystem.repos.SeatAllocationRepository;
import com.example.collegeautomationsystem.repos.CollegeSeatMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatAllocationService {

	@Autowired
	private SeatAllocationRepository seatAllocationRepository;

	@Autowired
	private SeatOccupancy seatOccupancy;

	@Autowired
	private CollegeSeatMasterRepository seatMasterRepository;

	private SeatsMapped seatsMapped;
	
	private CollegeSeatMaster seatMaster;

	public String allocateSeat(SeatsMapped seatMapped) {

		StringBuilder sb = new StringBuilder();
		boolean check = false;
		CollegeSeatMaster seatMaster = seatMasterRepository.findBySeatId(seatMapped.getSeatId());
		if (!seatMaster.isOccupied()) {
			seatsMapped = seatAllocationRepository.save(seatMapped);
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
		return seatAllocationRepository.findSeatAndEmployee(roomId);
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

	public CollegeSeatMaster deallocateSeat(String empId) {
		seatsMapped=seatAllocationRepository.findByEmpId(empId);
		seatAllocationRepository.delete(seatsMapped);
		seatMaster=seatMasterRepository.findBySeatId(seatsMapped.getSeatId());
		seatMaster.setOccupied(false);
		seatMaster=seatMasterRepository.save(seatMaster);
		return seatMaster;
	}
}
