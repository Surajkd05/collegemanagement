package com.college.automation.system.repos;

import com.college.automation.system.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SheduleRepo extends JpaRepository<Schedule,Long> {

    @Query("from Schedule where branch=:branch and year=:year and section=:section")
    List<Schedule> findSchedule(@Param("branch") String  branch, @Param("year") String year, @Param("section") String section);
}
