package com.example.angula.database.repository;

import com.example.angula.database.model.Post;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    Page<Post> findByTitleContaining(String title, Pageable pageable);

    Page<Post> findByContentContaining(String content, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE (:from IS NULL OR p.createdAt >= :from) AND (:to IS NULL OR p.createdAt <= :to)")
    Page<Post> findByCreatedAtBetween(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable
    );

}
