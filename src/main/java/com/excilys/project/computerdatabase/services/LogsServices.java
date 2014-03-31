package com.excilys.project.computerdatabase.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.project.computerdatabase.domain.Logs;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;
import com.excilys.project.computerdatabase.persistence.LogsDAO;

@Service
public class LogsServices {
	
	public static LogsServices instance = null;
	synchronized public static LogsServices getInstance(){
		if(instance == null){
			instance = new LogsServices();
		}
		return instance;
	}
	
	@Autowired
	ConnectionManager connectionManager;
	public void setConnectionManager(ConnectionManager connectionManager){
		this.connectionManager = connectionManager;
	}
	
	@Autowired
	LogsDAO logsDAO;
	public void setLogsDAO(LogsDAO logsDAO){
		this.logsDAO = logsDAO;
	}

	public List<Logs> getAllLogs(){
		List<Logs> logs = null;
		try{
			connectionManager.getConnection();
			
			logs = logsDAO.retrieveAll();
			
			if(logs!=null){	  
				connectionManager.commit(null);
			}else{
				connectionManager.rollback(null);
			}
		}catch(SQLException sqle){
			connectionManager.rollback(null);
			sqle.printStackTrace();
		}finally{
			connectionManager.closeConnection();
		}

		return logs;
	}

	public Logs getLog(long idLog){
		Logs log = null;

		try{
			connectionManager.getConnection();

			log = logsDAO.retrieveByLogId(idLog);

			if(log!=null){	  
				connectionManager.commit(null);
			}else{
				connectionManager.rollback(null);
			}
		}catch(SQLException sqle){
			connectionManager.rollback(null);
			sqle.printStackTrace();
		}finally{
			connectionManager.closeConnection();
		}

		return log;
	}

	public void insert(String description, String type){
		try{
			connectionManager.getConnection();

			logsDAO.insert(description, type);

			connectionManager.commit(null);

		}catch(SQLException sqle){
			connectionManager.rollback(null);
			sqle.printStackTrace();
		}finally{
			connectionManager.closeConnection();
		}
	}

}
