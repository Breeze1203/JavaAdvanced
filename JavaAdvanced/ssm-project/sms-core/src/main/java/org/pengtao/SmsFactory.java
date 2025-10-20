package org.pengtao;

import java.util.Set;

public interface SmsFactory {

    /**
     * 根据名称获取一个SmsService实例
     * @param providerName "aliyun", "tencent" 等
     */
    SmsService getService(String providerName);

    /**
     * 获取所有已注册的厂商名称
     */
    Set<String> getProviderNames();
}
