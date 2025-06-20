package com.example.angula.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.angula.database.model.AngulaClient;

@Repository
public interface ClientRepo extends JpaRepository<AngulaClient, Long> {
    
}
