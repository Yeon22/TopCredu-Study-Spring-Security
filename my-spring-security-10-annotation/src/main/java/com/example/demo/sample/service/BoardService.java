package com.example.demo.sample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import com.example.demo.sample.model.Board;

@Service
public class BoardService {
	private List<Board> boards = new ArrayList<Board>();

	{
		for (int i = 1; i <= 4; i++) {
			int zeroOrOne = i % 2;
			Board board = new Board(i, "writer" + zeroOrOne, "content" + zeroOrOne);
			boards.add(board);
		}
		
		Board board = new Board(5, "admin", "content");
		boards.add(board);
	}

	@PreFilter("filterObject.writer == 'writer0'") // 설정된 조건을 만족하는 데이터만 전달된다.
	public List<Board> testPreFilter(List<Board> boards) {
		return boards;
	}

	@PostFilter("filterObject.writer == 'writer1'") // 설정된 조건을 만족하는 데이터만 리턴된다.
	public List<Board> testPostFilter() {
		return boards;
	}
	
	@PreAuthorize("(hasRole('ROLE_MANAGER') and #board.getContent().length() > 5) or hasRole('ROLE_ADMIN')")
	public Board testPreAuthorize(Board board) {
		return board;
	}
	
	@PreAuthorize("authenticated")
	@PostAuthorize("returnObject.writer == principal.username")
	public Board testPostAuthorize() {
		Board board = new Board(1, "manager", "content");
		return board;
	}
}
