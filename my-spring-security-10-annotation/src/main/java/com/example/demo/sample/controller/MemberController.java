package com.example.demo.sample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.sample.model.Member;
import com.example.demo.sample.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@GetMapping("/all")
	public List<Member> select() {
		return memberService.select();
	}

	@GetMapping("/insert")
	public Member insert() {
		Member member = new Member();
		member.setId(0);
		member.setUsername("XX");
		member.setPassword("XX");
		member.setEnabled(true);
		
		return memberService.insert(member);
	}

	@GetMapping("/delete/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable(value="id", required=true) int id) {
		memberService.delete(id);
	}

	@GetMapping("/all/size")
	public Object count() {
		Map<String, Integer> params = new HashMap<>();
		params.put("size", memberService.count());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<Map<String, Integer>>(params, headers, HttpStatus.OK);
	}

}
