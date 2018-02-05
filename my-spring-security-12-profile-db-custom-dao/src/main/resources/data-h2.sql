INSERT INTO users(username, password, enabled) VALUES ('user', 'user', true);
INSERT INTO users(username, password, enabled) VALUES ('manager', 'manager', true);
INSERT INTO users(username, password, enabled) VALUES ('admin', 'admin', true);

INSERT INTO user_roles(username, role) VALUES ('user', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('manager', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('manager', 'ROLE_MANAGER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_MANAGER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');

--Unique index or primary key violation: "PRIMARY_KEY_4 ON PUBLIC.USERS(USERNAME) VALUES ('user', 1)"; SQL statement: