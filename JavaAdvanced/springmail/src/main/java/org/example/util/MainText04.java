package org.example.util;

import java.util.ArrayList;
import java.util.List;

public class MainText04 {
    public static void main(String[] args) {
        testAttachMail();
    }

    public static void testAttachMail(){
        MailSendInfo mailSendInfo = new MailSendInfo();
        mailSendInfo.setServerHost("smtp.163.com");
        mailSendInfo.setServerPort("25");
        mailSendInfo.setUserName("19972552055@163.com");
        mailSendInfo.setUserPwd("NLWHGXZQZKUCAJUQ");
        mailSendInfo.setSubject("邮件封装");
        List<String> files = new ArrayList<>();
        files.add("/Users/pt/Downloads/彭涛.pdf");
        files.add("/Users/pt/Downloads/RocketMq.pdf");
        mailSendInfo.setAttachFileNames(files);
        mailSendInfo.setFromAddress("19972552055@163.com");
        mailSendInfo.setContent("<h2>这是封装后发送的邮件</h2>");
        List<String> users = new ArrayList<>();
        users.add("19972552055@163.com");
        mailSendInfo.setToAddress(users);
        MailSender mailSender=new MailSender();
        mailSender.sendMail(mailSendInfo);
    }
}
