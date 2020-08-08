package com.college.automation.system.repos;

import com.college.automation.system.model.Answers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswersRepo extends JpaRepository<Answers, Long> {
    @Query(value = "select * from answers where question_id=:questionId",nativeQuery = true)
    List<Answers> findAllByQuestion(@Param("questionId") Long questionId, Pageable pageable);
}
