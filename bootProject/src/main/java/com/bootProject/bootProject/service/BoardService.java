package com.bootProject.bootProject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootProject.bootProject.dto.BoardCommentDTO;
import com.bootProject.bootProject.dto.BoardDTO;
import com.bootProject.bootProject.entity.BoardCommentEntity;
import com.bootProject.bootProject.entity.BoardEntity;
import com.bootProject.bootProject.repository.BoardCommentRepository;
import com.bootProject.bootProject.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	// 댓글 repository
	private final BoardCommentRepository boardCommentRepository;
	
	public void save(BoardDTO boardDTO) {
		BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
		boardRepository.save(boardEntity);
	}

	public List<BoardDTO> findAll() {
		List<BoardEntity> entityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		List<BoardDTO> dtoList = new ArrayList<BoardDTO>();
		for(BoardEntity boardEntity : entityList) {
			dtoList.add(BoardDTO.toBoardDTO(boardEntity));
		}
		return dtoList;
	}
	
	@Transactional
	public void updateHits(Long id) {
		boardRepository.updateHits(id);
		
	}

	public BoardDTO findById(Long id) {
		Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
		if(optionalBoardEntity.isPresent()) {
			BoardEntity boardEntity = optionalBoardEntity.get();
			BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
			return boardDTO;
		}
		else {
			return null;
		}
		
	}

	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public int boardModifyById(BoardDTO board) {
		BoardEntity entity = BoardEntity.toSaveEntity(board);
		int row = boardRepository.updateBoard(entity);
		
		return row;
	}

	// 페이징
	public Page<BoardDTO> paging(Pageable pageable) {
		int page = pageable.getPageNumber() - 1;	// 0부터 시작하기 때문에 -1
		int pageSize = 10;	// 한 페이지 글 개수
		Page<BoardEntity> boardEntities = 
				boardRepository.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id")));
		
		// boardEntity 객체를 boardDTO객체로 바꾸는법
		// 목록 
		Page<BoardDTO> boardDTO = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
		
		return boardDTO;
	}

	
	// 댓글
	public Long boardCommentInsert(BoardCommentDTO boardCommentDTO) {
		Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardCommentDTO.getBoardId());
		if(optionalBoardEntity.isPresent()) {
			BoardEntity boardEntity = optionalBoardEntity.get();
			BoardCommentEntity entity = BoardCommentEntity.toBoardCommentyEntity(boardCommentDTO, boardEntity);
			
			return boardCommentRepository.save(entity).getId();
		}
		else {
			return null;
		}
	}
	
	public List<BoardCommentDTO> commentFindAll(Long id) {
		Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
		BoardEntity boardEntity = optionalBoardEntity.get();
		
		List<BoardCommentEntity> commentList = boardCommentRepository.findByBoardEntityOrderByIdDesc(boardEntity);
		List<BoardCommentDTO> commentDTOList = new ArrayList<BoardCommentDTO>();
		for(BoardCommentEntity comment : commentList) {
			commentDTOList.add(BoardCommentDTO.toBoardCommentDTO(comment));
		}
		return commentDTOList;
	}

	public void boardCommentDelete(Long id) {
		boardCommentRepository.deleteById(id);
	}

}
