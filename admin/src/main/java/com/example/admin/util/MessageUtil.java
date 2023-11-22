package com.example.admin.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.example.admin.model.User;

public class MessageUtil {
    public static void sendMessage(User user) throws Exception {
        Config config = new Config();
        // 必填，您的 AccessKey ID
        config.setAccessKeyId("LTAI5tCKYkSjSgVnawvexE64");
        config.setAccessKeySecret("6XOoX8RiVKtSYvEjem8YVq4B32Bf68");
        config.endpoint = "dysmsapi.aliyuncs.com";
        Client client = new Client(config);
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(String.valueOf(user.getPhone()));
        sendSmsRequest.setSignName("adminflow");
        sendSmsRequest.setTemplateCode("SMS_463720732");
        String templateParam = "{\"user\":\"" + user.getUsername() + "\",\"password\":\"" + user.getPassword() + "\"}";
        sendSmsRequest.setTemplateParam(templateParam);
        client.sendSms(sendSmsRequest);
    }
}
