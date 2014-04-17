package com.excilys.project.computerdatabase.persistence;

import java.sql.Types;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.project.computerdatabase.domain.Logs;
import com.excilys.project.computerdatabase.rowmapper.LogsRowMapper;

@Repository
public class LogsDAO {
	
	private static final String table = "logs";

	public static LogsDAO instance = null;
	synchronized public static LogsDAO getInstance(){
		if(instance == null){
			instance = new LogsDAO();
		}
		return instance;
	}
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public List<Logs> retrieveAll(){
		StringBuilder query = new StringBuilder("SELECT * FROM ").append(table);
		return jdbcTemplate.query(query.toString(), new LogsRowMapper());
	}

	public Logs retrieveByLogId(long idLog) {
		StringBuilder query = new StringBuilder("SELECT ca.* FROM company AS ca WHERE ca.id = ?");
		return (Logs) jdbcTemplate.queryForObject(query.toString(), new Object[] {idLog}, Logs.class);
	}

	public void insert(String description, String type) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ")
	     .append(table)
	     .append(" VALUES(0,?,?,LOCALTIME())");	
		Object[] params = {description,type};
		int[] types = {Types.VARCHAR, Types.VARCHAR};
		
		jdbcTemplate.update(query.toString(), params, types);

	}
}
