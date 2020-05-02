package com.example.collegeautomationsystem.repos;

import com.example.collegeautomationsystem.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivateRepo extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByUserEmail(String email);
    VerificationToken findByToken(String token);
    void deleteByUserEmail(String email);
}
