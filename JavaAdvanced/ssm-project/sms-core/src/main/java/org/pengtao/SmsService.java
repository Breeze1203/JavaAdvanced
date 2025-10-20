package org.pengtao;

public interface SmsService {

    /**
     * 发送短信
     */
    SmsResponse send(SmsRequest request);

    /**
     * 获取服务商的唯一标识名称
     */
    String getProviderName();
}
