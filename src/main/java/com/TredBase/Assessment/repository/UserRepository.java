package com.TredBase.Assessment.repository;

import com.TredBase.Assessment.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE u.username = ?1")
    AppUser findByUsername(String username);
}