package com.excilys.project.computerdatabase.persistence;
import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionManager {

	private static boolean firstUsing = true;
	private static boolean firstBoneCP = true;
	private static final String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String usr = "jee-cdb";
	private static final String pwd = "password";
	
	public static BoneCP connectionPool = null;
	
	public static Connection getConnection(){
		if(firstUsing){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			firstUsing = false;
		}
		
		if(firstBoneCP){
			try {
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
