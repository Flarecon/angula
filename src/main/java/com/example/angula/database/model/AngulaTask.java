package com.example.angula.database.model;

import java.io.Serializable;


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
@Table(name = "task")
public class AngulaTask implements Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    String task;
    String cron;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AngulaTask)) {
            return false;
        }
        return id != null && id.equals(((AngulaTask) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
