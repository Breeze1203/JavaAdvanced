package org.example.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/*
发送附件邮件
 */
public class MailTest03 {
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
            transport.connect("smtp.qq.com", "3548297839@qq.com", "ttksttywwdoecjig");
            //    4.设置message邮件对象
            Message simpleMail = createAttachMail(session);
            //    5.发送邮件
            transport.sendMessage(simpleMail, simpleMail.getAllRecipients());
            transport.close();
        } catch (
                NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (
                MessagingException e) {
            System.out.println("连接出错");
            throw new RuntimeException(e);
        }
    }

    private static Message createAttachMail(Session session) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setSubject("附件邮件");
        message.setFrom("3548297839@qq.com");
        message.setSentDate(new Date());
        message.setRecipients(MimeMessage.RecipientType.TO, "3548297839@qq.com");
        MimeMultipart mimeMultipart = new MimeMultipart();
        // 创建附件
        MimeBodyPart attachPart = new MimeBodyPart();
        // 创建正件
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent("使用JavaMail创建的带附件的邮件", "text/html;charset=UTF-8");
        // 本地文件
        DataHandler dataHandler = new DataHandler(new FileDataSource("/Users/pt/Downloads/彭涛.pdf"));
        // 将本地文件添加到附件
        attachPart.setDataHandler(dataHandler);
        attachPart.setFileName(dataHandler.getName());
        mimeMultipart.addBodyPart(mimeBodyPart);
        mimeMultipart.addBodyPart(attachPart);
        // 如果在邮件中要添加附件，设置为mixed；
        mimeMultipart.setSubType("mixed");
        message.setContent(mimeMultipart);
        return message;
    }
}
