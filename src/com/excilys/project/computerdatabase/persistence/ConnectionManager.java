package com.excilys.project.computerdatabase.persistence;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionManager {

	private static boolean firstUsing = true;
	private static boolean firstBoneCP = true;
	private static final String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String usr = "jee-cdb";
	private static final String pwd = "password";
	
	public static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	
	public static BoneCP connectionPool = null;
	
	/*public static Connection getConnection(){
		// Load jdbc driver one only time
		if(firstUsing){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				logger.info("JDBC driver loaded");
				firstUsing = false;
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				logger.error("JDBC driver loading error");
			}
		}
		
		// Searching for connection
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url,usr,pwd);
		} catch (SQLException e) {
			logger.error("Connection problem with JDBC ... ");
		}
		
		logger.info("Connection request complete");
		return conn;
	}*/
	
	public static Connection getConnection(){
		if(firstUsing){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			logger.info("JDBC driver loaded");
			firstUsing = false;
		}
		
		if(firstBoneCP){
			try {
				// setup the connection pool
				BoneCPConfig config = new BoneCPConfig();
				config.setJdbcUrl(url);
				config.setUsername(usr); 
				config.setPassword(pwd);
				config.setMinConnectionsPerPartition(5);
				config.setMaxConnectionsPerPartition(10);
				config.setPartitionCount(1);
				connectionPool = new BoneCP(config);
				firstBoneCP = false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		Connection connection = null;	
		try {
			connection = connectionPool.getConnection();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return connection;
	}
	
}
