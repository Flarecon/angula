package com.example.angula.database.model;

import com.example.angula.auditor.VelocityAuditor;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class Post extends VelocityAuditor{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String title;
    private String content;
}
