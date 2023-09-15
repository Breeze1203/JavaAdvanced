package org.example;

import org.example.springmail.SimpleOrderManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws MessagingException {
        ApplicationContext ac=new ClassPathXmlApplicationContext("spring.xml");
        SimpleOrderManager simpleOrderManager =
                (SimpleOrderManager) ac.getBean("simpleOrderManager");
        simpleOrderManager.placeOrder();

        JavaMailSender mailSender = (JavaMailSender) ac.getBean("mailSender");
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress("3548297839@qqq.com"));
        message.setSubject("spring_mail"); // 邮件主题
        // 创建带有附件的消息帮组类
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
        helper.setTo(new InternetAddress("收件人的邮箱地址"));//设置接收人
        helper.setText("Thank you for ordering!"); // 邮件内容
        helper.setFrom("发件人的邮箱地址"); // 发件人
        // 设置附件
        File file = new File("C:\\work\\邮件附件.txt");
        // 添加附件
        helper.addAttachment(file.getName(), file);
        // 发送邮件
        mailSender.send(message);
    }
}
