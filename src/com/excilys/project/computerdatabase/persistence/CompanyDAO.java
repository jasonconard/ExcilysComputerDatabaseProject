package com.excilys.project.computerdatabase.persistence;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.util.List;
import java.util.ArrayList;

import com.excilys.project.computerdatabase.domain.Company;

public class CompanyDAO {
	
	private static final String table = "company";
	
	public static CompanyDAO instance = null;
	
	public List<Company> retrieveAll(){
		Connection con = ConnectionManager.getConnection();
		
		List<Company> alc = new ArrayList<Company>();
		
		String query = "SELECT * FROM "+table;
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = con.prepareStatement(query);
			results = preparedStatement.executeQuery(query);
			
			while(results.next()){
				long id = results.getLong("id");
				String name = results.getString("name");
				alc.add(new Company.CompanyBuilder(id).name(name).build());
			}
			
		} catch (SQLException e) {
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(results,preparedStatement,con);
		}
		
		return alc;
	}
	
	public Company retrieveByCompanyId(long idCompany){
		Connection con = ConnectionManager.getConnection();
		
		Company company = null;
		
		String query = "SELECT ca.* FROM company AS ca WHERE ca.id = "+idCompany;
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = con.prepareStatement(query);
			results = preparedStatement.executeQuery();
			
			if(results.next()){
				long id = results.getLong("id");
				String name = results.getString("name");
				company = new Company.CompanyBuilder(id).name(name).build();
			}
			
		} catch (SQLException e) {
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(results,preparedStatement,con);
		}
		
		return company;
	}
	
	public void insert(Company c){
		Connection con = ConnectionManager.getConnection();
		
		String query = "INSERT INTO "+table+" VALUES(?,?)";
		
		PreparedStatement preparedStatement = null;
		
		try{
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setLong(1, c.getId());
			preparedStatement.setString(2, c.getName());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(null,preparedStatement,con);
		}
	}
	
	private void closeAll(ResultSet rs,PreparedStatement ps, Connection cn){
		try {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(cn!=null){
				cn.close();
			}
		} catch (SQLException e) {
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
