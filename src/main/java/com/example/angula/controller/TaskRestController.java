package com.example.angula.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.angula.database.model.AngulaTask;
import com.example.angula.database.repository.TaskRepo;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskRepo taskRepo;
    private final CacheManager cacheManager;
    
    @GetMapping
    public Iterable<AngulaTask> getTasks() {
        return taskRepo.findAll();
    }
    
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody AngulaTask task) {
        AngulaTask savedTask = taskRepo.save(task);
        cacheManager.getCache("task").putIfAbsent(savedTask.getId(), savedTask);
        return ResponseEntity.ok(savedTask);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable("id") Long id) {
        try {
            var item = cacheManager.getCache("task").get(id);
            if(item != null)
                return ResponseEntity.ok(item.get());
        } catch (Exception e) {}
        var task = taskRepo.findById(id).orElseThrow();
        cacheManager.getCache("task").put(id, task);
        return ResponseEntity.ok(task);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") Long id, @RequestBody AngulaTask task) {
        AngulaTask existingTask = taskRepo.findById(id).orElseThrow();
        existingTask.setTask(task.getTask());
        existingTask.setCron(task.getCron());
        taskRepo.save(existingTask);
        cacheManager.getCache("task").put(id, existingTask);
        return ResponseEntity.ok("task updated");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {
        taskRepo.deleteById(id);
        cacheManager.getCache("task").evictIfPresent(id);
        return ResponseEntity.ok("task deleted");
    }

}
