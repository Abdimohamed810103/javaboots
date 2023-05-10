package com.javaboots.javaboots.service;

import com.javaboots.javaboots.dto.IncomeDto;
import com.javaboots.javaboots.exceptions.ServiceException;
import com.javaboots.javaboots.model.Income;
import com.javaboots.javaboots.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService{

    @Autowired
    private IncomeRepository incomeRepository;
    @Override
    public Income createIncome(IncomeDto incomeDto) {
        Income income = Income.builder("0","income", incomeDto.getAmount(), incomeDto.getDate(), incomeDto.getCategory(),incomeDto.getDescription());
        return incomeRepository.save(income);
    }

    @Override
    public List<Income> getAllIncome() {
        List<Income> incomeList = incomeRepository.findAll();
        return incomeList;
    }

    @Override
    public Optional<Income> getIncomeById(String id) throws ServiceException {
        Optional<Income> income = incomeRepository.findById(id);
        if(income.isPresent()){
            return income;
        }else {
            throw new ServiceException("There are no record for the Id");
        }

    }
}
