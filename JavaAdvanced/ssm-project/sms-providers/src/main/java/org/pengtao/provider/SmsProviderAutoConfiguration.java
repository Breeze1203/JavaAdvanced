package org.pengtao.provider;

import org.pengtao.SmsService;
import org.pengtao.provider.aliyun.AliyunConfigProperties;
import org.pengtao.provider.aliyun.AliyunSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        AliyunConfigProperties.class,
})
public class SmsProviderAutoConfiguration {

    // 只要 "sms.aliyun.access-key" 存在，就创建阿里云 Bean
    @Bean
    @ConditionalOnProperty(prefix = "sms.aliyun", name = "access-key")
    public SmsService aliyunSmsService(AliyunConfigProperties config) {
        return new AliyunSmsService(config);
    }

}
