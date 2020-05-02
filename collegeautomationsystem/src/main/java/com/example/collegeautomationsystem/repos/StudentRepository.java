package com.example.collegeautomationsystem.repos;

import com.example.collegeautomationsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findUserById(Long userId);
}
