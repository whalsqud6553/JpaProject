package com.bootProject.bootProject.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bootProject.bootProject.dto.MemberDTO;
import com.bootProject.bootProject.kakao.KakaoApi;
import com.bootProject.bootProject.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class KakaoController {
	
	private final KakaoApi kakaoApi;
	private final MemberService memberService;
	
	private String accessToken = "";
	
	// not null 속성으로 column에 주입하기 위한 key
	@Value("${cos.key}")
	private String cosKey;
	
	@GetMapping("/login/oauth2/code/kakao")
	public String kakaoLogin(@RequestParam("code") String code, HttpSession session) {
		
		// 토큰 받기
		accessToken = kakaoApi.getAccessToken(code);
		
		// 사용자 정보 받기
		Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String email = (String)userInfo.get("email");
        String nickname = (String)userInfo.get("nickname");
        
//        System.out.println("email = " + email);
//        System.out.println("nickname = " + nickname);
//        System.out.println("accessToken = " + accessToken);
        
        // ------ DB에 정보 넣기
        
        MemberDTO kakaoIdCheck = memberService.userIdDuplicationCheck(email);
        if(kakaoIdCheck != null && kakaoIdCheck.getIsKakao().equals("Y")) {
        	session.setAttribute("user", kakaoIdCheck);
        	
        	return "redirect:/";
        }
        else {
        	MemberDTO memberDTO = new MemberDTO();
            
            // -- 카카오 아이디와 유저 아이디가 겹칠 경우
            MemberDTO userIdCheck = memberService.userIdDuplicationCheck(email);
            int userIdNum = 1;
            String kakaoId = email + userIdNum;
            if(userIdCheck == null) {
            	memberDTO.setUserId(email);
            }
            else {
            	List<MemberDTO> memberList = memberService.findAll();
            	for(int i = 0; i < memberList.size(); i++) {
            		MemberDTO userIdCheck2 = memberService.userIdDuplicationCheck(kakaoId);
            		if(userIdCheck2 == null) {
            			memberDTO.setUserId(kakaoId);
            			userIdNum++;
            		}
            	}
            }
            // --
            
            memberDTO.setUserPassword(cosKey);
            memberDTO.setSalt(cosKey);
            
            // -- 카카오 닉네임과 일반 유저 닉네임이 겹칠 경우
            MemberDTO nickCheck = memberService.userNicknameDuplicationCheck(nickname);
            int nickNum = 1;
            String userNickname = "user" + nickNum;
            if(nickCheck == null) {
                memberDTO.setUserNickname(nickname);
            }
            else {
            	List<MemberDTO> memberList = memberService.findAll();
            	for(int i = 0; i < memberList.size(); i++) {
            		MemberDTO nickCheck2 = memberService.userNicknameDuplicationCheck(userNickname);
            		if(nickCheck2 == null) {
            			memberDTO.setUserNickname(userNickname);
                    	nickNum++;
                    	break;
            		}
            	}
            }
            // --
            
            if(email.contains("@")) {
            	memberDTO.setUserEmail(email);
            }
            else {
            	memberDTO.setUserEmail(email + "@kakao.com");
            }
            
            // ------
            
            memberDTO.setIsKakao("Y");
            
            memberService.insertKakaoMember(memberDTO);
            
            session.setAttribute("user", memberDTO);
            
    		return "redirect:/";
        }
        
	}
	
	@GetMapping("/kakaoLogout")
	public String kakaoLogout(HttpSession session) {
		kakaoApi.kakaoLogout(accessToken);
		
		session.invalidate();
		return "redirect:/";
	}
}
