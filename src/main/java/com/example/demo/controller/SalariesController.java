package com.example.demo.controller;

import com.example.demo.model.Salaries;
import com.example.demo.repository.SalariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salaries")
public class SalariesController {

    @Autowired
    private SalariesRepository salariesRepository;

    // GET: Получить все зарплаты
    @GetMapping
    public List<Salaries> getAllSalaries() {
        return salariesRepository.findAll();
    }

    // GET: Получить зарплату по ID
    @GetMapping("/{id}")
    public ResponseEntity<Salaries> getSalaryById(@PathVariable Long id) {
        Optional<Salaries> salary = salariesRepository.findById(id);
        return salary.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Создать новую зарплату
    @PostMapping
    public ResponseEntity<Salaries> createSalary(@RequestBody Salaries salary) {
        Salaries createdSalary = salariesRepository.save(salary);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSalary);
    }

    // PUT: Обновить зарплату по ID
    @PutMapping("/{id}")
    public ResponseEntity<Salaries> updateSalary(@PathVariable Long id, @RequestBody Salaries salary) {
        Optional<Salaries> existingSalary = salariesRepository.findById(id);
        if (existingSalary.isPresent()) {
            salary.setId(id);
            Salaries updatedSalary = salariesRepository.save(salary);
            return ResponseEntity.ok(updatedSalary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Удалить зарплату по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalary(@PathVariable Long id) {
        Optional<Salaries> existingSalary = salariesRepository.findById(id);
        if (existingSalary.isPresent()) {
            salariesRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}