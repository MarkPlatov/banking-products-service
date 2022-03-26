package com.example.bankingproductsservice;

import com.example.bankingproductsservice.helpers.StringHelper;
import com.example.bankingproductsservice.models.Product;
import com.example.bankingproductsservice.repos.ProductRepo;
import com.example.bankingproductsservice.repos.RuleRepo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return StringHelper.formatObjToJSON("products", products);
    }


    @RequestMapping(value = "/products/{id}/rules", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getRules(@PathVariable("id") int id) {
        Product product = productRepo.findById(id);
        if (product == null) {
            return "";
        }
        return StringHelper.formatObjToJSON("rules", product.getRules());
    }


    /*
    curl -i -X POST http://localhost:8080/products/apply\?salary\=30000\&claim\=20000\&is_debtor\=true
    curl -i -X POST http://localhost:8080/products/apply\?salary\=60000\&claim\=300000\&is_debtor\=false
     */
    @RequestMapping(value = "/products/apply", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getProductsForClient(
            @RequestParam(name = "salary") Integer salary,
            @RequestParam(name = "claim") Integer claim,
            @RequestParam(name = "is_debtor") Boolean isDebtor
    ) {
        // Все продукты, предоставляющие кредит на сумму большую или эквивалентную указанной (claim)
        List<Product> products = productRepo.findAllByMaxLoanAmountIsGreaterThanEqualAndIsActiveTrue(claim);
        // Все продукты, предоставляющие кредит на любую сумму
        products.addAll(productRepo.findAllByMaxLoanAmountIsNullAndIsActiveTrue());

        if (products.size() == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder("");
        for (Product product : products){
            if (product.isMatches(salary, isDebtor)){
                result.append(product);
            }
        }

        return StringHelper.formatObjToJSON("products", result);
    }

}