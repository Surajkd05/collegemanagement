package com.college.automation.system.services;

import com.college.automation.system.model.Schedule;
import com.college.automation.system.repos.SheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private SheduleRepo sheduleRepo;

    @Autowired
    private MessageSource messageSource;

    public String addSchedule(Schedule schedule){
        sheduleRepo.save(schedule);
        return messageSource.getMessage("Period.added",null, LocaleContextHolder.getLocale());
    }

    public List<Schedule> findSchedule(String branch, String year, String section){
        return sheduleRepo.findSchedule(branch, year, section);
    }
}
