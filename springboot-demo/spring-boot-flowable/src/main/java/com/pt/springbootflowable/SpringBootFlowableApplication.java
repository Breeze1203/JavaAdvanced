package com.pt.springbootflowable;

import com.pt.springbootflowable.service.MyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(proxyBeanMethods = false)
public class SpringBootFlowableApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFlowableApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(final MyService myService) {

		return new CommandLineRunner() {
			public void run(String... strings) throws Exception {
				myService.createDemoUsers();
			}
		};
	}
}
