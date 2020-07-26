package com.college.automation.system.services;

import com.college.automation.system.dtos.StudentViewDto;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.Student;
import com.college.automation.system.repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public List<Student> getStudentsForAttendance(String branch, String year, String section){
        return studentRepo.findAllStudent(branch, year, section);
    }

    public Set<StudentViewDto> getAllStudents(Long branchId, int year, int section, int semester){
        List<Student> students = studentRepo.findAllStudents(branchId, year, section, semester);

        Set<StudentViewDto> studentViewDtos = new LinkedHashSet<>();
        if(!students.isEmpty()) {
            for (Student student : students) {

                if(student.isActive()) {
                    StudentViewDto studentViewDto = new StudentViewDto();

                    studentViewDto.setUserId(student.getUserId());
                    studentViewDto.setActive(student.isActive());
                    studentViewDto.setFirstName(student.getFirstName());
                    studentViewDto.setLastName(student.getLastName());
                    studentViewDto.setEmail(student.getEmail());

                    studentViewDtos.add(studentViewDto);
                }
            }
            return studentViewDtos;
        }else {
            throw new NotFoundException("No students found");
        }
    }
}
