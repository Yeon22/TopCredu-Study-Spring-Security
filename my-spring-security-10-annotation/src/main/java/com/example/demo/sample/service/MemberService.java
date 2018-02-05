package com.example.demo.sample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.sample.model.Member;

@Service
public class MemberService {
	private List<Member> members = new ArrayList<Member>();

	{
		for (int i = 1; i <= 4; i++) {
			Member member = new Member(i, "id" + i, "pw" + i, true);
			members.add(member);
		}
	}

	@Secured(value = { "ROLE_ADMIN", "ROLE_MANAGER" })
	public Member insert(Member member) {
		member.setId(members.size() + 1);
		members.add(member);
		return member;
	}

	@Secured(value = { "ROLE_ADMIN", "ROLE_MANAGER" })
	public int delete(final int id) {
		Optional<Member> matchingObject = members.stream().filter(new Predicate<Member>() {
			@Override
			public boolean test(Member member) {
				return member.getId() == id;
			}
		}).findFirst();
		Member member = matchingObject.get();
		members.remove(member);
		return 1;
	}

	@RolesAllowed(value = { "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	public int count() {
		return members.size();
	}

	@PreAuthorize("authenticated")
//	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public List<Member> select() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		
		return members;
	}

}
