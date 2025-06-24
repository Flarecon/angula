package com.example.reactor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactor.components.Sweet;
import com.example.reactor.error.CustomException;
import com.example.reactor.service.SweetService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@RestController
@RequestMapping("reactor")
public class AsteroidRestController {

    @Autowired(required = false)
    SweetService sweetService;

    @Autowired
    @Qualifier("cake")
    Sweet sweet1;

    @Autowired
    Sweet sweet3;

    @Autowired
    @Qualifier("sweet3")
    Sweet sweet2;

    @PostConstruct
    void init() {
        System.out.println("------------------------\nasteoid controller is rising " + this.hashCode()
                + "\n------------------------");
    }

    @PreDestroy
    void outit() {
        System.out.println("------------------------\nasteoid controller is falling" + this.hashCode()
                + "\n------------------------");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/sweet/{name}")
    public ResponseEntity<Sweet> getSweets(@PathVariable("name") String name) throws CustomException {
        switch (name) {
            case "s1":
                return ResponseEntity.ok(sweet1);
            case "s2":
                return ResponseEntity.ok(sweet2);
            case "s3":
                return ResponseEntity.ok(sweet3);
            case "s4":
                return ResponseEntity.ok(sweetService.getSweet(sweet3));
            default:
                throw new CustomException(name + " not accepted", 701);
        }
    }
}
