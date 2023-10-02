package org.example.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.example.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("HystrixService")
public class HystrixService {
    @Autowired
    RestTemplate restTemplate;

    /*
    在这个方法中，我嫩将发起一个远程调用，去调用eureka-provider里面的/product/list接口
    @HystrixCommand这个注解表示方法调用失效时的临时替代方法
     */

    // 忽略异常，当这个方法出现异常时后，不进行服务降级，直接抛出异常
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class)
    @CacheResult//表示该方法的请求结果会被缓存起来，缓存的key是方法的参数，缓存的value就是方法的返回值 这里不会生效
    public List<Product> getProduct(String name){
        String url = "http://192.168.3.49:7071/product/list";
        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );
        System.out.println("测试缓存");
        return responseEntity.getBody();
    }

    /* 服务降级
    注解实现
    注意这个方法名字要和fallbackMethod一致，方法返回值也要和对应方法一致
    代码出现异常，服务降级的同时也可以获取到异常信息
     */
    public List<Product> error(String name,Throwable e){
        ArrayList<Product> products = new ArrayList<>();
        Product product = new Product("小米", 3000, 56);
        products.add(product);
        System.out.println(e.getMessage());
        return products;
    }
}
