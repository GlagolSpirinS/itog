package com.example.demo.repository;

import com.example.demo.model.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractsRepository extends JpaRepository<Contracts, Long> {
}