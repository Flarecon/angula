package com.example.angula.database.repository;

import com.example.angula.database.model.Pokemon;
import com.example.angula.enums.PokemonType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepo extends JpaRepository<Pokemon, Long> {
    Slice<Pokemon> findByType(PokemonType type, Pageable pageable);
}
