package com.bootProject.bootProject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.bootProject.bootProject.dto.MemberDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member_table")
@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq", initialValue = 1, allocationSize = 1)
public class MemberEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
	private Long Id;
	
	@Column(length = 20, nullable = false)
	private String userId;
	
	@Column(length = 200, nullable = false)
	private String userPassword;
	
	@Column(length = 10, nullable = false)
	private String salt;
	
	@Column(length = 8, nullable = false)
	private String userNickname;
	
	@Column(length = 50, nullable = false)
	private String userEmail;
	
	@Column(length = 2, nullable = false)
	@ColumnDefault(value = "'N'")
	private String user_isDelete = "N";
	
	@Column(length = 2, nullable = false)
	@ColumnDefault(value = "'N'")
	private String isKakao = "N";
	
	public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
		MemberEntity entity = new MemberEntity();
		entity.setId(memberDTO.getId());
		entity.setUserEmail(memberDTO.getUserEmail());
		entity.setSalt(memberDTO.getSalt());
		entity.setUserId(memberDTO.getUserId());
		entity.setUserPassword(memberDTO.getUserPassword());
		entity.setUserNickname(memberDTO.getUserNickname());
		entity.setUser_isDelete(memberDTO.getUser_isDelete());
		entity.setIsKakao(memberDTO.getIsKakao());
		
		return entity;
	}
}
