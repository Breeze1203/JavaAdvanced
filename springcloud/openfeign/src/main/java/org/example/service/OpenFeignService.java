package org.example.service;

import org.example.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**

 这里指定服务提供者名 openfeign也支持继承，
 可以定义一个公共模块，定义一个接口，使provider里面的controller和这里的接口继承该类
 第二个参数定义服务降级 也可以通过自定义fallbackFactory ,定义一个类
 implements FallbackFactory<OpenFeignService>
 @FeignClient(value = "service-provider",fallbackFactory = HystrixService.class)
 **/
@FeignClient(value = "service-provider",fallback = HystrixService.class)
public interface OpenFeignService {
    /*
    这里相当于调用service-provider里面的
   @RestController
   @RequestMapping("/product")
   public class ProductController {
    @Resource(name = "ProductService")
    ProductService productService;

    @GetMapping("/list")
    public List<Product> getProductList(){
        return productService.getById(1);
    }
}
     */
    @GetMapping("/list")
    List<Product> getProduct();

//    参数类型传递 凡事key/value类型的参数，一定要标记参数的名称
    @GetMapping("/product")
    String hello(@RequestParam("name")String name);

    @PostMapping("/product1")
    Product addProduct(Product product);

    //其中{id}表示URL路径中的参数@PathVariable("id")注解将该路径参数绑定到方法的id参数上，
    @DeleteMapping("/product2/{id}")
    void deleteProductById(@PathVariable("id")Integer id);

    //RequestHeader("name")注解将HTTP请求头参数的值绑定到方法的参数上
    @GetMapping("/product3")
    void getProductByName(@RequestHeader("name") String name);
}
