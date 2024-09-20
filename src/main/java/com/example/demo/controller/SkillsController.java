package com.example.demo.controller;

import com.example.demo.model.Skills;
import com.example.demo.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/skills")
public class SkillsController {

    @Autowired
    private SkillsRepository skillsRepository;

    // GET: Получить все навыки
    @GetMapping
    public List<Skills> getAllSkills() {
        return skillsRepository.findAll();
    }

    // GET: Получить навык по ID
    @GetMapping("/{id}")
    public ResponseEntity<Skills> getSkillById(@PathVariable Long id) {
        Optional<Skills> skill = skillsRepository.findById(id);
        return skill.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Создать новый навык
    @PostMapping
    public ResponseEntity<Skills> createSkill(@RequestBody Skills skill) {
        Skills createdSkill = skillsRepository.save(skill);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSkill);
    }

    // PUT: Обновить навык по ID
    @PutMapping("/{id}")
    public ResponseEntity<Skills> updateSkill(@PathVariable Long id, @RequestBody Skills skill) {
        Optional<Skills> existingSkill = skillsRepository.findById(id);
        if (existingSkill.isPresent()) {
            skill.setId(id);
            Skills updatedSkill = skillsRepository.save(skill);
            return ResponseEntity.ok(updatedSkill);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Удалить навык по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        Optional<Skills> existingSkill = skillsRepository.findById(id);
        if (existingSkill.isPresent()) {
            skillsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}