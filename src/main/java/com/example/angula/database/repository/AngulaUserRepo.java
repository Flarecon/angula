package com.example.angula.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.angula.database.model.AngulaUser;

@Repository
public interface AngulaUserRepo extends JpaRepository<AngulaUser, Long> {
    AngulaUser findByUsername(String username);
    
}
