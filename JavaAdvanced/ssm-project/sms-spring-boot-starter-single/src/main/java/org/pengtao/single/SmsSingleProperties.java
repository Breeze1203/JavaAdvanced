package org.pengtao.single;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sms")
public class SmsSingleProperties {
    /**
     * 全局启用
     */
    private boolean enabled = true;
    /**
     * 选择要使用的服务商 (aliyun, tencent, mock)
     */
    private String provider = "mock"; // 默认使用 mock

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}