package org.example.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;


public class RibbonConfig {

    @Bean
    public IRule LoadBalanceRule(){
        return new RandomRule();
    }
}
