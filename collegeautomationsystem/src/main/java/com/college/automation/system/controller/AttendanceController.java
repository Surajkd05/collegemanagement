package com.college.automation.system.controller;

import com.college.automation.system.model.Attendance;
import com.college.automation.system.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping(path = "/add")
    private String addAttendance(@RequestBody List<Attendance> attendance){
        return attendanceService.addAttendance(attendance);
    }
}
