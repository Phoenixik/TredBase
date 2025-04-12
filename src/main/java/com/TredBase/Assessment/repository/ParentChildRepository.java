package com.TredBase.Assessment.repository;

import com.TredBase.Assessment.model.ParentChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentChildRepository extends JpaRepository<ParentChild, Long> {

    @Query("SELECT p FROM ParentChild p WHERE p.studentId = ?1")
    List<ParentChild> listByStudentId(Long studentId);
}