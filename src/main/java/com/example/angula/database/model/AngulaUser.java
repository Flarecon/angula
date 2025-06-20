package com.example.angula.database.model;
import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class AngulaUser {
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Expose
    @Column(length = 20, unique = true)
    String username;
    
    @Column(length = 50)
    String password;

    @Expose
    @Column(length = 10)
    String Role = "USER";
}