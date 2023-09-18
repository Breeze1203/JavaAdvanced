package com.example.springbootmail;

import com.example.springbootmail.service.MailSend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringbootmailApplicationTests {
	@Autowired
	MailSend mailSend;

	@Test
	void contextLoads() {
//		mailSend.sendText();
//		mailSend.sentThymeleafHtml();
		mailSend.sendAttachment();
	}

}
