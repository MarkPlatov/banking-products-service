package com.example.bankingproductsservice.repos;

import com.example.bankingproductsservice.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {
    Product findById(Integer id);
}
