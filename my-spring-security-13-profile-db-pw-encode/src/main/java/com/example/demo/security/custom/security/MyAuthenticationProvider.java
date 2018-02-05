package com.example.demo.security.custom.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private MyUserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * The method authenticate() performs authentication and returns a fully authenticated object including credentials. 
	 * It may return null if the AuthenticationProvider is unable to support authentication of the passed Authentication object. 
	 * In such a case, the next AuthenticationProvider that supports the presented Authentication class will be attempted. 
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("@@@ MyAuthenticationProvider # authenticate() called.");
		
		String username = authentication.getName();
		Object credentials = authentication.getCredentials();
        System.out.println("@@@ credentials class: " + credentials.getClass());
        
        if (!(credentials instanceof String)) {
            return null;
        }
        String password = credentials.toString();
		
		User user = null;
		Collection<GrantedAuthority> authorities = null;

		try {
			user = (User) userService.loadUserByUsername(username);

			if (!passwordEncoder.matches(password, user.getPassword())) {
				throw new BadCredentialsException("Invalid Password");
			}

			authorities = user.getAuthorities();
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(e.getMessage());
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		/*
		 * 첫 번째 파라미터에 user 대신 username을 설정하는 예제가 인터넷에 많이 보인다.
		 * 그렇게 사용하면 JSP에서 pricipal 객체를 사용할 수 없게 된다.
		 */
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	/*
	 * The method supports() returns true if this AuthenticationProvider supports the indicated Authentication object 
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
