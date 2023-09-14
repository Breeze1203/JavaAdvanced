package org.example.service.impl;

import org.example.pojo.Order;
import org.example.pojo.Product;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient; // Ribbon 负载均衡器


    @Override
    public Order selectOrderById(Integer id) {
        return new Order(id, "order-001", "中国", 31994D,
                selectProductListByLoadBalancerClient());
    }

    private List<Product> selectProductListByDiscoveryClient() {
        // 拼接访问的URL，线程安全
        StringBuffer sb = null;
        // 获取服务列表
        List<String> serviceIds = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(serviceIds)) return null;
        // 根据服务名称获取服务
        List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
        // 如果获取不到服务
        if (CollectionUtils.isEmpty(instances)) return null;
        ServiceInstance serviceInstance = instances.get(0);
        sb = new StringBuffer();
        sb.append("http://").append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort()).append("/product/list");

        // ResponseEntity: 封装了返回数据
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                sb.toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                });
        return response.getBody();
    }

    private List<Product> selectProductListByLoadBalancerClient() {
        StringBuilder sb = null;
        // 根据服务名称获取服务
        ServiceInstance si = loadBalancerClient.choose("service-provider");
        if (null == si)
            return null;
        sb = new StringBuilder();
        sb.append("http://" + si.getHost() + ":" + si.getPort() + "/product/list");
        // ResponseEntity: 封装了返回数据
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                sb.toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                });
        return response.getBody();
    }
}
