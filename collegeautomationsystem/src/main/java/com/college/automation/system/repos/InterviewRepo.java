package com.college.automation.system.repos;

import com.college.automation.system.model.InterviewTips;
import com.college.automation.system.model.Questions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepo extends JpaRepository<InterviewTips, Long> {

    @Query(value = "select * from interview_tips where branch_id=:branchId",nativeQuery = true)
    List<InterviewTips> findAllByBranch(@Param("branchId") Long branchId, Pageable pageable);
}
