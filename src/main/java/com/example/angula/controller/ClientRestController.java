package com.example.angula.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.angula.database.model.AngulaClient;
import com.example.angula.database.repository.AngulaUserRepo;
import com.example.angula.database.repository.ClientRepo;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientRestController {
    private final ClientRepo clientRepo;
    private final AngulaUserRepo userRepo;

    @Transactional
    @PostMapping
    public ResponseEntity<String> createClient(@RequestBody AngulaClient client) {
        client.setUser(userRepo.findById(client.getUser().getId()).get());
        clientRepo.save(client);
        return ResponseEntity.ok("Client created");
    }

    @Transactional
    @Cacheable(value = "client") // cache for this method
    @GetMapping("/{id}")
    public AngulaClient getClientById(@PathVariable("id") Long id) {
        System.out.println("get client by id: " + id);
        return clientRepo.findById(id).get();
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<AngulaClient>> getAllClients() {
        return ResponseEntity.ok(clientRepo.findAll());
    }

    @Transactional
    @CachePut(value = "client") // update cache
    @PutMapping("/{id}")
    public AngulaClient updateClient(@PathVariable("id") Long id, @RequestBody AngulaClient client) {
        return clientRepo.findById(id).map(existingClient -> {
            existingClient.setName(client.getName());
            existingClient.setEmail(client.getEmail());
            existingClient.setMobile(client.getMobile());
            existingClient.setUser(client.getUser());
            existingClient.setTodos(client.getTodos());
            clientRepo.save(existingClient);
            return existingClient;
        }).orElse(null);
    }

    @Transactional
    @CacheEvict(value = "client") // evict cache
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") Long id) {
        if (clientRepo.existsById(id)) {
            clientRepo.deleteById(id);
            return ResponseEntity.ok("Client deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
