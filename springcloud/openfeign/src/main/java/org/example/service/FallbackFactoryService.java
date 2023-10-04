package org.example.service;

import feign.hystrix.FallbackFactory;
import org.example.pojo.Product;

import java.util.List;

public class FallbackFactoryService implements FallbackFactory<OpenFeignService> {
    @Override
    public OpenFeignService create(Throwable throwable) {
        return new OpenFeignService() {
            @Override
            public List<Product> getProduct() {
                return null;
            }

            @Override
            public String hello(String name) {
                return null;
            }

            @Override
            public Product addProduct(Product product) {
                return null;
            }

            @Override
            public void deleteProductById(Integer id) {

            }

            @Override
            public void getProductByName(String name) {

            }
        };
    }
}
