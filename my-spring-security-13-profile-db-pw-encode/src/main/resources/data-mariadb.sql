--INSERT INTO users(username, password, enabled) VALUES ('user', 'user', true);
--INSERT INTO users(username, password, enabled) VALUES ('manager', 'manager', true);
--INSERT INTO users(username, password, enabled) VALUES ('admin', 'admin', true);
--
--INSERT INTO user_roles(username, role) VALUES ('user', 'ROLE_USER');
--INSERT INTO user_roles(username, role) VALUES ('manager', 'ROLE_USER');
--INSERT INTO user_roles(username, role) VALUES ('manager', 'ROLE_MANAGER');
--INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_USER');
--INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_MANAGER');
--INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');

--쿼리가 아예 없으면 에러가 발생하기 때문에 다음 쿼리를 배치한다.
select 1;

--패스워드가 암호화 된 후 입력되지 않는다. 따라서, 대신 com.example.demo.TestDataInit 클래스를 만들었다.