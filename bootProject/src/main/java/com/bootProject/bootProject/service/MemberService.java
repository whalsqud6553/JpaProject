package com.bootProject.bootProject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootProject.bootProject.component.HashComponent;
import com.bootProject.bootProject.dto.MemberDTO;
import com.bootProject.bootProject.entity.MemberEntity;
import com.bootProject.bootProject.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final HashComponent hashComponent;
	
	@Transactional
	public MemberDTO userIdDuplicationCheck(String userid) {
		MemberEntity memberEntity = memberRepository.findByUserId(userid);
		if(memberEntity != null) {
			MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
			
			return memberDTO;
		}
		else {
			return null;
		}
		
	}

	public MemberEntity insertMember(MemberDTO dto) {
		if(dto != null) {
			String salt = hashComponent.getSalt();
			String hashPassword = hashComponent.getHash(dto.getUserPassword(), salt);
			dto.setUserPassword(hashPassword);
			dto.setSalt(salt);
		}
		MemberEntity memberEntity = MemberEntity.toSaveEntity(dto);
		MemberEntity saveMember = memberRepository.save(memberEntity);
		
		return saveMember;
	}
	
	@Transactional
	public MemberDTO userNicknameDuplicationCheck(String userNickname) {
		MemberEntity memberEntity = memberRepository.finByUserNickname(userNickname);
		if(memberEntity != null) {
			MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
			
			return memberDTO;
		}
		else {
			return null;
		}
	}

	public MemberDTO login(MemberDTO memberDTO) {
		// userID로 찾기
		MemberEntity memberEntity = memberRepository.findByUserId(memberDTO.getUserId());
		if(memberEntity != null) {
			// 웹에서 입력한 비밀번호와 DB에 저장된 salt 값으로 해시 값 생성
			String hashPassword = hashComponent.getHash(memberDTO.getUserPassword(), memberEntity.getSalt());
			// DB에 저장된 해시 비밀번호와 위에서 해시 처리한 hashPassword와 같다면 로그인
			if(memberEntity.getUserPassword().equals(hashPassword)) {
				MemberDTO user = MemberDTO.toMemberDTO(memberEntity);
				return user;
			}
			
			return null;
		}
		else {

			return null;
		}
		
	}
	
	@Transactional
	public int updatePasswordByUserId(String userpw, String userid) {
		MemberEntity memberEntity = memberRepository.findByUserId(userid);
		MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
		String salt = memberDTO.getSalt();
		String hash = hashComponent.getHash(userpw, salt);
		
		int row = memberRepository.updatePasswordByUserId(hash, userid);
		
		return row;
	}
	
	@Transactional
	public List<MemberDTO> findMemberEntityByUserEmail(String email) {
		List<MemberDTO> memberDTOList = new ArrayList<MemberDTO>();
		List<MemberEntity> memberEntityList = memberRepository.findMemberEntityByUserEmail(email);
		for(MemberEntity list : memberEntityList) {
			MemberDTO memberDTO = MemberDTO.toMemberDTO(list);
			memberDTOList.add(memberDTO);
		}
		
		return memberDTOList;
	}

	public String passwordCheck(String userpw, String userid) {
		MemberEntity memberEntity = memberRepository.findByUserId(userid);
		
		if(memberEntity != null) {
			MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
			
			String userpassword = memberDTO.getUserPassword();	// userid의 비밀번호 hash 값
			String changePassword = hashComponent.getHash(userpw, memberDTO.getSalt());	// 입력받은 비밀번호의 hash 값
			
			if(userpassword.equals(changePassword)) {
				return "equals";
			}
			else {
				return "fail";
			}
			
		}
		else {
			return "fail";
		}
		
		
	}
	
	@Transactional
	public int deleteMember(String userid) {
		int row = memberRepository.deleteByUserId(userid);
		
		return row;
	}

	public List<MemberDTO> findAll() {
		List<MemberEntity> memberEntityList = memberRepository.findAll();
		List<MemberDTO> memberDtoList = new ArrayList<MemberDTO>();
		for(MemberEntity entity : memberEntityList) {
			MemberDTO memberDTO = MemberDTO.toMemberDTO(entity);
			memberDtoList.add(memberDTO);
		}
		return memberDtoList;
	}

	public void insertKakaoMember(MemberDTO memberDTO) {
		MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
		memberRepository.save(memberEntity);
		
	}
}
