package com.example.collegeautomationsystem.repos;

import com.example.collegeautomationsystem.dtos.CustomSeatProjection;
import com.example.collegeautomationsystem.model.SeatsMapped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatAllocationRepository extends JpaRepository<SeatsMapped, Long> {
	
	@Query("select distinct csm.seatCode as seatCode,csm.roomId as roomId,csm.isOccupied as isOccupied,csm.seatId as seatId,sm.empName as empName, sm.empId as empId from CollegeSeatMaster csm left join SeatsMapped sm on csm.seatId = sm.seatId "
			+ "where csm.roomId=:roomId order by csm.seatCode")
	List<CustomSeatProjection> findSeatAndEmployee(@Param("roomId") Long roomId);

	SeatsMapped findByEmpId(String empId);

}
