package com.javaboots.javaboots.service;

import com.javaboots.javaboots.dto.IncomeDto;
import com.javaboots.javaboots.exceptions.ServiceException;
import com.javaboots.javaboots.model.Income;

import java.util.List;
import java.util.Optional;

public interface IncomeService {
    Income createIncome(IncomeDto income);

    List<Income> getAllIncome();

    Optional<Income> getIncomeById(String id) throws ServiceException;
}
