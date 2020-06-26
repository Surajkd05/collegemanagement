package com.college.automation.system.repos;

import com.college.automation.system.model.PlacementData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlacementDataRepo extends JpaRepository<PlacementData, Long> {

    @Query(value = "select * from placement_data where placement_id=:placementId",nativeQuery = true)
    Set<PlacementData> findAllByPlacementId(@Param("placementId") Long placementId);
}
