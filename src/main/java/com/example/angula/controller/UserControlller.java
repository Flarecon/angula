package com.example.angula.controller;

import com.example.angula.database.model.AngulaUser;
import com.example.angula.database.repository.AngulaUserRepo;
import com.example.angula.services.AngulaService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/user")
@Slf4j
public class UserControlller {

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    AngulaUserRepo userRepo;

    @Autowired
    AngulaService angulaService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody AngulaUser user){
        TransactionStatus status = transactionManager.getTransaction(null);
        try{
            userRepo.save(user);
            transactionManager.commit(status);
            log.info("user created username {}", user.getUsername());
            return ResponseEntity.ok(user);
        }
        catch (Exception e){
            transactionManager.rollback(status);
            log.error("could not create user {}", e.getMessage());
            return ResponseEntity.badRequest().body("could not create user");
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getUserBYId(@PathVariable("id") Long id){
        var user = userRepo.findById(id);
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("gets/{id}")
    @ResponseBody
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserByIdFromService(@PathVariable("id") Long id){
        var user = angulaService.findUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<AngulaUser>> getAllUser(){
        log.info("getting all users");
        return ResponseEntity.ok().body(userRepo.findAll());
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody AngulaUser user){
        var existingUser = userRepo.findById(id);
        existingUser.get().setUsername(user.getUsername());
        existingUser.get().setPassword(user.getPassword());
        existingUser.get().setRole(user.getRole());
        userRepo.save(existingUser.get());
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userRepo.deleteById(id);
        log.info("user deleted with id {}", id);
        return ResponseEntity.ok("user deleted");
    }
}
