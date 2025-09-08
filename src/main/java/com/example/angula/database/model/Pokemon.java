package com.example.angula.database.model;

import com.example.angula.enums.PokemonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "pokemon")
public class Pokemon {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(length = 50, nullable = false, unique = true)
    @NotNull
    String name;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    PokemonType type;

    @Column(nullable = false)
    int attack;

    @Column(nullable = false)
    int defense;

}