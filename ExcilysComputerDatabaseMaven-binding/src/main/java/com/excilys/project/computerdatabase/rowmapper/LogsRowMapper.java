package com.excilys.project.computerdatabase.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.project.computerdatabase.domain.Logs;
import com.excilys.project.computerdatabase.domain.Logs.LogsBuilder;

@SuppressWarnings("rawtypes")
public class LogsRowMapper implements RowMapper{
	
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Logs logs = new Logs(new LogsBuilder(rs.getLong("id"), rs.getString("description"), rs.getString("type"), rs.getDate("date_logs")));			
			return logs;
		}
}