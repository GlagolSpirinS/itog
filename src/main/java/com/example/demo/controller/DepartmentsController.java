package com.example.demo.controller;

import com.example.demo.model.Departments;
import com.example.demo.repository.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {

    @Autowired
    private DepartmentsRepository departmentsRepository;

    // GET: Получить все отделы
    @GetMapping
    public List<Departments> getAllDepartments() {
        return departmentsRepository.findAll();
    }

    // GET: Получить отдел по ID
    @GetMapping("/{id}")
    public ResponseEntity<Departments> getDepartmentById(@PathVariable Long id) {
        Optional<Departments> department = departmentsRepository.findById(id);
        return department.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Создать новый отдел
    @PostMapping
    public ResponseEntity<Departments> createDepartment(@RequestBody Departments department) {
        Departments createdDepartment = departmentsRepository.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    // PUT: Обновить отдел по ID
    @PutMapping("/{id}")
    public ResponseEntity<Departments> updateDepartment(@PathVariable Long id, @RequestBody Departments department) {
        Optional<Departments> existingDepartment = departmentsRepository.findById(id);
        if (existingDepartment.isPresent()) {
            department.setId(id);
            Departments updatedDepartment = departmentsRepository.save(department);
            return ResponseEntity.ok(updatedDepartment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Удалить отдел по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        Optional<Departments> existingDepartment = departmentsRepository.findById(id);
        if (existingDepartment.isPresent()) {
            departmentsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}