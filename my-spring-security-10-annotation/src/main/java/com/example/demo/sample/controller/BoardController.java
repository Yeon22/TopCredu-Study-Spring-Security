package com.example.demo.sample.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.sample.model.Board;
import com.example.demo.sample.service.BoardService;

@RestController
@RequestMapping("/boards")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@GetMapping("/testPreFilter")
	public Object testPreFilter() {
		List<Board> boards = new ArrayList<Board>();
		for (int i = 1; i <= 4; i++) {
			int zeroOrOne = i % 2;
			Board board = new Board(i, "writer" + zeroOrOne, "content" + zeroOrOne);
			boards.add(board);
		}
		System.out.println(boards);
		
		return boardService.testPreFilter(boards);
	}

	@GetMapping("/testPostFilter")
	public Object testPostFilter() {
		return boardService.testPostFilter();
	}
	
	@GetMapping("/testPreAuthorize")
	public Object testPreAuthorize(@RequestParam(value="content", required=false, defaultValue="123456") String content) {
		Board board = new Board(1, "writer", content);
		System.out.println(board);
		return boardService.testPreAuthorize(board);
	}
	
	@GetMapping("/testPostAuthorize")
	public Object testPostAuthorize() {
		return boardService.testPostAuthorize();
	}
}
