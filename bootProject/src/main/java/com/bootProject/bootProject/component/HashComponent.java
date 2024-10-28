package com.bootProject.bootProject.component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HashComponent {

	// 해쉬 처리하기 위한 salt값
	public String getSalt() {
		String salt = UUID.randomUUID().toString().substring(0, 8);
		
		return salt;
	}
	
	// 해쉬처리
	public String getHash(String source, String salt) {
		
		// 비밀번호를 암호화 하기 위해 messageDigest SHA512 알고리즘으로 해시값 생성
		try {
			MessageDigest md = MessageDigest.getInstance("SHA512");
			
			// 데이터를 해시(다이제스트 갱신) 한다
			md.update(salt.getBytes());
			md.update(source.getBytes());
			
			// salt와 source로 128자리 16진수를 양수로 저장
			String hash = String.format("%0128x", new BigInteger(md.digest()));
			
			return hash;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
