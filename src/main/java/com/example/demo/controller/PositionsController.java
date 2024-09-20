package com.example.demo.controller;

import com.example.demo.model.Employees;
import com.example.demo.model.Positions;
import com.example.demo.repository.PositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/positions")
public class PositionsController {

    @Autowired
    private PositionsRepository positionsRepository;

    @GetMapping
    public String showEmployees(Model model) {
        List<Positions> positions = positionsRepository.findAll();
        model.addAttribute("Positions", positions);
        return "positions";
    }

//    // GET: Получить все должности
//    @GetMapping
//    public List<Positions> getAllPositions() {
//        return positionsRepository.findAll();
//    }




    // GET: Получить должность по ID
    @GetMapping("/{id}")
    public ResponseEntity<Positions> getPositionById(@PathVariable Long id) {
        Optional<Positions> position = positionsRepository.findById(id);
        return position.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT: Обновить должность по ID
    @PutMapping("/{id}")
    public ResponseEntity<Positions> updatePosition(@PathVariable Long id, @RequestBody Positions position) {
        Optional<Positions> existingPosition = positionsRepository.findById(id);
        if (existingPosition.isPresent()) {
            position.setId(id);
            Positions updatedPosition = positionsRepository.save(position);
            return ResponseEntity.ok(updatedPosition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Удалить должность по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        Optional<Positions> existingPosition = positionsRepository.findById(id);
        if (existingPosition.isPresent()) {
            positionsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Отображение формы для добавления новой должности
    @GetMapping("/new")
    public String showNewPositionForm(Model model) {
        model.addAttribute("position", new Positions());
        return "new_position"; // Имя html-файла для формы
    }

    // Обработка POST-запроса для создания новой должности
    @PostMapping("/new")
    public String createPosition(@ModelAttribute Positions position) {
        positionsRepository.save(position);
        return "redirect:/positions";
    }
}