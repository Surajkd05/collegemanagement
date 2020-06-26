package com.college.automation.system.controller;

import com.college.automation.system.model.Schedule;
import com.college.automation.system.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping(path = "/addSchedule")
    public String addSchedule(@RequestBody Schedule schedule){
        return scheduleService.addSchedule(schedule);
    }
}
