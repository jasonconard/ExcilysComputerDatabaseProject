package com.excilys.project.computerdatabase.persistence;
import java.sql.Connection;
import java.sql.SQLException;

import com.excilys.project.computerdatabase.services.LogsServices;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public enum ConnectionManager {
	INSTANCE;
	
	private static final String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String usr = "jee-cdb";
	private static final String pwd = "password";
	
	public BoneCP connectionPool = null;
	public ThreadLocal<Connection> tlc = null;
	public ThreadLocal<Boolean> tlb = null;
	private LogsServices logsServices = LogsServices.getInstance();
	
	ConnectionManager(){
		tlc = new ThreadLocal<Connection>();
		tlb = new ThreadLocal<Boolean>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
	} 
	
	public Connection getConnection(){
		
		if(tlc.get() == null){
			try {
				tlc.set(connectionPool.getConnection());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return tlc.get();
	}
	
	public void closeConnection(){
		if(tlc.get() != null){
			try {
				tlc.get().close();
				tlc.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void startTransaction(){
		if(tlc.get() != null){
			try {
				tlc.get().setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
			}
		}
	}
	
}
