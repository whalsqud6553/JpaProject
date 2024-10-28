package com.bootProject.bootProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bootProject.bootProject.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	
	@Query(value = "select m from MemberEntity m where m.userId = :userid")
	MemberEntity findByUserId(@Param("userid") String userid);
	
	@Query(value = "select m from MemberEntity m where m.userNickname = :userNickname")
	MemberEntity finByUserNickname(@Param("userNickname") String userNickname);
	
	@Modifying
	@Query(value ="update MemberEntity m set m.userPassword = :hash where m.userId = :userid")
	int updatePasswordByUserId(@Param("hash") String hash,  @Param("userid") String userid);
	
	@Query(value = "select m from MemberEntity m where m.userEmail = :email")
	List<MemberEntity> findMemberEntityByUserEmail(@Param("email") String email);
	
	@Modifying
	@Query(value = "update MemberEntity m set m.user_isDelete = 'Y' where m.userId = :userid")
	int deleteByUserId(@Param("userid") String userid);
	
}
