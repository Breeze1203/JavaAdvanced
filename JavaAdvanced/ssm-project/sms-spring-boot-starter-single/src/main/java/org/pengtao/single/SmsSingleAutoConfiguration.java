package org.pengtao.single;

import org.pengtao.SmsService;
import org.pengtao.provider.SmsProviderAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(SmsSingleProperties.class)
// 确保 Provider AutoConfig 先运行
@AutoConfigureAfter(SmsProviderAutoConfiguration.class)
// "sms.enabled" 开关
@ConditionalOnProperty(prefix = "sms", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SmsSingleAutoConfiguration {

    @Bean
    // 允许用户自定义覆盖
    @ConditionalOnMissingBean(SmsService.class)
    public SmsService singleSmsService(SmsSingleProperties props,
                                       List<SmsService> allServices) {
        String providerName = props.getProvider();
        Map<String, SmsService> serviceMap = allServices.stream()
                .collect(Collectors.toMap(SmsService::getProviderName, Function.identity()));
        SmsService selectedService = serviceMap.get(providerName);
        if (selectedService == null) {
            throw new SmsException(
                    "未找到名称为 '" + providerName + "' 的SmsService配置。" +
                            "请检查 'sms.provider' 属性是否正确，或者对应厂商 (如 sms.aliyun) 是否已配置。"
            );
        }

        return selectedService;
    }
}
