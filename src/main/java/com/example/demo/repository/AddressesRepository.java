package com.example.demo.repository;

import com.example.demo.model.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressesRepository extends JpaRepository<Addresses, Long> {
}