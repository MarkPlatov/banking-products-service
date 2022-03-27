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

    @Test
    public void getProductsForClient() {
        Assert.assertTrue(true);
    }

    @Test
    public void addRuleToProduct() {
        Assert.assertTrue(true);
    }

    @Test
    public void deleteRule() {
    }
}