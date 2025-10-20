package org.pengtao.single;

import org.pengtao.SmsException;
import org.pengtao.SmsFactory;
import org.pengtao.SmsService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


public class DefaultSmsFactory implements SmsFactory {

    private final Map<String, SmsService> registry;

    // 构造函数注入所有 SmsService Beans
    public DefaultSmsFactory(List<SmsService> allServices) {
        this.registry = allServices.stream()
                .collect(Collectors.toMap(SmsService::getProviderName, Function.identity()));
    }

    @Override
    public SmsService getService(String providerName) {
        SmsService service = registry.get(providerName);
        if (service == null) {
            throw new SmsException("未找到名称为 '" + providerName + "' 的SmsService实例。");
        }
        return service;
    }

    @Override
    public Set<String> getProviderNames() {
        return registry.keySet();
    }
}
