package com.example.reactor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import com.example.angula.database.repository.AngulaUserRepo;
import com.example.angula.database.repository.ClientRepo;
import com.example.reactor.components.Sweet;
import com.example.reactor.enricher.React;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/react")
public class ReactRestController {

    private final ClientRepo clientRepo;
    private final AngulaUserRepo userRepo;
    
    @GetMapping
    public React DemoReactRes() {
        return React.response("welcome to react");
    }

    @GetMapping("/client")
    public React getClients() {
        return React.response(clientRepo.findAll());
    }

    @GetMapping("/user")
    public React getUsers() {
        return React.response(userRepo.findAll());
    }
        
    @GetMapping("/sweet")
    public React getMethodName(Sweet sweet) {
        return React.response(sweet);
    }

}
