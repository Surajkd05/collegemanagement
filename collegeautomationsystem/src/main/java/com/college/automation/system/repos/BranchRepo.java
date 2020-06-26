package com.college.automation.system.repos;

import com.college.automation.system.model.Branches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepo extends JpaRepository<Branches, Long> {
}
