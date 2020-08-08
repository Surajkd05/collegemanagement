package com.college.automation.system.repos;

import com.college.automation.system.model.UserAttempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttemptRepo extends JpaRepository<UserAttempts,Long> {
    UserAttempts findByUsername(String username);

    void deleteByUsername(String username);
}
