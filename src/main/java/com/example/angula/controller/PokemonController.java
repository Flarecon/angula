package com.example.angula.controller;

import com.example.angula.database.model.Pokemon;
import com.example.angula.enums.PokemonType;
import com.example.angula.services.PokemonService;
import com.example.reactor.enricher.React;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokemon")
@ConditionalOnProperty(name = "angula.dynamic.controller", havingValue = "pokemon", matchIfMissing = true)
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping
    public React<List<Pokemon>> findAll() {
        return React.response(pokemonService.findAll());
    }

    @GetMapping("/type")
    public React<Slice<Pokemon>> findByType(PokemonType type, Pageable pageable) {
        return React.response(pokemonService.findByType(type, pageable));
    }

    @PostMapping
    public React<Pokemon> save(@RequestBody @Valid Pokemon pokemon) {
        return React.response(pokemonService.save(pokemon));
    }

    @PostMapping("/all")
    public React<List<Pokemon>> saveAll(@RequestBody @Valid List<Pokemon> pokemons) {
        return React.response(pokemonService.saveAll(pokemons));
    }

    @GetMapping("retry")
    public React<String> retryMethod() {
        return React.response(pokemonService.retryableMethod());
    }

}