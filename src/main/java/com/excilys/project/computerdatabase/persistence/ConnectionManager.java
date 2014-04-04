package com.excilys.project.computerdatabase.persistence;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.project.computerdatabase.services.LogsServices;

@Component
public class ConnectionManager {

	public static ConnectionManager instance = null;
	synchronized public static ConnectionManager getInstance(){
		if(instance == null){
			instance = new ConnectionManager();
		}
		return instance;
	}

	@Autowired
	LogsServices logsServices;
	public void setLogsServices(LogsServices logsServices){
		this.logsServices = logsServices;
	}
	
	@Autowired
	DataSource ds;

	public ThreadLocal<Connection> tlc = null;
	Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

	ConnectionManager(){
		tlc = new ThreadLocal<Connection>();
	} 

	public Connection getConnection(){

		if(tlc.get() == null){
			try {
				tlc.set(ds.getConnection());
			} catch (SQLException e) {
				logger.error("Connection Pool connection retrieving error.");
			}
		}
		try {
			tlc.get().setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("Set Auto commit to false error.");
		}
		return tlc.get();
	}

	public void closeConnection(){
		if(tlc.get() != null){
			try {
				tlc.get().close();
				tlc.remove();
			} catch (SQLException e) {
				logger.error("Connection closing error.");
			}
		}
	}

	public void rollback(String message){
		if(tlc.get() != null){
			try {
				tlc.get().rollback();
				if(message!=null){
					logsServices.insert(message+" failed", "Error");
				}
			} catch (SQLException e) {
				logger.error("Connection rollback error.");
			}
		}
	}

	public void commit(String message){
		if(tlc.get() != null){
			try {
				tlc.get().commit();
				tlc.get().setAutoCommit(true);
				if(message!=null){
					logsServices.insert(message+" successful", "Complete");
				}
			} catch (SQLException e) {
				logger.error("Connection commit error.");
			}
		}
	}

}
