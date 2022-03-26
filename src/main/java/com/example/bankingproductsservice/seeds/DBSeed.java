package com.example.bankingproductsservice.seeds;

import com.example.bankingproductsservice.models.Product;
import com.example.bankingproductsservice.models.Rule;
import com.example.bankingproductsservice.repos.ProductRepo;
import com.example.bankingproductsservice.repos.RuleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBSeed implements CommandLineRunner {

    private final ProductRepo productRepo;
    private final RuleRepo ruleRepo;

    public DBSeed(ProductRepo productRepo, RuleRepo ruleRepo) {
        this.productRepo = productRepo;
        this.ruleRepo = ruleRepo;
    }

    @Override
    public void run(String... args) {
        loadProducts();
    }

    private void loadProducts() {
        if (ruleRepo.count() != 0) {
            ruleRepo.deleteAll();
        }
        if (productRepo.count() != 0) {
            productRepo.deleteAll();
        }

        Product p1 = new Product(200000, 6, 3);
        Product p2 = new Product(null, 15, null);
        Product p3 = new Product(1000000, 12, 5);
        Rule r11 = new Rule(p1, 50000);
        Rule r21 = new Rule(p1, false);
        Rule r12 = new Rule(p2, false);
        Rule r13 = new Rule(p3, 25000);

        productRepo.save(p1);
        productRepo.save(p2);
        productRepo.save(p3);
        ruleRepo.save(r11);
        ruleRepo.save(r21);
        ruleRepo.save(r12);
        ruleRepo.save(r13);
    }
}