package com.example.nacosdiscoveryconsumer.controller;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.example.nacosdiscoveryconsumer.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @GetMapping("/getProduct")
    public List<Product> get(){
        // 拼接访问的URL，线程安全
        StringBuffer sb = null;
        // 获取服务列表
        List<String> serviceIds = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(serviceIds)) return null;
        // 根据服务名称获取服务
        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-discovery-provider");
        // 如果获取不到服务
        if (CollectionUtils.isEmpty(instances)) return null;
        ServiceInstance serviceInstance = instances.get(0);
        sb = new StringBuffer();
        sb.append("http://").append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort()).append("/product");
        ResponseEntity<List<Product>> response = restTemplate.exchange(sb.toString(), HttpMethod.POST, null, new ParameterizedTypeReference<List<Product>>() {
        });
        return response.getBody();
    }

    @GetMapping("/testGateway")
    public String gateway(){
        return "成功";
    }
}
