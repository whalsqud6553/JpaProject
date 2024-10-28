package com.bootProject.bootProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootProject.bootProject.entity.BoardCommentEntity;
import com.bootProject.bootProject.entity.BoardEntity;

public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Long> {
	
	// select * from board_comment_table where board_id = ? order by id desc;
	List<BoardCommentEntity> findByBoardEntityOrderByIdDesc(BoardEntity boardEntity);

}
