package com.college.automation.system.repos;

import com.college.automation.system.model.Placement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlacementRepo extends JpaRepository<Placement, Long> {
    @Query(value = "Select * from placement where student_id=:userId",nativeQuery = true)
    Placement findByUserId(@Param("userId") Long userId);

    @Query(value = "select * from placement where branch_id=:branchId",nativeQuery = true)
    List<Placement> findAllByBranch(@Param("branchId") Long branchId, Pageable pageable);

    @Query("from Placement where studentName like %:studentName%")
    List<Placement> findByStudentName(@Param("studentName") String studentName, Pageable pageable);
}
