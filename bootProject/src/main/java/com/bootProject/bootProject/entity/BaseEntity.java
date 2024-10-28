package com.bootProject.bootProject.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
	
	@CreationTimestamp	// 생성된 시간
	@Column(updatable = false)	// 업데이트에 관여 안함
	private LocalDateTime createdTime;
	
	@UpdateTimestamp
	@Column(insertable = false)	// insert에 관여 안함
	private LocalDateTime updatedTime;
}
