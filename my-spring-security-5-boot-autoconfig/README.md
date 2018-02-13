Spring Security

1. 시큐리티 설정은 Java 파일에 한다.
com.example.demo.config.MySecurityConfiguration

@EnableWebSecurity 애노테이션을 적용하면 스프링 시큐리티 오토 커피규레이션이 적용된다.
즉, 다른 설정 클래스 파일을 필요없다.

2. 사용자 계정정보는 Java 파일에 한다.
com.example.demo.config.MySecurityConfiguration


3. 로그인 화면은 개발자가 제공하는 것을 사용한다.
com.example.demo.config.MySecurityConfiguration
