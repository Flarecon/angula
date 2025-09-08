package com.example.angula.services;

import com.example.angula.database.model.Pokemon;
import com.example.angula.database.repository.PokemonRepo;
import com.example.angula.enums.PokemonType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepo pokemonRepo;

    @Transactional(readOnly = true)
    public List<Pokemon> findAll(){
        return pokemonRepo.findAll();
    }

    public Pokemon save(Pokemon pokemon){
        return pokemonRepo.save(pokemon);
    }

    public List<Pokemon> saveAll(List<Pokemon> pokemons){
        return pokemonRepo.saveAll(pokemons);
    }

    public Slice<Pokemon> findByType(PokemonType type, Pageable pageable){
        return pokemonRepo.findByType(type, pageable);
    }

}
