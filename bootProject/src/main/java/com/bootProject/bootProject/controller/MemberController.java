package com.bootProject.bootProject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootProject.bootProject.dto.MemberDTO;
import com.bootProject.bootProject.entity.MemberEntity;
import com.bootProject.bootProject.kakao.KakaoApi;
import com.bootProject.bootProject.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final KakaoApi kakaoApi;
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
		model.addAttribute("redirectUrl", kakaoApi.getRedirectUrl());
		
		return "/member/login";
	}
	
	@PostMapping("/login")
	public String login(MemberDTO memberDTO, HttpSession session) {
		
		MemberDTO user = memberService.login(memberDTO);
		
		if(user != null) {
			if(user.getUser_isDelete().equals("N")) {
				session.setAttribute("user", user);
				
				return "redirect:/";
			}
			else {
				return "/member/loginDelete_msg";
			}
		}
		else {
			
			return "/member/login_msg";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/";
	}
	
	@GetMapping("/join")
	public String join() {
		
		return "/member/join";
	}
	
	@PostMapping("/join")
	public String join(MemberDTO dto) {
		MemberEntity memberEntity =  memberService.insertMember(dto);
		if(memberEntity != null) {
			return "/member/join_msg";
		}
		else {
			return "/member/join_error";
		}
		
	}
	
	@GetMapping("/mypage")
	public String mypage() {
		
		return "/member/mypage";
	}
	
	// ---- ID 중복 체크
	@PostMapping("/userIdDuplicationCheck")
	@ResponseBody
	public int userIdDuplicationCheck(@RequestParam("userid") String userid) {
		int row = 0;
		MemberDTO memberDTO = memberService.userIdDuplicationCheck(userid);
		if(memberDTO == null) {
			row = 1;
		}
		return row;
	}
	// ----
	
	// ---- 닉네임 중복체크
	@PostMapping("/userNicknameDuplicationCheck")
	@ResponseBody
	public int userNicknameDuplicationCheck(@RequestParam("userNickname") String userNickname) {
		int row = 0;
		MemberDTO memberDTO = memberService.userNicknameDuplicationCheck(userNickname);
		System.out.println(userNickname);
		if(memberDTO == null) {
			row = 1;
		}
		else {
			row = 0;
		}
		
		return row;
	}
	
	// ----
	
	// ---- 비밀번호 찾기
	@GetMapping("/findPassword")
	public String findPassword() {
		
		return "/member/findPassword";
	}
	
	@PostMapping("/idCheck")
	@ResponseBody
	public String idCheck(@RequestParam("userid") String userid) {
		MemberDTO memberDTO = memberService.userIdDuplicationCheck(userid);
		if(memberDTO != null) {
			return "success";
		}
		else {
			return "fail";
		}
	}
	
	@PostMapping("/updatePassword")
	@ResponseBody
	public int updatePasswordByUserId(@RequestParam("userpw") String userpw, @RequestParam("userid") String userid) {
		System.out.println(userpw);
		System.out.println(userid);
		int row = memberService.updatePasswordByUserId(userpw, userid);
		System.out.println("update rows : " + row);
		
		return row;
	}
	// ----
	
	// ---- 아이디 찾기
	@GetMapping("/findUserId")
	public String findUserId() {
		
		return "/member/findUserId";
	}
	
	@PostMapping("/emailCheck")
	@ResponseBody
	public List<MemberDTO> emailCheck(@RequestParam("email") String email) {
		List<MemberDTO> memberDTOList = memberService.findMemberEntityByUserEmail(email);
		
		return memberDTOList;
	}
	
	// ----
	
	// ---- 비밀번호 변경
	@GetMapping("/updatePassword")
	public String updatePassword() {
		return "/member/updatePassword";
	}
	
	@PostMapping("/pwCheck")
	@ResponseBody
	public String passwordCheck(@RequestParam("userpw") String userpw, @RequestParam("userid") String userid) {
		String str = memberService.passwordCheck(userpw, userid);
		
		return str;
	}
	// ----
	
	// ---- 회원 탈퇴
	@GetMapping("/memberSecession")
	public String memberSecession() {
		return "/member/memberSecession";
	}
	
	@GetMapping("/memberDelete/{userid}")
	public String memberDelete(@PathVariable("userid") String userid, HttpSession session) {
		int row = memberService.deleteMember(userid);
		if(row != 0) {
			session.invalidate();
			return "/member/delete_msg";
		}
		else {
			return "redirect:/";
		}
	}
	// ----
}
