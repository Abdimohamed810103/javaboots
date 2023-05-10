package com.javaboots.javaboots.controller;

import com.javaboots.javaboots.dto.IncomeDto;
import com.javaboots.javaboots.exceptions.ServiceException;
import com.javaboots.javaboots.model.Income;
import com.javaboots.javaboots.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class IncomeController {


    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Income> createIncome(@Valid @RequestBody IncomeDto income){
        return new ResponseEntity<>(incomeService.createIncome(income), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Income> getAllIncome(){
        return incomeService.getAllIncome();
    }

    @GetMapping("/{id}")
    public Optional<Income> getIncomeById(@PathVariable String id) throws ServiceException {
        return incomeService.getIncomeById(id);
    }
}
