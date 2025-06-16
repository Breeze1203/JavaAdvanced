package com.example.springbootdynamic.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DateSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource dynamicDatasourceMaster() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource dynamicDatasourceSlave() {
        return DruidDataSourceBuilder.create().build();
    }

    /*
    通常用于标识一个Bean定义为首选的候选项。当存在多个相同类型的Bean时，
    Spring容器会选择具有@Primary注解的Bean作为首选项
     */
    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource createDynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        // 设置默认的数据源为Master
        DataSource defaultDataSource = dynamicDatasourceMaster();
        dataSourceMap.put("master", defaultDataSource);
        dataSourceMap.put("slave", dynamicDatasourceSlave());
        return new DynamicDataSource(defaultDataSource, dataSourceMap);
    }

}
