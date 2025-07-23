package com.example.flowabledemo.service;

import com.example.flowabledemo.entity.PurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName PurchaseRequestRepository
 * @Author pt
 * @Description
 * @Date 2025/7/22 20:51
 **/
public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long> {
}