package com.bootProject.bootProject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bootProject.bootProject.dto.BoardCommentDTO;
import com.bootProject.bootProject.dto.BoardDTO;
import com.bootProject.bootProject.dto.MemberDTO;
import com.bootProject.bootProject.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor	// 초기화 되지않은 fianl필드나 @NonNull 이 불은 필드에 대해 생성자를 생성해줌(@Autowired를 사용하지 않고 의존성 주입)
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/save")
	public String save() {
		return "/board/save";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute BoardDTO boardDTO, HttpSession session) {
		MemberDTO user = (MemberDTO) session.getAttribute("user");
		boardDTO.setBoardWriter(user.getUserId());
		boardService.save(boardDTO);
		
		return "redirect:/board/list";
	}
	
	// /board/list?page=1
	@GetMapping("/list")
	public String findAll(@PageableDefault(page = 1) Pageable pageable, Model model) {
//		pageable.getPageNumber();
		Page<BoardDTO> boardList = boardService.paging(pageable);
		int blockLimit = 5;
		int startPage = ((((int)Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;	// 1 4 7 10
		int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("startPage" , startPage);
		model.addAttribute("endPage", endPage);
		
		return "/board/list";
	}
	
	@GetMapping("/list/{id}")
	public String findById(@PathVariable("id") Long id, Model model,
							@PageableDefault(page=1) Pageable pageable) {
		BoardDTO boardDTO = boardService.findById(id);
		boardService.updateHits(id);
		
		List<BoardCommentDTO> commentList = boardService.commentFindAll(id);
		
		model.addAttribute("board", boardDTO);
		model.addAttribute("commentList", commentList);
		model.addAttribute("page", pageable.getPageNumber());
		return "/board/detail";
	}
	
	@GetMapping("/boardModify/{id}")
	public String boardModify(@PathVariable("id") Long id, Model model) {
		BoardDTO board = boardService.findById(id);
		model.addAttribute("board", board);
		
		return "/board/boardModify";
	}
	
	@PostMapping("/boardModify/{id}")
	public String boardModify(BoardDTO board) {
		int row = boardService.boardModifyById(board);
		if(row != 1) {
			return "/board/boardModify_msg";
		}
		else {
			return "redirect:/board/list/" + board.getId();
		}
	}
	
	@GetMapping("/boardDelete/{id}")
	public String boardDelete(@PathVariable("id") Long id, HttpSession session) {
		if(session.getAttribute("user") == null) {
			return "/board/boardDelete_msg";
		}
		else {
			MemberDTO user = (MemberDTO)session.getAttribute("user");
			BoardDTO writer = boardService.findById(id);
			if(user.getUserId().equals(writer.getBoardWriter())) {
				boardService.deleteBoard(id);
				
				return "redirect:/board/list";
			}
			else {
				return "/board/boardDelete_msg";
			}
		}
		
	}
	
	// 댓글
	@PostMapping("/comment/{id}")
	public String boardCommentInsert(@PathVariable("id") Long id, BoardCommentDTO boardCommentDTO, HttpSession session) {
		MemberDTO user = (MemberDTO) session.getAttribute("user");
		if(user != null) {
			boardCommentDTO.setCommentWriter(user.getUserId());
			boardCommentDTO.setBoardId(id);
			Long saveResult = boardService.boardCommentInsert(boardCommentDTO);
			if(saveResult == null) {
				return "작성 실패";
			}
			
		}
		return "redirect:/board/list/" + id;
	}
	
	@GetMapping("/commentDelete/{commentId}/{boardId}")
	public String commentDelete(@PathVariable("commentId") Long id, @PathVariable("boardId") Long boardId) {
		boardService.boardCommentDelete(id);
		return "redirect:/board/list/" + boardId;
	}
}
