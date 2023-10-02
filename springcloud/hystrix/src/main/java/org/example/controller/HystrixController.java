package org.example.controller;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.example.config.HystrixConfig;
import org.example.pojo.Product;
import org.example.service.HystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/getProduct")
public class HystrixController {
    @Resource(name = "HystrixService")
    HystrixService hystrixService;
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/")
    public List<Product> getProduct(){
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        List<Product> product = hystrixService.getProduct("测试缓存");
        // 在ctx之前缓存有效
        ctx.close();
        return product;
    }

    @RequestMapping("/two")
    public List<Product> getPro(){
        HystrixConfig hystrixConfig = new HystrixConfig(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("javaboypt")), restTemplate);
        List<Product> execute = hystrixConfig.execute(); // 同步调用直接执行
        try {
            // 二者选其一
//            Future<List<Product>> queue = hystrixConfig.queue();// 异步调用 先入队后执行
//            List<Product> products = queue.get();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }
            return execute;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
