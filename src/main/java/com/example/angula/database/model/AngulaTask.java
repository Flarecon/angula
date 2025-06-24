package com.example.angula.database.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "task")
public class AngulaTask implements Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    String task;
    String cron;
}
