### nacos config 配置中心
1.添加依赖
```
<dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```
2.在nacos里面添加配置
```
Data ID:    nacos-config.properties
Group  :    DEFAULT_GROUP
配置格式:    Properties
配置内容：   username=彭涛
```
**注意dataId是以bootstrap.properties(默认的文件扩展名方式)为扩展名**<br>
3.创建springboot应用，编写配置文件名称<br>
```
spring.application.name=nacos-discovery-config
spring.cloud.nacos.config.server-addr=localhost:8848
server.port=8854
```
**注意spring.application.name要与Data ID一致**<br>
4.测试
```
@SpringBootApplication
public class NacosDiscoveryConfigApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(NacosDiscoveryConfigApplication.class, args);
        String username = run.getEnvironment().getProperty("username");
        System.out.println(username);
    }

}
输出：
彭涛
```
5.基于 dataId 为 yaml 的文件扩展名配置方式<br>
在应用的 bootstrap.properties 配置文件中显示的声明 dataId 文件扩展名<br>
```
spring.cloud.nacos.config.file-extension=yaml
```
spring-cloud-starter-alibaba-nacos-config 在加载配置的时候，**不仅仅加载了以 dataId 为${spring.application.name}.${file-extension:properties} 为前缀的基础配置**<br>
**还加载了dataId为 ${spring.application.name}-${profile}.${file-extension:properties} 的基础配置**<br>
优先级nacos-discovery-config-dev.yaml > nacos-discovery-config.yaml<br>
在日常开发中如果遇到多套环境下的不同配置，可以通过Spring 提供的 ${spring.profiles.active} 这个配置项来配置<br>
也就是说，如果现在有两个环境，一个为pro,一个为dev<br>
**可以在bootstrap.yaml文件里面配置**
```
spring:
  profiles:
    active: pro/dev
  application:
    name: nacos-discovery-config
  cloud:
    nacos:
      config:
        server-addr: localhost:8848
        file-extension: yaml
server:
  port: 8854
```
此时只需在nacos里面加入两个配置文档<br>
名称分别为nacos-discovery-config-dev.yaml和nacos-discovery-config-pro.yaml<br>
[更详细文档](https://github.com/alibaba/spring-cloud-alibaba/wiki/Nacos-config)




