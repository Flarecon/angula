package com.example.angula.database.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.example.angula.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "todos")
public class AngulaTodo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80)
    private String title;
    
    private String body;
    
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private LocalDateTime date = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AngulaTodo)) {
            return false;
        }
        return id != null && id.equals(((AngulaTodo) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}