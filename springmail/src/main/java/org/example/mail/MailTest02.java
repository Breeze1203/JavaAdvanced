package org.example.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/*
发送html的邮件
使用javamail发送邮件的步骤
1.创建session对象，加载properties对象
2.通过session对象得到transport对象
3.使用邮箱的用户名和密码连接邮件服务器
4.设置message邮件对象
5.发送邮件

 */
public class MailTest02 {
    public static void main(String[] args) {
        // 设置邮箱服务器的相关配置
        Properties properties = new Properties();
        // 设置邮箱服务器主机名
        properties.setProperty("mail.smtp.host", "smtp.qq.com");
        // 设置邮箱服务器的端口
        properties.setProperty("main.smtp.port", "465");
        // 设置邮箱服务器是否需要身份认真
        properties.setProperty("main.smtp.auto", "true");
        //  使用javamain发送邮件     1.创建session对象，加载properties对象
        Session session = Session.getInstance(properties);
        // 开启session的debug模式，可以在控制台看到邮件的发送状态
        session.setDebug(true);
        //        2.通过session对象得到transport对象
        try {
            Transport transport = session.getTransport();
            //    3.使用邮箱的用户名和密码连接邮件服务器(用户名：@符号前面的内容 密码：授权码
            transport.connect("smtp.qq.com","3548297839@qq.com","ttksttywwdoecjig");
            //    4.设置message邮件对象
            Message simpleMail = createHtmlMail(session);
            //    5.发送邮件
            transport.sendMessage(simpleMail,simpleMail.getAllRecipients());
            transport.close();
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            System.out.println("连接出错");
            throw new RuntimeException(e);
        }
    }

    private static Message createHtmlMail(Session session) throws MessagingException {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 设置发件人的邮箱地址
        message.setFrom("3548297839@qq.com");
        // 设置收件人
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("3548297839@qq.com")});
        message.setSubject("测试发送html的邮件");
        message.setSentDate(new Date());
        /*准备邮件数据*/
        // 设置对象容器（一个Multipart对象可以包含多个bodypart对象）
        MimeMultipart mimeMultipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        // 设置html内容
        StringBuffer sb=new StringBuffer();
        sb.append("<html><body><a href='http://www.baidu.com'>百度一下</a></body></html>");
        // 将html内容设置到邮件体对象中
        mimeBodyPart.setContent(sb.toString(),"text/html;charset=UTF-8");
        // 将邮件对象设置到多媒体对象容器中
        mimeMultipart.addBodyPart(mimeBodyPart);
        // 将多媒体对象容器设置到message邮件对象中
        message.setContent(mimeMultipart);
        return message;
    }
}
