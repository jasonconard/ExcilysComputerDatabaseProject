package com.excilys.project.computerdatabase.persistence;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.project.computerdatabase.domain.Company;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class CompanyDAO {
	
	public static CompanyDAO instance = null;
	synchronized public static CompanyDAO getInstance(){
		if(instance == null){
			instance = new CompanyDAO();
		}
		return instance;
	}
	
	@Autowired
	BoneCPDataSource ds;
	
	private static final String table = "company";

	public List<Company> retrieveAll(){
		List<Company> alc = new ArrayList<Company>();

		try{
		
			Connection connection = ds.getConnection();
			
			StringBuilder query = new StringBuilder("SELECT * FROM ").append(table)
					.append(" ORDER BY name");
			
			ResultSet results = null;
			PreparedStatement preparedStatement = null;
	
			preparedStatement = connection.prepareStatement(query.toString());
			results = preparedStatement.executeQuery();
	
			while(results.next()){
				long id = results.getLong("id");
				String name = results.getString("name");
				alc.add(new Company.CompanyBuilder(id).name(name).build());
			}
	
			closeAll(results,preparedStatement);
			connection.close();
		}catch (SQLException e) {}	
		
		return alc;
	}

	public Company retrieveByCompanyId(long idCompany){
		Company company = null;
		
		try{
		
			Connection connection = ds.getConnection();
			
			StringBuilder query = new StringBuilder("SELECT ca.* FROM company AS ca WHERE ca.id = ?");
	
			ResultSet results = null;
			PreparedStatement preparedStatement = null;
	
			preparedStatement = connection.prepareStatement(query.toString());
			
			preparedStatement.setLong(1, idCompany);
			
			results = preparedStatement.executeQuery();
	
			if(results.next()){
				long id = results.getLong("id");
				String name = results.getString("name");
				company = new Company.CompanyBuilder(id).name(name).build();
			}
			closeAll(results,preparedStatement);
			connection.close();
		}catch (SQLException e) {}
		
		return company;
	}

	public void insert(Company c){
		
		try{
		
			Connection connection = ds.getConnection();
			
			StringBuilder query = new StringBuilder("INSERT INTO ").append(table).append(" VALUES(?,?)");
	
			PreparedStatement preparedStatement = null;
	
			preparedStatement = connection.prepareStatement(query.toString());
	
			preparedStatement.setLong(1, c.getId());
			preparedStatement.setString(2, c.getName());
	
			preparedStatement.executeUpdate();
			closeAll(null,preparedStatement);
			connection.close();
		}catch (SQLException e) {}
	}

	private void closeAll(ResultSet rs, PreparedStatement ps) throws SQLException{

		if(rs!=null){
			rs.close();
		}
		if(ps!=null){
			ps.close();
		}
	}

}