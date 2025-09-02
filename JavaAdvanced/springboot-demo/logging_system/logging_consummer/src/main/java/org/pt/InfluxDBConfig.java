package org.pt;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName InfluxDBConfig
 * @Author pt
 * @Description
 * @Date 2025/8/31 21:30
 **/
@Configuration
public class InfluxDBConfig {
    @Value("${influx.url}") private String url;
    @Value("${influx.token}") private String token;

    @Bean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(url, token.toCharArray());
    }
}