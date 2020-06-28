package com.college.automation.system.repos;

import com.college.automation.system.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<ImageData, Long> {

    @Query("from ImageData where userId=:userId")
    ImageData findImageByUserId(@Param("userId") Long userId);
}