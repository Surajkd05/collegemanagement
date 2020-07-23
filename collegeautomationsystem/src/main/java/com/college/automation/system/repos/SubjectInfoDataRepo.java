package com.college.automation.system.repos;


import com.college.automation.system.model.SubjectInfoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubjectInfoDataRepo extends JpaRepository<SubjectInfoData, Long> {

    @Query(value = "Select * from subject_info_data where info_id=:infoId ORDER BY data_id ASC",nativeQuery = true)
    Set<SubjectInfoData> findByInfoId(@Param("infoId") Long infoId);
}
