package com.college.automation.system.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CollegeSeatMaster {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long seatId;
	
	private Long roomId;
	
	private String seatCode;
	
	private boolean isOccupied;
	
	@Temporal(value = TemporalType.DATE)
    private Date createdAt; 

	@Temporal(value = TemporalType.DATE)
    private Date updatedAt; 
	
	
	public Long getSeatId() {
		return seatId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
