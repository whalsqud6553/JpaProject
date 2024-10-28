package com.bootProject.bootProject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bootProject.bootProject.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	
	@Modifying
	@Query(value = "update BoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
	void updateHits(@Param("id") Long id);
	
	@Modifying
	@Query(value = "UPDATE BoardEntity b SET b.boardTitle = :#{#board.boardTitle}, b.boardContents = :#{#board.boardContents} WHERE b.id = :#{#board.Id}")
	int updateBoard(@Param("board") BoardEntity board);
	
}
