package com.excilys.project.computerdatabase.persistence;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.project.computerdatabase.services.LogsServices;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

@Component
public class ConnectionManager {

	private static final String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String usr = "jee-cdb";
	private static final String pwd = "password";

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
	
	public BoneCP connectionPool = null;
	public ThreadLocal<Connection> tlc = null;
	Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

	ConnectionManager(){
		tlc = new ThreadLocal<Connection>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error("JDBC Driver loading error.");
		}

		try {
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(url);
			config.setUsername(usr); 
			config.setPassword(pwd);
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			connectionPool = new BoneCP(config);
		} catch (SQLException e) {
			logger.error("BoneCP config error.");
		}
	} 

	public Connection getConnection(){

		if(tlc.get() == null){
			try {
				tlc.set(connectionPool.getConnection());
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
