Spring Security

1. 프로파일링

spring.profiles.active=dev 설정일 때 application-dev.properties를 사용한다. 
개발 시 H2 데이터베이스를 사용한다.

spring.profiles.active=prod 설정일 때 application-prod.properties를 사용한다.
배포 시 mariadb 데이터베이스를 사용한다.
