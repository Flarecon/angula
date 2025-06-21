package com.example.angula.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "task")
public class AngulaTask {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    String task;
    String cron;
}
