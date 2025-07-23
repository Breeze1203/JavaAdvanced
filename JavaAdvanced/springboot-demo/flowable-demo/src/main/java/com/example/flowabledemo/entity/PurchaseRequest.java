package com.example.flowabledemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * @ClassName PurchaseRequest
 * @Author pt
 * @Description
 * @Date 2025/7/22 20:50
 **/
@Entity
public class PurchaseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Double amount;
    private String applicant;
    private String status; //  "PENDING", "APPROVED", "REJECTED"

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getApplicant() { return applicant; }
    public void setApplicant(String applicant) { this.applicant = applicant; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
