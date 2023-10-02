package org.example.config;

import com.netflix.hystrix.HystrixCommand;
import org.example.pojo.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class HystrixConfig extends HystrixCommand<List<Product>> {
    private RestTemplate restTemplate;

    public HystrixConfig(Setter setter, RestTemplate restTemplate) {
        super(setter);
        this.restTemplate = restTemplate;
    }

    /*
    请求方法失败的回调
     */
    @Override
    protected List<Product> getFallback() {
        // 这里也可以访问到异常信息
        String message = getExecutionException().getMessage();
        System.out.println(message);
        Product product = new Product("华硕", 4550, 7);
        ArrayList<Product> list = new ArrayList<>();
        list.add(product);
        return list;
    }

    @Override
    protected List<Product> run() throws Exception {
        String url = "http://192.168.3.49:7071/product/list";
        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );
        return responseEntity.getBody();
    }
}
