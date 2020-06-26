package com.college.automation.system.repos;

import com.college.automation.system.model.DepartmentMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMasterRepo extends JpaRepository<DepartmentMaster, Long> {
	List<DepartmentMaster> findByDeptId(Long deptId);

	DepartmentMaster findByRoomId(Long roomId);
}
