package com.example.bankingproductsservice.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    private Integer maxLoanAmount;
    private Integer interestRate;
    private Integer creditTermYears;

    private Boolean isActive;

    public Product() {
        isActive = true;
    }

    public Product(Integer maxLoanAmount, Integer interestRate, Integer creditTermYears) {
        this.maxLoanAmount = maxLoanAmount;
        this.interestRate = interestRate;
        this.creditTermYears = creditTermYears;
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

    public void delete() {
        setActive(false);
    }
}
