Spring Security

1. application.properties
프로파일을 적용하면 여러 설정을 미리 해 놓고 상황에 따라서 적용할 수 있다.

```
spring.profiles.active=xml
#spring.profiles.active=javaconfig
```

위 설정에 따라서 활성화 된 프로파일은 xml 이다.

1.1. 프로파일이 xml 일 때

```
@Profile("xml")
@Configuration
@ImportResource({"classpath:spring/root-context.xml","classpath:spring/security.xml"})
public class SecurityConfiguration {

}
```

@Profile("xml") 애노테이션 설정에 따라 프로파일이 xml일 때 SecurityConfiguration 클래스가 사용되고 이에 따라서 root-context.xml, security.xml 파일에 설정한 내역이 적용된다.

1.2. 프로파일이 javaconfig 일 때

```
@Profile("javaconfig")
@Configuration
@EnableWebSecurity
public class SecurityConfigurationJava extends WebSecurityConfigurerAdapter {
	// 코드 생략
}
```

@Profile("javaconfig") 애노테이션 설정에 따라 프로파일이 javaconfig일 때 SecurityConfigurationJava 클래스가 사용되고
이에 따라서 SecurityConfigurationJava 클래스 파일에 설정한 내역이 적용된다.

2. 사용자 정보를 데이터베이스에 저장하기위한 설정

2.1. XML 설정

root-context.xml

```
	<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.Driver" />
		<property name="url" value="jdbc:mysql://localhost:3306/spring_security?createDatabaseIfNotExist=true" />
		<property name="username" value="root" />
		<property name="password" value="1111" />
	</bean>
```

security.xml

```
	<authentication-manager>
		<authentication-provider>
			<jdbc-user-service data-source-ref="dataSource" 
				users-by-username-query="select username, password, enabled from users where username=?"
				authorities-by-username-query="select username, role from user_roles where username=?" />
		</authentication-provider>
	</authentication-manager>
```

로그인 폼에서 ID/Passowrd를 POST 하면 스프링 시큐리티는 users-by-username-query의 쿼리를 사용하여 정보를 확인한다.
인증과정이 통과되면 사용자의 권한을 부여하기 위해서 authorities-by-username-query의 쿼리를 사용한다.

2.2. Java-config 설정

com.example.demo.config.SecurityConfigurationJava

```
	@Bean
    DataSource dataSource() throws SQLException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/spring_security?createDatabaseIfNotExist=true");
		dataSource.setUsername("root");
		dataSource.setPassword("1111");
		return dataSource;
    }
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select username, password, enabled from users where username=?")
			.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
	}
```

3. DDL/DML

schema.sql

```
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE users(
	username VARCHAR(45) NOT NULL ,
	password VARCHAR(45) NOT NULL ,
	enabled TINYINT NOT NULL DEFAULT 1 ,
	PRIMARY KEY (username)
);
  
CREATE TABLE user_roles(
	user_role_id int(11) NOT NULL AUTO_INCREMENT,
	username varchar(45) NOT NULL,
	role varchar(45) NOT NULL,
	PRIMARY KEY (user_role_id),
	UNIQUE KEY uni_username_role (role,username),
	KEY fk_username_idx (username),
	CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
);
```

data.sql

```
INSERT INTO users(username, password, enabled) VALUES ('user', 'user', true);
INSERT INTO users(username, password, enabled) VALUES ('manager', 'manager', true);
INSERT INTO users(username, password, enabled) VALUES ('admin', 'admin', true);

INSERT INTO user_roles(username, role) VALUES ('user', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('manager', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('manager', 'ROLE_MANAGER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_MANAGER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');
```
