package com.college.automation.system.services;

import com.college.automation.system.model.Student;
import com.college.automation.system.repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public List<Student> getStudentsForAttendance(String branch, String year, String section){
        return studentRepo.findAllStudent(branch, year, section);
    }
}
