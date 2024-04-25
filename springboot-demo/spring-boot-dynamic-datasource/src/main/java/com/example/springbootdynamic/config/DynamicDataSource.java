package com.example.springbootdynamic.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {
    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> targetDataSources) {
        /*
        通过调用父类的方法 setDefaultTargetDataSource和
        setTargetDataSources 来设置默认数据源和目标数据源映射关系
         */
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(targetDataSources);
    }

    /**
     * 这一步是关键，获取注册的数据源信息
     * @return
     * 实现了动态数据源的功能，根据某个上下文中的数据源标识动态地选择目标数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
