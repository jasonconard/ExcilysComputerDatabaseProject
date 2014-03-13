package com.excilys.project.computerdatabase.persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static boolean firstUsing = true;
	private static final String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String usr = "jee-cdb";
	private static final String pwd = "password";
	
	public static Connection getConnection(){
		
		/* Load jdbc driver one only time */
		if(firstUsing){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				firstUsing = false;
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		/* Searching for connection */
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url,usr,pwd);
		} catch (SQLException e) {
			System.err.println("Connection problem with JDBC ... ");
		}
		return conn;
	}
}
