package com.example.angula.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.angula.database.model.AngulaYt;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface YtRepo extends JpaRepository<AngulaYt, Long> {
    Optional<AngulaYt> findByVideoId(String videoId);
    void deleteByVideoId(String videoId);

    @Modifying
    @Query("UPDATE AngulaYt v SET v.videoTitle = :title WHERE v.videoId = :videoId")
    void updateTitleByVideoId(@Param("videoId") String videoId, @Param("title") String title);
}
