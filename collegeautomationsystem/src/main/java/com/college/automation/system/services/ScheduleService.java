package com.college.automation.system.services;

import com.college.automation.system.model.Schedule;
import com.college.automation.system.repos.SheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private SheduleRepo sheduleRepo;

    public String addSchedule(Schedule schedule){
        sheduleRepo.save(schedule);
        return "Periods added";
    }

    public List<Schedule> findSchedule(String branch, String year, String section){
        System.out.println("Branch is : "+branch +" year is : "+year+ " section is : "+section);
        return sheduleRepo.findSchedule(branch, year, section);
    }
}
