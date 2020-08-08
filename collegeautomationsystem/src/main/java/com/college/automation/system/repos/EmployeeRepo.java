package com.college.automation.system.repos;

import com.college.automation.system.model.Branches;
import com.college.automation.system.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    Employee findByUserId(Long userId);

    Employee findByEmail(String username);

    List<Employee> findByBranches(Branches branches);

    @Query(value = "select * from employee where branch_id=:branchId",nativeQuery = true)
    List<Employee> findEmployeeByBranch(@Param(value = "branchId") Long branchId);
}
