package com.college.automation.system.controller;

import com.college.automation.system.dtos.PlacementDto;
import com.college.automation.system.model.Schedule;
import com.college.automation.system.model.Student;
import com.college.automation.system.services.PlacementService;
import com.college.automation.system.services.ScheduleService;
import com.college.automation.system.services.StudentService;
import com.college.automation.system.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PlacementService placementService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @GetMapping(path="/studentList")
    private List<Student> getStudentsForAttendance(@RequestParam String branch, @RequestParam String year, @RequestParam String section){
            return studentService.getStudentsForAttendance(branch, year, section);
    }

    @GetMapping(path = "/getSchedule")
    public List<Schedule> getSchedule(@RequestParam String branch, @RequestParam String year, @RequestParam String section){
        System.out.println("Branch is : "+branch +" year is : "+year+ " section is : "+section);
        return scheduleService.findSchedule(branch, year, section);
    }
}
