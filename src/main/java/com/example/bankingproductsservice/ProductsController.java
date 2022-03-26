package com.example.bankingproductsservice;

import com.example.bankingproductsservice.helpers.StringHelper;
import com.example.bankingproductsservice.models.Product;
import com.example.bankingproductsservice.models.Rule;
import com.example.bankingproductsservice.repos.ProductRepo;
import com.example.bankingproductsservice.repos.RuleRepo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductsController {
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
        if (product == null || !product.getActive()) {
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


    /*
    curl -i -X POST http://localhost:8080/products/100/\?min_salary\=666
     */
    @RequestMapping(value = "/products/{productId}/rules", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String addRuleToProduct(
            @PathVariable Integer productId,
            @RequestParam(name = "min_salary", required = false) Integer minSalary,
            @RequestParam(name = "allow_debtors", required = false) Boolean allowDebtors
            ) {
        if (minSalary == null && allowDebtors == null) {
            return "Type minSalary or allowDebtors";
        }
        Product product = productRepo.findById(productId);
        if (product == null) {
            return "Product with id = " + productId + " not found";
        }
        Rule rule = new Rule(product, minSalary, allowDebtors);
        ruleRepo.save(rule);
        return "OK";
    }

    /*
    curl -i -X DELETE http://localhost:8080/products/100/rules/102
    */
    @RequestMapping(value = "/products/{productId}/rules/{ruleID}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String deleteRule(
            @PathVariable Integer productId,
            @PathVariable Integer ruleID
    ) {
        Rule rule = ruleRepo.findById(ruleID);
        if (rule == null) {
            return "Product with id = " + productId + " not found";
        }
        // Если правило не от указанного продукта
        if (!rule.getProduct().getId().equals(productId)){
            return "Incorrect product id or rule id";
        }

        rule.delete();
        ruleRepo.save(rule);
        return "OK";
    }


}