package com.example.bankingproductsservice.repos;

import com.example.bankingproductsservice.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {
    List<Product> findAllByIsActiveTrue();
    List<Product> findAllByMaxLoanAmountIsGreaterThanEqualAndIsActiveTrue(Integer claim);
    List<Product> findAllByMaxLoanAmountIsNullAndIsActiveTrue();
    Product findById(int id);
}
