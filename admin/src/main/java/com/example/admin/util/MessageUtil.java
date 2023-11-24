package com.example.admin.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.example.admin.model.User;

public class MessageUtil {
    private static final String key_ID="LTAI5tFszS2AtLqKqPFwdpq9";

    private static final String Key_Secret="yC4xvTPqZA9XBHVZ1PINspyqF9xuMU";

    private static final String point="dysmsapi.aliyuncs.com";
    // 发送短信提示密码更改
    public static void sendMessage(User user){
        Config config = message();
        Client client = null;
        try {
            client = new Client(config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(String.valueOf(user.getPhone()));
        sendSmsRequest.setSignName("adminflow");
        sendSmsRequest.setTemplateCode("SMS_463720732");
        String templateParam = "{\"user\":\"" + user.getUsername() + "\",\"password\":\"" + user.getPassword() + "\"}";
        sendSmsRequest.setTemplateParam(templateParam);
        try {
            client.sendSms(sendSmsRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 短信验证码
    public static void sendVerificationCode(String phone,int code){
        Config config = message();
        Client client = null;
        try {
            client = new Client(config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(phone);
        sendSmsRequest.setSignName("adminflow");
        sendSmsRequest.setTemplateCode("SMS_464030382");
        String templateParam = "{\"code\":\""+code+"\"}";
        sendSmsRequest.setTemplateParam(templateParam);
        try {
            client.sendSms(sendSmsRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Config message(){
        Config config = new Config();
        // 必填，您的 AccessKey ID
        config.setAccessKeyId(key_ID);
        config.setAccessKeySecret(Key_Secret);
        config.endpoint = point;
        return config;
    }
}
