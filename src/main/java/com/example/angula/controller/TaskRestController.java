package com.example.angula.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
public class TaskRestController {
    
    @Autowired
    private TaskRepo taskRepo;
    
    @GetMapping
    public Iterable<AngulaTask> getTasks() {
        return taskRepo.findAll();
    }
    
    @PostMapping
    public AngulaTask createTask(@RequestBody AngulaTask task) {
        return taskRepo.save(task);
    }
    
    @GetMapping("/{id}")
    public AngulaTask getTask(@PathVariable("id") Long id) {
        return taskRepo.findById(id).orElseThrow();
    }
    
    @PutMapping("/{id}")
    public AngulaTask updateTask(@PathVariable("id") Long id, @RequestBody AngulaTask task) {
        AngulaTask existingTask = taskRepo.findById(id).orElseThrow();
        existingTask.setTask(task.getTask());
        existingTask.setCron(task.getCron());
        return taskRepo.save(existingTask);
    }
    
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        taskRepo.deleteById(id);
    }

}
