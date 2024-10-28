package com.bootProject.bootProject.dto;

import com.bootProject.bootProject.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	
	private Long Id;
	private String userId;
	private String userPassword;
	private String salt;
	private String userNickname;
	private String userEmail;
	private String user_isDelete = "N";
	private String isKakao = "N";
	
	public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
		MemberDTO dto = new MemberDTO();
		dto.setId(memberEntity.getId());
		dto.setUserId(memberEntity.getUserId());
		dto.setUserPassword(memberEntity.getUserPassword());
		dto.setSalt(memberEntity.getSalt());
		dto.setUserNickname(memberEntity.getUserNickname());
		dto.setUserEmail(memberEntity.getUserEmail());
		dto.setUser_isDelete(memberEntity.getUser_isDelete());
		dto.setIsKakao(memberEntity.getIsKakao());
		
		return dto;
	}
}
