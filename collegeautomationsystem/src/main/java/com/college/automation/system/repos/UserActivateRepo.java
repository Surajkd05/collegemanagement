package com.college.automation.system.repos;

import com.college.automation.system.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivateRepo extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByUserEmail(String email);
    VerificationToken findByToken(String token);
    void deleteByUserEmail(String email);
}
