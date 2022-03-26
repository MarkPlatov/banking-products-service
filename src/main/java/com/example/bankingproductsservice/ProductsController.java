package com.example.bankingproductsservice;

import com.example.bankingproductsservice.models.Product;
import com.example.bankingproductsservice.models.Rule;
import com.example.bankingproductsservice.repos.ProductRepo;
import com.example.bankingproductsservice.repos.RuleRepo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ProductsController {
    private static final String ROUTE = "tasks";

    private final ProductRepo productRepo;
    private final RuleRepo ruleRepo;

    public ProductsController(ProductRepo productRepo, RuleRepo ruleRepo) {
        this.productRepo = productRepo;
        this.ruleRepo = ruleRepo;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String allProducts() {

        Iterable<Product> products = productRepo.findAllByIsActiveTrue();
        StringBuilder s = new StringBuilder("\"products\": [ \n");
        for (Product pr : products) {
            s.append(pr);
            s.append(",\n");
        }
        s.append("]");
        return s.toString();
    }

    @RequestMapping(value = "/products/{id}/rules", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getRules(@PathVariable("id") int id) {

        Product product = productRepo.findById(id);
        if (product == null) {
            return "";
        }

        StringBuilder s = new StringBuilder("\"rules\": [ \n");
        for (Rule rule : product.getRules()) {
            s.append(rule);
            s.append(",\n");
        }
        s.append("]");
        return s.toString();
    }

}