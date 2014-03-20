package com.excilys.project.computerdatabase.services;

import java.sql.SQLException;
import java.util.List;

import com.excilys.project.computerdatabase.domain.Logs;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;
import com.excilys.project.computerdatabase.persistence.LogsDAO;

public class LogsServices {

	public static LogsServices instance = null;

	private LogsDAO logsDAO = LogsDAO.getInstance();

	public List<Logs> getAllLogs(){
		List<Logs> logs = null;
		try{
			ConnectionManager.INSTANCE.getConnection();
			ConnectionManager.INSTANCE.startTransaction();
			
			logs = logsDAO.retrieveAll();
			
			if(logs!=null){	  
				ConnectionManager.INSTANCE.commit(null);
			}else{
				ConnectionManager.INSTANCE.rollback(null);
			}
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(null);
			sqle.printStackTrace();
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}

		return logs;
	}

	public Logs getLog(long idLog){
		Logs log = null;

		try{
			ConnectionManager.INSTANCE.getConnection();
			ConnectionManager.INSTANCE.startTransaction();

			log = logsDAO.retrieveByLogId(idLog);

			if(log!=null){	  
				ConnectionManager.INSTANCE.commit(null);
			}else{
				ConnectionManager.INSTANCE.rollback(null);
			}
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(null);
			sqle.printStackTrace();
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}

		return log;
	}

	public void insert(String description, String type){
		try{
			ConnectionManager.INSTANCE.getConnection();
			ConnectionManager.INSTANCE.startTransaction();

			logsDAO.insert(description, type);

			ConnectionManager.INSTANCE.commit(null);

		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(null);
			sqle.printStackTrace();
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}
	}

	synchronized public static LogsServices getInstance(){
		if(instance == null){
			instance = new LogsServices();
		}
		return instance;
	}
}
