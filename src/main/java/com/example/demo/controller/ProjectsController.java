package com.example.demo.controller;

import com.example.demo.model.Projects;
import com.example.demo.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectsRepository projectsRepository;

    // GET: Получить все проекты
    @GetMapping
    public List<Projects> getAllProjects() {
        return projectsRepository.findAll();
    }

    // GET: Получить проект по ID
    @GetMapping("/{id}")
    public ResponseEntity<Projects> getProjectById(@PathVariable Long id) {
        Optional<Projects> project = projectsRepository.findById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Создать новый проект
    @PostMapping
    public ResponseEntity<Projects> createProject(@RequestBody Projects project) {
        Projects createdProject = projectsRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    // PUT: Обновить проект по ID
    @PutMapping("/{id}")
    public ResponseEntity<Projects> updateProject(@PathVariable Long id, @RequestBody Projects project) {
        Optional<Projects> existingProject = projectsRepository.findById(id);
        if (existingProject.isPresent()) {
            project.setId(id);
            Projects updatedProject = projectsRepository.save(project);
            return ResponseEntity.ok(updatedProject);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Удалить проект по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Optional<Projects> existingProject = projectsRepository.findById(id);
        if (existingProject.isPresent()) {
            projectsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}