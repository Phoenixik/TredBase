package com.TredBase.Assessment.repository;

import com.TredBase.Assessment.model.Parent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
//@Transactional
public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query("SELECT p FROM Parent p WHERE p.email = ?1")
    Parent findByEmail(String email);
}