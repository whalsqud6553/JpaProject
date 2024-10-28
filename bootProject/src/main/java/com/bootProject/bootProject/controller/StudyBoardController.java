package com.bootProject.bootProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studyboard")
public class StudyBoardController {
	
	@GetMapping("/studyrecruit")
	public String studyRecruit() {
		
		
		return "/studyboard/studyrecruit";
	}
}
