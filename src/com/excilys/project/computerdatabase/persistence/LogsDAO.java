package com.excilys.project.computerdatabase.persistence;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.project.computerdatabase.domain.Logs;

public class LogsDAO {
	
	private static final String table = "logs";
	
	public static LogsDAO instance = null;
	public static Logger logger = LoggerFactory.getLogger(LogsDAO.class);
	
	public List<Logs> retrieveAll(Connection connection){
		
		List<Logs> logs = new ArrayList<Logs>();
		
		String query = "SELECT * FROM "+table;
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			results = preparedStatement.executeQuery(query);
			
			while(results.next()){
				long id = results.getLong("id");
				String description = results.getString("description");
				String type = results.getString("type");
				Date dateLogs = results.getDate("date_logs");
				logs.add(new Logs.LogsBuilder(id,description,type,dateLogs).build());
			}
			
		} catch (SQLException e) {
			logger.error("Retrieve All Logs error. SQL query : "+query);
		} finally{
			logger.info("Retrieve All Logs complete !");
			closeAll(results,preparedStatement);
		}
		
		return logs;
	}
	
	public Logs retrieveByLogId(long idLog, Connection connection){
		
		Logs log = null;
		
		String query = "SELECT ca.* FROM company AS ca WHERE ca.id = "+idLog;
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			results = preparedStatement.executeQuery();
			
			if(results.next()){
				long id = results.getLong("id");
				String description = results.getString("description");
				String type = results.getString("type");
				Date dateLogs = results.getDate("date_logs");
				log =new Logs.LogsBuilder(id,description,type,dateLogs).build();
			}
			
		} catch (SQLException e) {
			logger.error("Retrieve Log By Id error. SQL query : "+query);
		} finally{
			logger.info("Retrieve Log By Id complete !");
			closeAll(results,preparedStatement);
		}
		
		return log;
	}
	
	public void insert(String description, String type, Connection connection){
		String query = "INSERT INTO "+table+" VALUES(0,?,?,now())";
		String visualQuery = "INSERT INTO "+table+" VALUES(0,'"+description+"','"+type+"',now())";
		
		PreparedStatement preparedStatement = null;
		
		try{
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, description);
			preparedStatement.setString(2, type);
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("Insert logs error. SQL query  : "+visualQuery);
		} finally{
			logger.info("Insert logs complete !");
			closeAll(null,preparedStatement);
		}
	}
	
	private void closeAll(ResultSet rs, PreparedStatement ps){
		try {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			logger.info("Every connections closed !");
		} catch (SQLException e) {
			logger.error("Connections closing failed.");
			e.printStackTrace();
		}
	}
	
	synchronized public static LogsDAO getInstance(){
		if(instance == null){
			instance = new LogsDAO();
		}
		return instance;
	}
	
}
