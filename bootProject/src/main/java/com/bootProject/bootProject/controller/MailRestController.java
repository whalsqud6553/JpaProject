package com.bootProject.bootProject.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootProject.bootProject.dto.MemberDTO;
import com.bootProject.bootProject.service.MailService;
import com.bootProject.bootProject.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MailRestController {
	
	final MailService mailService;
	private final MemberService memberService;
	
	@PostMapping("mail/auth")
	public int mailConfirm(@RequestParam("email") String email) throws Exception {
		int code = 0;
		System.out.println(email);
		code = mailService.sendSimpleMessage(email);
		
		
		System.out.println("code : " + code);
		return code;
	}
	
	@PostMapping("findPassword/auth")
	public int mailConfirm(@RequestParam("email") String email, @RequestParam("userid") String userid) throws Exception {
		int code = 0;
		MemberDTO memberDTO = memberService.userIdDuplicationCheck(userid);
		if(memberDTO != null) {
			if(memberDTO.getUserEmail().equals(email)) {
				code = mailService.sendSimpleMessage(email);
			}
			else {
				code = 0;
			}
		}
		
		System.out.println("code : " + code);
		return code;
	}
}
