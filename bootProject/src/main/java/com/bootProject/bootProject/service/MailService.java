package com.bootProject.bootProject.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService implements MailServiceInter {
	
	
	final JavaMailSender emailSender;
	
	private int code; // 인증번호
	
	// 메일 내용
	@Override
	public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
		System.out.println("보내는 대상 : " + to);
		System.out.println("인증 번호 : " + code);
		
		MimeMessage message = emailSender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to);	// 보내는 대상
		message.setSubject("CodeReview 회원가입 이메일 인증");	// 제목
		
		String msg = "CodeReview 인증 번호는 " + code + " 입니다.";	
		
		message.setText(msg, "utf-8", "html"); // 내용, 문자 타입, subtype
		
		// 보내는 사람의 이메일 주소, 보내는 사람 이름
		message.setFrom(new InternetAddress("fdvc1212@naver.com", "CodeReview 대표"));
		
		return message;
	}
	
	// 랜덤 인증 코드
	@Override
	public int createKey() {
		Random random = new Random();
		int checkCode =  random.nextInt(888888) + 111111;
		
		return checkCode;
	}
	
	// 메일 발송
	// sendSimpleMessage의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
	// MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다
	// 그리고 bean으로 등록해둔 javaMail 객체를 사용해서 이메일 보냄
	@Override
	public int sendSimpleMessage(String to) throws Exception {
		code = createKey();
		System.out.println(to);
		MimeMessage message = createMessage(to); // 메일 발송
		try {
			emailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		}
		return code;
	}
}
