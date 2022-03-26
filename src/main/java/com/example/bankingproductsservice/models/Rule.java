package com.example.bankingproductsservice.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;



@Entity
@Table(name = "rules")
@EntityListeners(AuditingEntityListener.class)
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    private Boolean isActive = true;

    private Integer minSalary;
    private Boolean allowDebtors;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    public Rule() {
    }

    public Rule(Product product, Integer minSalary) {
        this.product = product;
        this.minSalary = minSalary;
    }

    public Rule(Product product, Boolean allowDebtors) {
        this.product = product;
        this.allowDebtors = allowDebtors;
    }

    public Rule(Product product, Integer minSalary, Boolean allowDebtors) {
        this.product = product;
        this.minSalary = minSalary;
        this.allowDebtors = allowDebtors;
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

    public Boolean getAllowDebtors() {
        return allowDebtors;
    }

    public void setAllowDebtors(Boolean allowDebtors) {
        this.allowDebtors = allowDebtors;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        if (!isActive){
            return "";
        }
        return  "\n   {\n" +
                "       \"id\": " + id + ",\n" +
                "       \"createdDate\": \"" + createdDate + "\",\n" +
                "       \"lastModifiedDate\": \"" + lastModifiedDate + "\",\n" +
                "       \"isActive\": " + isActive + ",\n" +
                "       \"minSalary\": " + minSalary + ",\n" +
                "       \"isDebtor\": " + allowDebtors + ",\n" +
                "   }";
    }



    public void delete() {
        setActive(false);
    }

    public boolean isMatches(Integer salary, Boolean isDebtor) {
        return isMatchesSalary(salary) && isMatchesIsDebtor(isDebtor);
    }

    // Примечание: Если переданная зп равна минимальной зп в правиле, вернёт true;
    private boolean isMatchesSalary(Integer salary) {
        if (minSalary == null) return true;
        if (salary == null) return false;
        return salary >= minSalary;
    }

    // Если правило позволяет слиенту иметь задолженность или
    // правило по задолженности не установлено, возвращается true
    // Если правило не позволяет иметь задолженность, возврашается !isDebtor
    private boolean isMatchesIsDebtor(Boolean isDebtor) {
        if (this.allowDebtors == null || this.allowDebtors) return true;
        return !isDebtor;
    }
}
