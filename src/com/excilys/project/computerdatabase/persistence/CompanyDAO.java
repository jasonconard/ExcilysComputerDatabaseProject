package com.excilys.project.computerdatabase.persistence;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.services.LogsServices;

public class CompanyDAO {
	
	private static final String table = "company";
	
	public static CompanyDAO instance = null;
	public static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private static LogsServices logsServices = LogsServices.getInstance();
	
	public List<Company> retrieveAll(Connection connection){
		
		List<Company> alc = new ArrayList<Company>();
		
		String query = "SELECT * FROM "+table;
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			results = preparedStatement.executeQuery(query);
			
			while(results.next()){
				long id = results.getLong("id");
				String name = results.getString("name");
				alc.add(new Company.CompanyBuilder(id).name(name).build());
			}
			
		} catch (SQLException e) {
			logsServices.insert("Retrieve All Companies error. SQL query : "+query, "Error");
			//logger.error("Retrieve All Companies error. SQL query : "+query);
		} finally{
			logsServices.insert("Retrieve All Companies complete !", "Complete");
			//logger.info("Retrieve All Companies complete !");
			closeAll(results,preparedStatement);
		}
		
		return alc;
	}
	
	public Company retrieveByCompanyId(long idCompany, Connection connection){
		
		Company company = null;
		
		String query = "SELECT ca.* FROM company AS ca WHERE ca.id = "+idCompany;
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			results = preparedStatement.executeQuery();
			
			if(results.next()){
				long id = results.getLong("id");
				String name = results.getString("name");
				company = new Company.CompanyBuilder(id).name(name).build();
			}
			
		} catch (SQLException e) {
			logsServices.insert("Retrieve Company By Id error. SQL query : "+query, "Error");
			//logger.error("Retrieve Company By Id error. SQL query : "+query);
			//System.err.println("SQL query problem : "+query);
		} finally{
			logsServices.insert("Retrieve Company By Id complete !", "Complete");
			//logger.info("Retrieve Company By Id complete !");
			closeAll(results,preparedStatement);
		}
		
		return company;
	}
	
	public void insert(Company c, Connection connection){
		String query = "INSERT INTO "+table+" VALUES(?,?)";
		String visualQuery = "INSERT INTO "+table+" VALUES("+c.getId()+",'"+c.getName()+"')";
		
		PreparedStatement preparedStatement = null;
		
		try{
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, c.getId());
			preparedStatement.setString(2, c.getName());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			logsServices.insert("Insert company error. SQL query  : "+visualQuery, "Error");
			//logger.error("Insert company error. SQL query  : "+visualQuery);
		} finally{
			logsServices.insert("Insert company complete !", "Complete");
			//logger.info("Insert company complete !");
			closeAll(null,preparedStatement);
		}
	}
	
	private void closeAll(ResultSet rs, PreparedStatement ps){
		try {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			logger.info("Every connections closed !");
		} catch (SQLException e) {
			logger.error("Connections closing failed.");
			e.printStackTrace();
		}
	}
	
	synchronized public static CompanyDAO getInstance(){
		if(instance == null){
			instance = new CompanyDAO();
		}
		return instance;
	}
	
}
