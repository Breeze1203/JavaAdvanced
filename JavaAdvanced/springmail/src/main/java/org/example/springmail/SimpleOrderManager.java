package org.example.springmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class SimpleOrderManager implements OrderManager{
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;
    @Override
    public void placeOrder() {
        // 获取邮件对象
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        // 设置邮件收件人的邮箱地址
        msg.setTo("3548297839@qq.com");
        // 设置邮件内容
        msg.setText("Hello Spring Mail");
        try{
            // 发送邮件
            this.mailSender.send(msg);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
