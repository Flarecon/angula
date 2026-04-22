package com.example.angula.services;

import com.example.angula.database.model.Pokemon;
import com.example.angula.database.repository.PokemonRepo;
import com.example.angula.enums.PokemonType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
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

    @Retryable(
            maxAttempts = 5, // retry attempts default is 3
            retryFor = RuntimeException.class, // retry for specific exception by default does for all
            backoff = @Backoff(delay = 2000, multiplier = 2.0) // delays between retries in ms,
            // multiplier multiplies the delay for next retry here: 2s, 4s, 8s, 16s
    )
    public String retryableMethod() {
        System.out.println("trying to find the pokemon...");
        throw new RuntimeException("pokemon not found");
    }

}
