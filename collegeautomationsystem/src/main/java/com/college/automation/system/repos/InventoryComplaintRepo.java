package com.college.automation.system.repos;

import com.college.automation.system.model.InventoryComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryComplaintRepo extends JpaRepository<InventoryComplaint, Long> {

    @Query(value = "Select token from inventory_complaint where room=:room and inventoryName=:inventoryName",nativeQuery = true)
    String tokenExist(@Param("room") String room, @Param("inventoryName") String inventoryName);

    InventoryComplaint findByToken(String token);

    void deleteByToken(String token);

}
