package com.college.automation.system.services;

import com.college.automation.system.model.Attendance;
import com.college.automation.system.repos.AttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;

    public String addAttendance(List<Attendance> attendances){

        for(Attendance attendance : attendances){
            attendanceRepo.save(attendance);
        }

        return "Attendance uploaded";
    }
}
