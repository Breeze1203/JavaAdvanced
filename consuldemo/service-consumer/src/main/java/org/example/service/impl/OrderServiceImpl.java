package org.example.service.impl;

import org.apache.coyote.Response;
import org.example.pojo.Order;
import org.example.pojo.Product;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restemplate;

    @Override
    public Order selectOrderById(Integer id) {
        return new Order(id,"order-001","中国",22788D,selectProductListByLoadBalancerAnnotation());
    }

    private List<Product> selectProductListByLoadBalancerAnnotation() {
        // ResponseEntity;封装返回数据
        ResponseEntity<List<Product>> response=restemplate.exchange("http://service-provider/product/list", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
        });
        return response.getBody();
    }
}
