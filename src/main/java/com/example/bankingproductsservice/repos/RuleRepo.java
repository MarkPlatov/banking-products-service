package com.example.bankingproductsservice.repos;

import com.example.bankingproductsservice.models.Product;
import com.example.bankingproductsservice.models.Rule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RuleRepo extends CrudRepository<Rule, Long> {
    Rule findById(Integer id);
}
