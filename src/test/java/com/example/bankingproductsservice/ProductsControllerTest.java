package com.example.bankingproductsservice;

import com.example.bankingproductsservice.models.Product;
import com.example.bankingproductsservice.models.Rule;
import com.example.bankingproductsservice.repos.ProductRepo;
import com.example.bankingproductsservice.repos.RuleRepo;
import com.example.bankingproductsservice.seeds.DBSeed;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductsControllerTest {

    @Autowired
    private ProductsController productsController;

    @MockBean
    private ProductRepo productRepo;

    @MockBean
    private RuleRepo ruleRepo;

    @MockBean
    private DBSeed dbSeed;


    @Test
    public void allProductsFindSome() {
        Product product = new Product(10, 10, 10);
        Mockito.doReturn(new ArrayList(Collections.singleton(product)))
                .when(productRepo)
                .findAllByIsActiveTrue();

        String responseBody = productsController.allProducts();
        Assert.assertThat(responseBody, Matchers.containsString(
                "{\n" +
                "\"products\": \n" +
                "[\n" +
                "{" ));
        assertNotNull(responseBody);

        Mockito.verify(productRepo, Mockito.times(1)).findAllByIsActiveTrue();
    }

    @Test
    public void allProductsFindNothing() {
        Mockito.doReturn(new ArrayList(Collections.singleton(null)))
                .when(productRepo)
                .findAllByIsActiveTrue();

        String responseBody = productsController.allProducts();

        Assert.assertEquals("{\n\"products\": \n[null]\n}", responseBody);
        Mockito.verify(productRepo, Mockito.times(1)).findAllByIsActiveTrue();
    }

    @Test
    public void getRulesFindSome() {
        Product product = new Product(10, 10, 10);
        Rule rule = new Rule(product, 10);
        product.setRules(Collections.singleton(rule));
        product.setId(0);
        Mockito.doReturn(product)
                .when(productRepo)
                .findById(product.getId());

        String responseBody = productsController.getRules(product.getId());
        Assert.assertThat(responseBody, Matchers.containsString(rule.toString()));
        Mockito.verify(productRepo, Mockito.times(1)).findById(product.getId());
    }

    @Test
    public void getRulesFindProductNullableRules() {
        Product product = new Product(10, 10, 10);
        product.setRules(null);
        product.setId(0);
        Mockito.doReturn(product)
                .when(productRepo)
                .findById(product.getId());

        String responseBody = productsController.getRules(product.getId());
        Assert.assertEquals("{\n\"rules\": \n[null]\n}", responseBody);
        Mockito.verify(productRepo, Mockito.times(1)).findById(product.getId());
    }

    @Test
    public void getRulesFindNull() {
        Mockito.doReturn(null)
                .when(productRepo)
                .findById(0);

        String responseBody = productsController.getRules(0);
        Assert.assertEquals("", responseBody);
        Mockito.verify(productRepo, Mockito.times(1)).findById(0);
    }

    @Test
    public void getRulesFindInactiveProduct() {
        Product product = new Product(10, 10, 10);
        product.setId(0);
        product.delete();
        Mockito.doReturn(product)
                .when(productRepo)
                .findById(product.getId());

        String responseBody = productsController.getRules(product.getId());
        Assert.assertEquals("", responseBody);
        Mockito.verify(productRepo, Mockito.times(1)).findById(product.getId());
    }

    // Пример из ТЗ с заёмщиком №1
    @Test
    public void getProductsForClientExample1() {
        Integer salary = 30000;
        Integer claim = 20000;
        Boolean isDebtor = true;

        Product product1 = new Product(200000, 6, 3);
        Product product2 = new Product(null, 15, null);
        Product product3 = new Product(1000000, 12, 5);
        Rule rule1 = new Rule(product1, 50000);
        Rule rule2 = new Rule(product1, false);
        Rule rule3 = new Rule(product2, false);
        Rule rule4 = new Rule(product3, 25000);

        product1.addRule(rule1);
        product1.addRule(rule2);
        product2.addRule(rule3);
        product3.addRule(rule4);

        List<Product> list1 = new ArrayList<>();
        List<Product> list2 = new ArrayList<>();
        list1.add(product1);
        list1.add(product3);
        list2.add(product2);

        Mockito.doReturn(list1)
                .when(productRepo)
                .findAllByMaxLoanAmountIsGreaterThanEqualAndIsActiveTrue(claim);
        Mockito.doReturn(list2)
                .when(productRepo)
                .findAllByMaxLoanAmountIsNullAndIsActiveTrue();

        String responseBody = productsController.getProductsForClient(salary, claim, isDebtor);

        Assert.assertThat(responseBody, Matchers.not(Matchers.containsString(product1.toString())));
        Assert.assertThat(responseBody, Matchers.not(Matchers.containsString(product2.toString())));
        Assert.assertThat(responseBody, Matchers.containsString(product3.toString()));
    }

    // Пример из ТЗ с заёмщиком №2
    @Test
    public void getProductsForClientExample2() {
        Integer salary = 60000;
        Integer claim = 300000;
        Boolean isDebtor = false;

        Product product1 = new Product(200000, 6, 3);
        Product product2 = new Product(null, 15, null);
        Product product3 = new Product(1000000, 12, 5);
        Rule rule1 = new Rule(product1, 50000);
        Rule rule2 = new Rule(product1, false);
        Rule rule3 = new Rule(product2, false);
        Rule rule4 = new Rule(product3, 25000);

        product1.addRule(rule1);
        product1.addRule(rule2);
        product2.addRule(rule3);
        product3.addRule(rule4);

        List<Product> list1 = new ArrayList<>();
        List<Product> list2 = new ArrayList<>();
//        list1.add(product1);
        list1.add(product3);
        list2.add(product2);

        Mockito.doReturn(list1)
                .when(productRepo)
                .findAllByMaxLoanAmountIsGreaterThanEqualAndIsActiveTrue(claim);
        Mockito.doReturn(list2)
                .when(productRepo)
                .findAllByMaxLoanAmountIsNullAndIsActiveTrue();

        String responseBody = productsController.getProductsForClient(salary, claim, isDebtor);

        Assert.assertThat(responseBody, Matchers.not(Matchers.containsString(product1.toString())));
        Assert.assertThat(responseBody, Matchers.containsString(product2.toString()));
        Assert.assertThat(responseBody, Matchers.containsString(product3.toString()));
    }

    @Test
    public void addRuleToProductSuccess() {
        Integer productId = 0;
        Integer minSalary = 10;
        Boolean allowDebtors = false;

        Product product = new Product();
        product.setId(productId);
        Mockito.doReturn(product)
                .when(productRepo)
                .findById(product.getId());
        Mockito.doReturn(null)
                .when(ruleRepo)
                .save(ArgumentMatchers.any(Rule.class));

        String responseBody = productsController.addRuleToProduct(productId, minSalary, allowDebtors);

        Rule rule = new Rule(product, minSalary, allowDebtors);

        Mockito.verify(ruleRepo, Mockito.times(1))
                .save(ArgumentMatchers.isA(rule.getClass()));

        Assert.assertEquals("OK", responseBody);
    }

    @Test
    public void addRuleToProductNullQuery() {
        Integer productId = 0;
        Integer minSalary = null;
        Boolean allowDebtors = null;

        String responseBody = productsController.addRuleToProduct(productId, minSalary, allowDebtors);

        Mockito.verify(ruleRepo, Mockito.times(0))
                .save(ArgumentMatchers.any(Rule.class));

        Assert.assertEquals("Type minSalary or allowDebtors", responseBody);
    }

    @Test
    public void addRuleToProductProductNotFound() {
        Integer productId = 0;
        Integer minSalary = 10;
        Boolean allowDebtors = null;

        Mockito.doReturn(null)
                .when(productRepo)
                .findById(productId);

        String responseBody = productsController.addRuleToProduct(productId, minSalary, allowDebtors);

        Mockito.verify(ruleRepo, Mockito.times(0))
                .save(ArgumentMatchers.any(Rule.class));

        Assert.assertEquals("Product with id = " + productId + " not found", responseBody);
    }

    @Test
    public void deleteRule() {
    }
}