package com.example.angula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.angula.database.model.AngulaClient;
import com.example.angula.database.repository.AngulaUserRepo;
import com.example.angula.database.repository.ClientRepo;

@RestController
@RequestMapping("/client")
public class ClientRestController {
    @Autowired
    ClientRepo clientRepo;

    @Autowired
    AngulaUserRepo userRepo;

@PostMapping
public ResponseEntity<String> createClient(@RequestBody AngulaClient client) {
    client.setUser(userRepo.findById(client.getUser().getId()).get());
    clientRepo.save(client);
    return ResponseEntity.ok("Client created");
}

@GetMapping("/{id}")
public ResponseEntity<AngulaClient> getClientById(@PathVariable("id") Long id) {
    return clientRepo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

@GetMapping
public ResponseEntity<List<AngulaClient>> getAllClients() {
    return ResponseEntity.ok(clientRepo.findAll());
}

@PutMapping("/{id}")
public ResponseEntity<String> updateClient(@PathVariable("id") Long id, @RequestBody AngulaClient client) {
    return clientRepo.findById(id).map(existingClient -> {
        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        existingClient.setMobile(client.getMobile());
        existingClient.setUser(client.getUser());
        clientRepo.save(existingClient);
        return ResponseEntity.ok("Client updated");
    }).orElse(ResponseEntity.notFound().build());
}

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
