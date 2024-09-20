package com.example.demo.controller;

import com.example.demo.model.Addresses;
import com.example.demo.repository.AddressesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/addresses")
public class AddressesController {

    @Autowired
    private AddressesRepository addressesRepository;

    // GET: Получить все адреса
    @GetMapping
    public List<Addresses> getAllAddresses() {
        return addressesRepository.findAll();
    }

    // GET: Получить адрес по ID
    @GetMapping("/{id}")
    public ResponseEntity<Addresses> getAddressById(@PathVariable Long id) {
        Optional<Addresses> address = addressesRepository.findById(id);
        return address.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Создать новый адрес
    @PostMapping
    public ResponseEntity<Addresses> createAddress(@RequestBody Addresses address) {
        Addresses createdAddress = addressesRepository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    // PUT: Обновить адрес по ID
    @PutMapping("/{id}")
    public ResponseEntity<Addresses> updateAddress(@PathVariable Long id, @RequestBody Addresses address) {
        Optional<Addresses> existingAddress = addressesRepository.findById(id);
        if (existingAddress.isPresent()) {
            address.setId(id);
            Addresses updatedAddress = addressesRepository.save(address);
            return ResponseEntity.ok(updatedAddress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Удалить адрес по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        Optional<Addresses> existingAddress = addressesRepository.findById(id);
        if (existingAddress.isPresent()) {
            addressesRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}