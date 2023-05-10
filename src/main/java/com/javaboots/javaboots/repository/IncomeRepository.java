package com.javaboots.javaboots.repository;

import com.javaboots.javaboots.model.Income;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends MongoRepository<Income, String> {
}
