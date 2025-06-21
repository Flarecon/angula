package com.example.angula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    CacheManager cacheManager;

    @GetMapping("/{name}")
    public Cache getCache(@PathVariable("name") String name) {
        Cache cache = cacheManager.getCache(name);
        return cache;
    }

    @DeleteMapping("/{name}")
    public String clearCache(@PathVariable("name") String name) {
        Cache cache = cacheManager.getCache(name);
        if(cache != null){
            cache.clear();
            return "Cache cleared";
        }
        return "Cache not found";
    }
}
