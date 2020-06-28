package com.college.automation.system.repos;

import com.college.automation.system.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long> {

    @Query(value = "Select subject_name,subject_code from subject where branch_id=:branchId and year=:year",nativeQuery = true)
    Set<Subject> findSubjectByBranchAndYear(@Param("branchId") Long branchId, @Param("year") int year);
}
