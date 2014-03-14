package com.excilys.project.computerdatabase.persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager {

	private static boolean firstUsing = true;
	private static final String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String usr = "jee-cdb";
	private static final String pwd = "password";
	
	public static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	
	public static Connection getConnection(){
		
		/* Load jdbc driver one only time */
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
		
		/* Searching for connection */
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url,usr,pwd);
		} catch (SQLException e) {
			logger.error("Connection problem with JDBC ... ");
		}
		
		logger.info("Connection request complete");
		return conn;
	}
}
