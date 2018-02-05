package com.example.demo.security.custom.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.security.custom.model.Role;
import com.example.demo.security.custom.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
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

	
	@Override
	public User findByUsername(String username) {
		String sql = "select u.*, r.* from users u left outer join user_roles r on u.username=r.username where u.username=:username";
		
		Map<String, Object> paramMap = new HashMap<String, Object>(1);
		paramMap.put("username", username);
		
		return jdbcTemplate.query(sql, paramMap, resultSetExtractor);
	}

}
