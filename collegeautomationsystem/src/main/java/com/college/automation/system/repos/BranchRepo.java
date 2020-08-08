package com.college.automation.system.repos;

import com.college.automation.system.model.Branches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepo extends JpaRepository<Branches, Long> {
    @Query(value = "Select * from branches where course_id =:courseId",nativeQuery = true)
    List<Branches> findAllBranchesByCourse(@Param("courseId") Long courseId);
}
