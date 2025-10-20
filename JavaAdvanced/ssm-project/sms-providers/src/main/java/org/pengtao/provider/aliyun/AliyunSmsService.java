package org.pengtao.provider.aliyun;

import org.pengtao.SmsRequest;
import org.pengtao.SmsResponse;
import org.pengtao.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AliyunSmsService implements SmsService {
    private static final Logger log = LoggerFactory.getLogger(AliyunSmsService.class);
    private final AliyunConfigProperties config;

    public AliyunSmsService(AliyunConfigProperties config) {
        this.config = config;
        // 在此初始化阿里云的真实 SDK Client
        log.info("AliyunSmsService 初始化... AccessKey: {}", config.getAccessKey());
    }

    @Override
    public SmsResponse send(SmsRequest request) {
        log.info("【阿里云】发送短信至: {}", request.phone());
        return new SmsResponse(true, "ali-msg-id", "ali-raw-response");
    }

    @Override
    public String getProviderName() {
        return "aliyun";
    }
}
