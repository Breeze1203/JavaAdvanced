package com.example.springbootmail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

@Component
public class MailSend {

    @Autowired
    public MailService mailService;

    @Autowired
    public TemplateEngine templateEngine;

    public void sentThymeleafHtml(){
        Context context=new Context();
        context.setVariable("username", "彭涛");
        String emailTemplate =this.templateEngine.process("emailTemplate", context);
        mailService.sendHtmlMail(emailTemplate);
    }

    public void sendText(){
        MailInfo mailInfo = new MailInfo();
        mailInfo.setSetFrom("3548297839@qq.com");
        mailInfo.setSetTo("19972552055@163.com");
        mailInfo.setDate(new Date());
        mailService.sendTextEmail(mailInfo);
    }

    public void sendAttachment(){
        MailInfo mailInfo = new MailInfo();
        mailInfo.setSetFrom("3548297839@qq.com");
        mailInfo.setSetTo("19972552055@163.com");
        mailInfo.setDate(new Date());
        mailService.sendAttachmentMail(mailInfo);
    }
}
