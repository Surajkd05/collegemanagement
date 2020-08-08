package com.college.automation.system.repos;

import com.college.automation.system.model.CollegeSeatMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CollegeSeatMasterRepo extends JpaRepository<CollegeSeatMaster, Long> {
	
	CollegeSeatMaster findBySeatId(Long seatId);

	List<CollegeSeatMaster> findByRoomId(Long roomId);
	
	@Query("select count(seatId) from CollegeSeatMaster where roomId=:roomId")
	int findTotalSeats(@Param("roomId") Long roomId);
	
	@Query("select count(seatId) from CollegeSeatMaster where roomId=:roomId and isOccupied=true")
	int findOccupiedSeats(@Param("roomId") Long roomId);
}
