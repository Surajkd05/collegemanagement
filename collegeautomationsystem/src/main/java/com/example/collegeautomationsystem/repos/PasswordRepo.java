package com.example.collegeautomationsystem.repos;

import com.example.collegeautomationsystem.model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepo extends JpaRepository<PasswordToken,Long> {
    PasswordToken findByUserEmail(String email);
    void deleteByUserEmail(String email);
}
