package com.bootProject.bootProject.dto;

import java.time.LocalDateTime;

import com.bootProject.bootProject.entity.BoardEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString // 필드 값 확인할 때
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
	private Long id;
	private String boardWriter;
	private String boardPass;
	private String boardTitle;
	private String boardContents;
	private int boardHits; // 조회수
	private LocalDateTime boardCreatedTime;
	private LocalDateTime boardUpdatedTime;

	public BoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
		super();
		this.id = id;
		this.boardWriter = boardWriter;
		this.boardTitle = boardTitle;
		this.boardHits = boardHits;
		this.boardCreatedTime = boardCreatedTime;
	}
	
	public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setId(boardEntity.getId());
		boardDTO.setBoardWriter(boardEntity.getBoardWriter());
		boardDTO.setBoardPass(boardEntity.getBoardPass());
		boardDTO.setBoardTitle(boardEntity.getBoardTitle());
		boardDTO.setBoardContents(boardEntity.getBoardContents());
		boardDTO.setBoardHits(boardEntity.getBoardHits());
		boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
		boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
		
		return boardDTO;
	}
}
