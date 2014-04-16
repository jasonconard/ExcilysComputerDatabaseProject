package com.excilys.project.computerdatabase.persistence;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.project.computerdatabase.domain.Logs;
import com.jolbox.bonecp.BoneCPDataSource;

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
	BoneCPDataSource ds;

	public List<Logs> retrieveAll(){
		List<Logs> logs = new ArrayList<Logs>();
		
		try {
	
			Connection connection = ds.getConnection();
			
			StringBuilder query = new StringBuilder();
			
			query.append("SELECT * FROM ")
			     .append(table);
	
			ResultSet results = null;
			PreparedStatement preparedStatement = null;
	
			preparedStatement = connection.prepareStatement(query.toString());
			results = preparedStatement.executeQuery();
	
			while(results.next()){
				long id = results.getLong("id");
				String description = results.getString("description");
				String type = results.getString("type");
				Date dateLogs = results.getDate("date_logs");
				logs.add(new Logs.LogsBuilder(id,description,type,dateLogs).build());
			}
	
			closeAll(results,preparedStatement);
			connection.close();
		}catch (SQLException e) {}
		
		return logs;
	}

	public Logs retrieveByLogId(long idLog) {
		Logs log = null;

		try{
		
			Connection connection = ds.getConnection();
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT ca.* FROM company AS ca WHERE ca.id = ")
			     .append(idLog);
	
			ResultSet results = null;
			PreparedStatement preparedStatement = null;
	
	
			preparedStatement = connection.prepareStatement(query.toString());
			results = preparedStatement.executeQuery();
	
			if(results.next()){
				long id = results.getLong("id");
				String description = results.getString("description");
				String type = results.getString("type");
				Date dateLogs = results.getDate("date_logs");
				log =new Logs.LogsBuilder(id,description,type,dateLogs).build();
			}
	
			closeAll(results,preparedStatement);
			connection.close();
		}catch (SQLException e) {}
		
		return log;
	}

	public void insert(String description, String type) {
		try{
		
			Connection connection = ds.getConnection();
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO ")
			     .append(table)
			     .append(" VALUES(0,?,?,LOCALTIME())");
			
	
			PreparedStatement preparedStatement = null;
	
			preparedStatement = connection.prepareStatement(query.toString());
	
			preparedStatement.setString(1, description);
			preparedStatement.setString(2, type);
	
			preparedStatement.executeUpdate();
	
			closeAll(null,preparedStatement);
			connection.close();
		}catch (SQLException e) {}
	}

	private void closeAll(ResultSet rs, PreparedStatement ps) throws SQLException{

		if(rs!=null){
			rs.close();
		}
		if(ps!=null){
			ps.close();
		}
	}
}