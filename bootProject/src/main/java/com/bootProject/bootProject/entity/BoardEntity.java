package com.bootProject.bootProject.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bootProject.bootProject.dto.BoardDTO;

import lombok.Getter;
import lombok.Setter;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "board_table")
@SequenceGenerator(name = "board_seq_generator", sequenceName = "board_seq", initialValue = 1, allocationSize = 1)
public class BoardEntity extends BaseEntity {
	@Id // pk 컬럼
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_generator")
	private Long id;
	
	@Column(length = 20, nullable = false) // 크기 20, not null
	private String boardWriter;
	
	@Column // 크기 255, null 가능
	private String boardPass;
	
	@Column(nullable = false)
	private String boardTitle;
	
	@Column(length = 500, nullable = false)
	private String boardContents;
	
	@Column
	private int boardHits;
	
	@OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<BoardCommentEntity> boardCommentEntityList = new ArrayList<>();
	
	public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setId(boardDTO.getId());
		boardEntity.setBoardWriter(boardDTO.getBoardWriter());
		boardEntity.setBoardPass(boardDTO.getBoardPass());
		boardEntity.setBoardTitle(boardDTO.getBoardTitle());
		boardEntity.setBoardContents(boardDTO.getBoardContents());
		boardEntity.setBoardHits(0);
		return boardEntity;
	}
}
