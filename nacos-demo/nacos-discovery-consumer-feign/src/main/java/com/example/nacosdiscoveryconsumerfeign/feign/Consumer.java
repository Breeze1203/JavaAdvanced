package com.example.nacosdiscoveryconsumerfeign.feign;

import com.example.nacosdiscoveryconsumerfeign.entity.Product;
import com.example.nacosdiscoveryconsumerfeign.service.FallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "nacos-discovery-provider",fallback = FallbackService.class)
public interface Consumer {

    @PostMapping("/provider/product")
    List<Product> getProduct();
}
