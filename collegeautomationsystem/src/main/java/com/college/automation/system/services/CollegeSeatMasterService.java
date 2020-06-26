package com.college.automation.system.services;

import com.college.automation.system.dtos.SeatDto;
import com.college.automation.system.model.CollegeSeatMaster;
import com.college.automation.system.model.DepartmentMaster;
import com.college.automation.system.repos.CollegeSeatMasterRepo;
import com.college.automation.system.repos.DepartmentMasterRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CollegeSeatMasterService{

	@Autowired
	private CollegeSeatMasterRepo seatMasterRepository;

	@Autowired
	private DepartmentMasterRepo departmentMasterRepo;

	public String createCollegeMaster(SeatDto seatMaster) {

		String  s = "";
		CollegeSeatMaster seatMaster1 = new CollegeSeatMaster();
		BeanUtils.copyProperties(seatMaster,seatMaster1);
		DepartmentMaster departmentMaster = departmentMasterRepo.findByRoomId(seatMaster.getRoomId());
		if(departmentMaster.getOccupied() < departmentMaster.getTotalCapacity()) {
			CollegeSeatMaster response = seatMasterRepository.save(seatMaster1);
			response.setSeatCode(seatMaster.getDeptId() + "-" + seatMaster.getRoomId() + "-" + response.getSeatId());
			seatMasterRepository.save(response);
			departmentMaster.setOccupied(departmentMaster.getOccupied()+1);
			departmentMasterRepo.save(departmentMaster);
			s = "Seat created and seat code is : "+seatMaster.getDeptId() + "-" + seatMaster.getRoomId() + "-" + response.getSeatId();
			return s;
		}else {
			return "Seat cannot be created, room is not vacant to create seat";
		}
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
