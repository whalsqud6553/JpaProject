package com.bootProject.bootProject.dto;

import java.time.LocalDateTime;

import com.bootProject.bootProject.entity.BoardCommentEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardCommentDTO {

	private Long Id;
	private String commentWriter;
	private String commentContent;
	private LocalDateTime commentCreateTime;
	private Long boardId;
	
	public static BoardCommentDTO toBoardCommentDTO(BoardCommentEntity boardCommentEntity) {
		BoardCommentDTO dto = new BoardCommentDTO();
		dto.setId(boardCommentEntity.getId());
		dto.setCommentWriter(boardCommentEntity.getCommentWriter());
		dto.setCommentContent(boardCommentEntity.getCommentContent());
		dto.setCommentCreateTime(boardCommentEntity.getCreatedTime());
		dto.setBoardId(boardCommentEntity.getBoardEntity().getId());
		
		return dto;
	}
}
