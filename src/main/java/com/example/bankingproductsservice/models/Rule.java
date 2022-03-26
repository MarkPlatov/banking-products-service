package com.example.bankingproductsservice.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;



@Entity
@Table(name = "rules")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    private Boolean isActive;

    @OneToOne(mappedBy = "rule")
    private Product product;

    private Integer minSalary;
    private Boolean isDebtor;




    public Rule() {
        isActive = true;
    }

    public Rule(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Rule(Boolean isDebtor) {
        this.isDebtor = isDebtor;
    }

    public Rule(Integer minSalary, Boolean isDebtor) {
        this.minSalary = minSalary;
        this.isDebtor = isDebtor;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Boolean getDebtor() {
        return isDebtor;
    }

    public void setDebtor(Boolean debtor) {
        isDebtor = debtor;
    }




    public void delete() {
        setActive(false);
    }
}
