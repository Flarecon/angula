package com.example.angula.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.angula.database.model.AngulaTask;


@Repository
public interface TaskRepo extends JpaRepository<AngulaTask, Long> {
    
}