package com.college.automation.system.repos;

import com.college.automation.system.model.SubjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectInfoRepo extends JpaRepository<SubjectInfo, Long> {

    @Query("from SubjectInfo where branchId=:branchId and year=:year and sectionId=:sectionId and subjectId=:subjectId")
    SubjectInfo findSubjectInfoUniqueData(@Param("branchId") Long branchId, @Param("year") int year, @Param("sectionId") int sectionId , @Param("subjectId") Long subjectId);
}
