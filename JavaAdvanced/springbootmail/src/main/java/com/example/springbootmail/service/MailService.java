package com.example.springbootmail.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;

@Component
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

//    发送普通文本邮件
    public void sendTextEmail(MailInfo mailInfo){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailInfo.getSetFrom());
        simpleMailMessage.setTo(mailInfo.getSetTo());
        simpleMailMessage.setText("普通文本邮件");
        simpleMailMessage.setSubject("发送普通文本邮件");
        simpleMailMessage.setSentDate(mailInfo.getDate());
        javaMailSender.send(simpleMailMessage);
    }
//     发送附件邮件
    public void sendAttachmentMail(MailInfo mailInfo){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            File file=new File("/Users/pt/Downloads/彭涛.pdf");
            helper.setTo(mailInfo.getSetTo());
            helper.setSubject("附件邮件");
            helper.setText("测试附件邮件发送");
            helper.setFrom(mailInfo.getSetFrom());
            helper.setSentDate(mailInfo.getDate());
            helper.addAttachment(file.getName(),file);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendHtmlMail(String context){
//        利用template模版创建发送html邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("3548297839@qq.com");
            mimeMessageHelper.setSubject("利用template创建html邮件模版");
            mimeMessageHelper.setText(context,true);
            mimeMessageHelper.setTo("3548297839@qq.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
