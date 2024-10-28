package com.bootProject.bootProject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bootProject.bootProject.dto.BoardCommentDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "board_comment_table")
@SequenceGenerator(name = "boardComment_seq_generator", sequenceName = "boardComment_seq", initialValue = 1, allocationSize = 1)
public class BoardCommentEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boardComment_seq_generator")
	private Long id;
	
	@Column(length = 20, nullable = false)
	private String commentWriter;
	
	@Column(length = 500, nullable = false)
	private String commentContent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private BoardEntity boardEntity;
	
	public static BoardCommentEntity toBoardCommentyEntity(BoardCommentDTO boardCommentDTO, BoardEntity boardEntity) {
		BoardCommentEntity entity = new BoardCommentEntity();
		entity.setId(boardCommentDTO.getId());
		entity.setCommentWriter(boardCommentDTO.getCommentWriter());
		entity.setCommentContent(boardCommentDTO.getCommentContent());
		entity.setBoardEntity(boardEntity);
		return entity;
	}
}
