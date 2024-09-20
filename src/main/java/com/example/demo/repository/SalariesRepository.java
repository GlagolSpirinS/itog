package com.example.demo.repository;

import com.example.demo.model.Salaries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalariesRepository extends JpaRepository<Salaries, Long> {
}