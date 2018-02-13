Spring Security

1. org.springframework.security.core.userdetails.UserDetailsService
개발자가 만든 DAO 클래스가 이미 존재할 때 이 로직을 스프링 시큐리티가 사용하도록 설정할 수 있다.

org.springframework.security.core.userdetails.UserDetailsService를 구현한 MyUserService 클래스를 만든다.
UserDetailsService 인터페이스는 UserDetails loadUserByUsername(String username) 메소드를 구현하도록 제안한다.

스프링 시큐리티는 로그인 폼에서 받은 username 문자열을 파라미터로 전달하고 org.springframework.security.core.userdetails.UserDetails 객체를 리턴받은 다음 이 정보를 사용한다.

2. com.example.demo.config.SecurityConfigurationJava 
설정 클래스에서 MyUserService의 객체를  userDetailsService 메소드를 이용하여 설정한다.

```
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
```

3. com.example.demo.security.custom.security.MyUserService

```
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		return new MyUser(user);
	}
```

userService.findByUsername 메소드가 리턴하는 객체는 개발자가 만든 User 객체가 된다.
이를 스프링 시큐리티가 사용하는 UserDetails 객체로 변경하여 전달할 필요가 있다.
MyUser 클래스는 org.springframework.security.core.userdetails.User 클래스를 상속하여 만든다.

4. com.example.demo.security.custom.security.MyUser

```
	public MyUser(User user) {
		super(user.getUsername(), user.getPassword(), authorities(user));
		this.user = user;
	}

	private static Collection<? extends GrantedAuthority> authorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRoles()) {
        	authorities.add(new SimpleGrantedAuthority(role.toString()));
		}
        return authorities;
    }
```

super(user.getUsername(), user.getPassword(), authorities(user)) 코드를 사용하여 사용자의 권한정보를 건네 준다.

5. Spring JDBC NamedParameterJdbcTemplate
사용자 인증정보 테이블과 권한정보 테이블이 분리되어 있는 상태에서 하나의 질의로 필요한 정보를 구하기 위해서 조인쿼리를 사용한다.

```
String sql = "select u.*, r.* from users u left outer join user_roles r on u.username=r.username where u.username=:username";
```

질의 결과를 User 객체의 멤버변수에 적절히 담기 위해서 로우매퍼 대신 ResultSetExtractor를 사용할 필요가 있다.

com.example.demo.security.custom.dao.UserRepositoryImpl

```
	private ResultSetExtractor<User> resultSetExtractor = new ResultSetExtractor<User>() {
		@Override
		public User extractData(ResultSet rs) throws SQLException, DataAccessException {
			User user = null;
			List<Role> roles = new ArrayList<>();
			
			while (rs.next()) {
				if (user == null) {
					user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setEnabled(rs.getBoolean("enabled"));
				}
				roles.add(Role.valueOf(rs.getString("role")));
			}
			
			user.setRoles(roles);
			return user;
		}
	};
```
