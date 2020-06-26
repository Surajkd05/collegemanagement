package com.college.automation.system.repos;

import com.college.automation.system.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    Employee findByUserId(Long userId);

    Employee findByEmail(String username);
}
