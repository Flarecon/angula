package com.example.angula.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.angula.database.model.AngulaYt;

@Repository
public interface YtRepo extends JpaRepository<AngulaYt, Long> {
    Optional<AngulaYt> findByVideoId(String videoId);
}
