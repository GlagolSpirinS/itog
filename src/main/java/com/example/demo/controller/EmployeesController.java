package com.example.demo.controller;

import com.example.demo.model.Employees;
import com.example.demo.model.Positions;
import com.example.demo.repository.DepartmentsRepository;
import com.example.demo.repository.EmployeesRepository;
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
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private PositionsRepository positionsRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    // GET: Отобразить список всех сотрудников
    @GetMapping
    public String showEmployees(Model model) {
        List<Employees> employees = employeesRepository.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    // GET: Отобразить форму для создания нового сотрудника
    @GetMapping("/new")
    public String showNewEmployeeForm(Model model) {
        model.addAttribute("employee", new Employees());
        model.addAttribute("positions", positionsRepository.findAll());
        model.addAttribute("departments", departmentsRepository.findAll());
        return "new_employee";
    }

    // POST: Создать нового сотрудника
    @PostMapping
    public String createEmployee(@ModelAttribute Employees employee) {
        // Найдите объект Positions по ID из формы
        Positions selectedPosition = positionsRepository.findById(employee.getPosition().getId()).orElse(null);

        // Установите найденный объект Positions в employee
        employee.setPosition(selectedPosition);

        employeesRepository.save(employee);
        return "redirect:/employees";
    }

    // GET: Отобразить форму для редактирования сотрудника
    @GetMapping("/{id}/edit")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        Optional<Employees> employee = employeesRepository.findById(id);
        employee.ifPresent(value -> model.addAttribute("employee", value));
        model.addAttribute("positions", positionsRepository.findAll());
        model.addAttribute("departments", departmentsRepository.findAll());
        return "edit_employee";
    }

//    // PUT: Обновить информацию о сотруднике
//    @PostMapping("/{id}")
//    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employees employee) {
//        // Найдите объект Positions по ID из формы
//        Positions selectedPosition = positionsRepository.findById(employee.getPosition().getId()).orElse(null);
//
//        // Установите найденный объект Positions в employee
//        employee.setPosition(selectedPosition);
//
//        employee.setId(id);
//        employeesRepository.save(employee);
//        return "redirect:/employees";
//    }

    // DELETE: Удалить сотрудника
    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeesRepository.deleteById(id);
        return "redirect:/employees";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeApi(@PathVariable Long id) {
        employeesRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/{id}/delete")
//    public String deleteEmployee(@PathVariable Long id) {
//        employeesRepository.deleteById(id);
//        return "redirect:/employees";
//    }

    // GET: Получить сотрудника по ID (для REST API)
    @GetMapping("/api/{id}")
    public ResponseEntity<Employees> getEmployeeByIdApi(@PathVariable Long id) {
        Optional<Employees> employee = employeesRepository.findById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }
}