package com.example.bankingproductsservice.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;



    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rule_id", referencedColumnName = "id")
    private Rule rule;

    private Integer maxLoanAmount;
    private Integer interestRate;
    private Integer creditTermYears;


    private Boolean isActive = true;

    public Product() {
    }

    public Product(Integer maxLoanAmount, Integer interestRate, Integer creditTermYears, Rule rule) {
        this.maxLoanAmount = maxLoanAmount;
        this.interestRate = interestRate;
        this.creditTermYears = creditTermYears;
        this.rule = rule;
        isActive = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public void setMaxLoanAmount(Integer maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    public Integer getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Integer interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getCreditTermYears() {
        return creditTermYears;
    }

    public void setCreditTermYears(Integer creditTermYears) {
        this.creditTermYears = creditTermYears;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return "{\n" +
                "   \"id\": " + id + ",\n" +
                "   \"createdDate\": " + createdDate + ",\n" +
                "   \"lastModifiedDate\": " + lastModifiedDate + ",\n" +
                rule + ",\n" +
                "   \"maxLoanAmount\": " + maxLoanAmount + ",\n" +
                "   \"interestRate\": " + interestRate + ",\n" +
                "   \"creditTermYears\": " + creditTermYears + ",\n" +
                "   \"isActive\": " + isActive + "\n" +
                "}";
    }

    public void delete() {
        setActive(false);
    }
}
