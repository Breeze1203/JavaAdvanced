package org.pengtao.single;

import org.pengtao.SmsFactory;
import org.pengtao.SmsService;
import org.pengtao.provider.SmsProviderAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
// 确保 Provider AutoConfig 先运行
@AutoConfigureAfter(SmsProviderAutoConfiguration.class)
public class SmsFactoryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SmsFactory.class)
    public SmsFactory smsFactory(List<SmsService> allServices) {
        // 将所有创建的 Bean 注入工厂
        return new DefaultSmsFactory(allServices);
    }
}