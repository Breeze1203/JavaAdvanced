package org.example.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/*
发送普通文件的有机
使用javamail发送邮件的步骤
1.创建session对象，加载properties对象
2.通过session对象得到transport对象
3.使用邮箱的用户名和密码连接邮件服务器
4.设置message邮件对象
5.发送邮件

 */
public class MailTest01 {
    public static void main(String[] args) {
        // 设置邮箱服务器的相关配置
        Properties properties = new Properties();
        // 设置邮箱服务器主机名
        properties.setProperty("mail.smtp.host", "smtp.qq.com");
        // 设置邮箱服务器的端口
        properties.setProperty("main.smtp.port", "25");
        // 设置邮箱服务器是否需要身份认真
        properties.setProperty("main.smtp.auto", "true");
        Session session = Session.getInstance(properties);
        //  使用javamain发送邮件     1.创建session对象，加载properties对象
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("3548297839@qq.com","fcztcachmdxqdbij");
//            }
//        });
        // 开启session的debug模式，可以在控制台看到邮件的发送状态
        session.setDebug(true);
        //        2.通过session对象得到transport对象
        try {
            Transport transport = session.getTransport();
            //    3.使用邮箱的用户名和密码连接邮件服务器(用户名：@符号前面的内容 密码：授权码
            transport.connect("smtp.qq.com","3548297839@qq.com","zspfodhlerezchde");
                //4.设置message邮件对象
            Message simpleMail = createSimpleMail(session);
            //    5.发送邮件
            transport.sendMessage(simpleMail,simpleMail.getAllRecipients());
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            System.out.println("连接出错");
            throw new RuntimeException(e);
        }
    }
    private static Message createSimpleMail(Session session) throws MessagingException {
        // 创建邮件对象
        MimeMessage message=new MimeMessage(session);
        // 设置邮件的发送人
        message.setFrom("3548297839@qq.com");
        // 设置邮件的接收人 发件人和接收认识同一个
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("3548297839@qq.com"));
        // 设置邮件的主题
        message.setSubject("测试文本邮件");
        // 设置发送日期
        message.setSentDate(new Date());
        // 设置邮件的文本内容
        message.setText("你好，这是一封测试文本的邮件！");
        // 返回封装好的邮箱对象
        return message;
    }
}
