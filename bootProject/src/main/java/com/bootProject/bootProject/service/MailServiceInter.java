package com.bootProject.bootProject.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public interface MailServiceInter {

	// 메일 내용 작성
	MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException;
	
	// 랜덤 인증코드 생성
	int createKey();
	
	// 메일 발송
	int sendSimpleMessage(String to) throws Exception;
}
