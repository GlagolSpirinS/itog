package com.example.demo.repository;

import com.example.demo.model.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillsRepository extends JpaRepository<Skills, Long> {
}