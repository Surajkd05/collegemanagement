package com.college.automation.system.repos;

import com.college.automation.system.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {

    @Query("from Student where branch=:branch and year=:year and section=:section")
    List<Student> findAllStudent(@Param("branch") String  branch, @Param("year") String year, @Param("section") String section);

    Student findByEmail(String username);
}
