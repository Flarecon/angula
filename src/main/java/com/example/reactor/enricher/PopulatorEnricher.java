package com.example.reactor.enricher;

import com.example.angula.controller.PokemonController;
import com.example.angula.database.model.Pokemon;
import com.example.angula.enums.PokemonCategory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collection;

@Service
@ControllerAdvice(basePackageClasses = PokemonController.class)
public class PopulatorEnricher implements ResponseBodyAdvice<React> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Only intercept responses of type React<?>
        return React.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public React<?> beforeBodyWrite(React body, MethodParameter returnType, MediaType selectedContentType,
                                 Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                 ServerHttpResponse response) {

        if (body == null || body.response == null) return body;

        Object data = body.response;

        // 1️⃣ Single Pokemon
        if (data instanceof Pokemon p) {
            enrichPokemon(p);
        }

        // 2️⃣ List/Set of Pokemon
        else if (data instanceof Collection<?> col) {
            col.stream()
                    .filter(Pokemon.class::isInstance)
                    .map(Pokemon.class::cast)
                    .forEach(this::enrichPokemon);
        }

        // 3️⃣ Page or Slice of Pokemon
        else if (data instanceof Page<?> page) {
            page.getContent().stream()
                    .filter(Pokemon.class::isInstance)
                    .map(Pokemon.class::cast)
                    .forEach(this::enrichPokemon);
        }
        else if (data instanceof Slice<?> slice) {
            slice.getContent().stream()
                    .filter(Pokemon.class::isInstance)
                    .map(Pokemon.class::cast)
                    .forEach(this::enrichPokemon);
        }

        return body; // Return enriched React
    }

    private void enrichPokemon(Pokemon p) {
        int limit = 100;
        int border = 60;
        int diff = 10;

        PokemonCategory category = null;

        if ((p.getAttack() - p.getDefense()) > diff) {
            if (p.getAttack() > limit)
                category = PokemonCategory.STRIKER;
            else if (p.getAttack() > border)
                category = PokemonCategory.DAMAGER;
            else
                category = PokemonCategory.ATTACKER;

        } else if ((p.getDefense() - p.getAttack()) > diff) {
            if (p.getDefense() > limit)
                category = PokemonCategory.TANK;
            else if (p.getDefense() > border)
                category = PokemonCategory.PROTECTOR;
            else
                category = PokemonCategory.DEFENDER;

        } else {
            if (p.getAttack() > limit && p.getDefense() > limit)
                category = PokemonCategory.CHAMPION;
            else if (p.getAttack() > border && p.getDefense() > border)
                category = PokemonCategory.SPECIAL;
            else
                category = PokemonCategory.MULTITASKER;

        }
        p.setCategory(category);
    }
}

