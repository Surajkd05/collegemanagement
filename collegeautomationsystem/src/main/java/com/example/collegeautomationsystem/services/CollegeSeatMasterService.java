package com.example.collegeautomationsystem.services;

import com.example.collegeautomationsystem.model.CollegeSeatMaster;
import com.example.collegeautomationsystem.repos.CollegeSeatMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CollegeSeatMasterService{

	@Autowired
	private CollegeSeatMasterRepository seatMasterRepository;

	public CollegeSeatMaster createCollegeMaster(CollegeSeatMaster seatMaster) {
		return seatMasterRepository.save(seatMaster);
	}

	public CollegeSeatMaster updateCollegeMaster(CollegeSeatMaster seatMaster) {
		return seatMasterRepository.save(seatMaster);
	}

	public List<CollegeSeatMaster> findAllCollegeSeatMaster() {
		return seatMasterRepository.findAll();
	}

	public CollegeSeatMaster findBySeatId(Long seatId) {
		return seatMasterRepository.findBySeatId(seatId);
	}

	public List<CollegeSeatMaster> findSeatByRoomId(Long roomId) {
		return seatMasterRepository.findByRoomId(roomId);
	}

}
