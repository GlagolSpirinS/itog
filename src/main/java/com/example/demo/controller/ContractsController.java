package com.example.demo.controller;

import com.example.demo.model.Contracts;
import com.example.demo.repository.ContractsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contracts")
public class ContractsController {

    @Autowired
    private ContractsRepository contractsRepository;

    // GET: Получить все контракты
    @GetMapping
    public List<Contracts> getAllContracts() {
        return contractsRepository.findAll();
    }

    // GET: Получить контракт по ID
    @GetMapping("/{id}")
    public ResponseEntity<Contracts> getContractById(@PathVariable Long id) {
        Optional<Contracts> contract = contractsRepository.findById(id);
        return contract.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Создать новый контракт
    @PostMapping
    public ResponseEntity<Contracts> createContract(@RequestBody Contracts contract) {
        Contracts createdContract = contractsRepository.save(contract);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContract);
    }

    // PUT: Обновить контракт по ID
    @PutMapping("/{id}")
    public ResponseEntity<Contracts> updateContract(@PathVariable Long id, @RequestBody Contracts contract) {
        Optional<Contracts> existingContract = contractsRepository.findById(id);
        if (existingContract.isPresent()) {
            contract.setId(id);
            Contracts updatedContract = contractsRepository.save(contract);
            return ResponseEntity.ok(updatedContract);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Удалить контракт по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        Optional<Contracts> existingContract = contractsRepository.findById(id);
        if (existingContract.isPresent()) {
            contractsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}