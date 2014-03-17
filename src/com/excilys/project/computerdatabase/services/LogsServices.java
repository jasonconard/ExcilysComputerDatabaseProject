package com.excilys.project.computerdatabase.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.project.computerdatabase.domain.Logs;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;
import com.excilys.project.computerdatabase.persistence.LogsDAO;

public class LogsServices {

	public static LogsServices instance = null;

	private LogsDAO logsDAO = LogsDAO.getInstance();

	public List<Logs> getAllLogs(){
		Connection connection = null;
		List<Logs> logs = null;
		try{
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);

			//traitement des différentes instructions composant la transaction
			logs = logsDAO.retrieveAll(connection);
			if(logs!=null){	  
				connection.commit(); // c'est ici que l'on valide la transaction
				connection.setAutoCommit(true);
			}else{
				connection.rollback();
			}
		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
		}catch(Exception e){
			try{connection.rollback();}catch(Exception e1){}
		}finally{
			try{connection.close();}catch(Exception e){}
		}

		return logs;
	}

	public Logs getLog(long idLog){
		Connection connection = null;
		Logs log = null;

		try{
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);

			log = logsDAO.retrieveByLogId(idLog, connection);

			if(log!=null){	  
				connection.commit();
				connection.setAutoCommit(true);
			}else{
				connection.rollback();
			}
		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
		}catch(Exception e){
			try{connection.rollback();}catch(Exception e1){}
		}finally{
			try{connection.close();}catch(Exception e){}
		}

		return log;
	}

	public void insert(String description, String type){
		Connection connection = null;

		try{
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);

			logsDAO.insert(description, type, connection);

			connection.commit();
			connection.setAutoCommit(true);

		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
		}catch(Exception e){
			try{connection.rollback();}catch(Exception e1){}
		}finally{
			try{connection.close();}catch(Exception e){}
		}
	}

	synchronized public static LogsServices getInstance(){
		if(instance == null){
			instance = new LogsServices();
		}
		return instance;
	}
}
