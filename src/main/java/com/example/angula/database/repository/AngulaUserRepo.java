package com.example.angula.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.angula.database.model.AngulaUser;

@Repository
public interface AngulaUserRepo extends JpaRepository<AngulaUser, Long> {
    Optional<AngulaUser> findByUsername(String username);
    
}
