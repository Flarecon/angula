package com.example.angula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.angula.database.model.AngulaClient;


@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    CacheManager cacheManager;

    @Transactional
    @GetMapping("/{name}")
    public Cache getCache(@PathVariable("name") String name) {
        Cache cache = cacheManager.getCache(name);
        return cache;
    }

    @Transactional
    @GetMapping("/{name}/{id}")
    public AngulaClient getCacheById(@PathVariable("name") String name, @PathVariable("id") Long id) {
        AngulaClient client = (AngulaClient) cacheManager.getCache(name).get(id).get();
        return client;
    }

    @Transactional
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
