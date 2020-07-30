package com.college.automation.system.services;

import com.college.automation.system.dtos.BranchDto;
import com.college.automation.system.dtos.BranchViewDto;
import com.college.automation.system.dtos.CourseDto;
import com.college.automation.system.dtos.CourseViewDto;
import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.Branches;
import com.college.automation.system.model.Course;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.BranchRepo;
import com.college.automation.system.repos.CourseRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private UserRepo userRepo;

    /*
     *
     * Add course service
     *
     */
    public String addCourse(String username, CourseDto courseDto){
        User admin = userRepo.findByEmail(username);
        if(null != admin){
            Course course = new Course();

            course.setCourseName(courseDto.getCourseName());

            courseRepo.save(course);

            return "Course added successfully";
        }else {
            throw new BadRequestException("You are not authorized to add course");
        }
    }

    /*
     *
     * Get course service
     *
     */

    public Set<CourseViewDto> getCourse(){
        List<Course> courseList =  courseRepo.findAll();

        if(!courseList.isEmpty()){
            Set<CourseViewDto> courseViewDtos = new LinkedHashSet<>();

            for(Course course : courseList){
                CourseViewDto courseViewDto = new CourseViewDto();

                courseViewDto.setCourseId(course.getCourseId());

                courseViewDto.setCourseName(course.getCourseName());

                courseViewDtos.add(courseViewDto);
            }
            return courseViewDtos;
        }else {
            throw new NotFoundException("No course found");
        }
    }
}

