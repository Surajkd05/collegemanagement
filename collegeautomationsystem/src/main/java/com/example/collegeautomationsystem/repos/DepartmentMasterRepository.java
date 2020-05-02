package com.example.collegeautomationsystem.repos;

import com.example.collegeautomationsystem.model.DepartmentMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMasterRepository extends JpaRepository<DepartmentMaster, Long> {
	List<DepartmentMaster> findByDeptId(Long deptId);
}
