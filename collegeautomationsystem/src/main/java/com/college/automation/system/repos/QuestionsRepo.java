package com.college.automation.system.repos;

import com.college.automation.system.model.Questions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepo extends JpaRepository<Questions, Long> {

    @Query(value = "select * from questions where branch_id=:branchId",nativeQuery = true)
    List<Questions> findAllByBranch(@Param("branchId") Long branchId, Pageable pageable);
}
