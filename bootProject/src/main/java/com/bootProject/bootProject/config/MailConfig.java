package com.bootProject.bootProject.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		
		javaMailSender.setHost("smtp.naver.com"); 	// 메인 도메인 서버 주소 -> smtp 도메인 서버 주소
		javaMailSender.setUsername("fdvc1212");		// 네이버 아이디
		javaMailSender.setPassword("wh10670159");	// 네이버 비밀번호
		
		javaMailSender.setPort(465);	// 포트번호 (네이버는 465)
		
		javaMailSender.setJavaMailProperties(getProperties());
		
		return javaMailSender;
	}
	
	private Properties getProperties() {
		Properties properties = new Properties();
		
		properties.setProperty("mail.transport.protocol", "smtp"); // 프로토콜 설정
        properties.setProperty("mail.smtp.auth", "true"); // smtp 인증
        properties.setProperty("mail.smtp.starttls.enable", "true"); // smtp strattles 사용
        properties.setProperty("mail.debug", "true"); // 디버그 사용
        properties.setProperty("mail.smtp.ssl.trust","smtp.naver.com"); // ssl 인증 서버는 smtp.naver.com
        properties.setProperty("mail.smtp.ssl.enable","true"); // ssl 사용
        
		return properties;
	}
	
}
