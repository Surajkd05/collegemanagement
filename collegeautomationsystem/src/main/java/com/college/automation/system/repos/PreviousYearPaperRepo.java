package com.college.automation.system.repos;

import com.college.automation.system.model.PreviousYearPapers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PreviousYearPaperRepo extends JpaRepository<PreviousYearPapers, Long> {

    @Query("from PreviousYearPapers where branchId=:branchId and year=:year and semester=:semester")
    Set<PreviousYearPapers> findByBranch(@Param("branchId") Long branchId, @Param("year") int year, @Param("semester") int semester);

}
