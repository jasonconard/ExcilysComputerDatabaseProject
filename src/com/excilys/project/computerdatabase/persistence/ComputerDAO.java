package com.excilys.project.computerdatabase.persistence;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.domain.WrapperComputer;

public class ComputerDAO {
	
	private static final String table = "computer";
	
	public static ComputerDAO instance = null;
	
	public List<Computer> retrieveAllWithCompanyNameByWrapper(WrapperComputer wc, Connection connection){
		
		List<Computer> alc = new ArrayList<Computer>();
		
		String like = "";
		if(wc.getFilter()!=null){
			like +=wc.getFilter();
		}
		
		String order = "";
		if(wc.getColumn()!=null){
			order +=wc.getColumn();
		}
		
		String direction = "";
		if(wc.getDirection()!=null){
			direction +=wc.getDirection();
		}
		
		int idBegin = wc.getPage()*WrapperComputer.NBLINEPERPAGES; 
		int nbLines   = WrapperComputer.NBLINEPERPAGES; 
		
		String query = "SELECT cu.*, ca.name AS name2 FROM company AS ca "
				+ "RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id "
				+ "WHERE cu.name LIKE '%"+like+"%' OR ca.name LIKE '%"+like+"%'"
				+ "ORDER BY "+ order + " "+direction+" "
				+ "LIMIT "+idBegin+", "+nbLines;
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			results = preparedStatement.executeQuery();
			while(results.next()){
				long id = results.getLong("id");
				String name = results.getString("name");
				Date introduced = results.getDate("introduced");
				Date discontinued = results.getDate("discontinued");
				long companyId = results.getLong("company_id");
				String companyName = results.getString("name2");
				alc.add(
						new Computer.ComputerBuilder(id, name)
						.introduced(introduced)
			            .discontinued(discontinued)
			            .company(
			            		new Company.CompanyBuilder(companyId)
			            		.name(companyName)
			            		.build()
			            		)
			            .build()
				);
			}
		} catch (SQLException e) {
		} finally{
			closeAll(results,preparedStatement);
		}

		return alc;
	}
	
	public Company retrieveCompanyByComputerId(long idComputer, Connection connection){
		
		Company company = null;
		
		String query = "SELECT ca.* FROM company AS ca INNER JOIN "+table+" AS cu ON cu.company_id = ca.id WHERE cu.id = "+idComputer;
		
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
			results.close();
			preparedStatement.close();
		} catch (SQLException e) {
		} finally{
			closeAll(results,preparedStatement);
		}
		
		return company;
	}
	
	public Computer retrieveByComputerId(long idComputer, Connection connection) {
		Computer computer = null;
		
		String query = "SELECT cu.*, ca.name AS name2 FROM company AS ca "
				+ "RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id "
				+ "WHERE cu.id = ?";
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, idComputer);
			results = preparedStatement.executeQuery();
			if(results.next()){
				long id = results.getLong("id");
				String name = results.getString("name");
				Date introduced = results.getDate("introduced");
				Date discontinued = results.getDate("discontinued");
				long companyId = results.getLong("company_id");
				String companyName = results.getString("name2");
				computer = new Computer.ComputerBuilder(id, name)
							.introduced(introduced)
				            .discontinued(discontinued)
				            .company(
				            		new Company.CompanyBuilder(companyId)
				            		.name(companyName)
				            		.build()
				            		)
				            .build();
			}
		} catch (SQLException e) {
		} finally{
			closeAll(results, preparedStatement);
		}
		
		return computer;
	}
	
	public void insert(Computer computer, Connection connection){
		String query = "INSERT INTO "+table+" VALUES(?,?,?,?,?)";

		PreparedStatement preparedStatement = null;
		
		try{
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong  (1, computer.getId()  );
			preparedStatement.setString(2, computer.getName());
			
			if(computer.getIntroduced()!=null){
				preparedStatement.setDate(3,  new java.sql.Date(computer.getIntroduced().getTime()));
			}else{
				preparedStatement.setNull(3, Types.TIMESTAMP);
			}
			
			if(computer.getDiscontinued()!=null){
				preparedStatement.setDate(4, new java.sql.Date(computer.getDiscontinued().getTime()));
			}else{
				preparedStatement.setNull(4, Types.TIMESTAMP);
			}
			
			if(computer.getCompany() != null){
				preparedStatement.setLong(5, computer.getCompany().getId());
			}else{
				preparedStatement.setNull(5, Types.BIGINT);
			}
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
		} finally{
			closeAll(null,preparedStatement);
		}
	}
	
	public void delete(long id, Connection connection) {
		String query = "DELETE FROM "+table+" WHERE id = ?";
		PreparedStatement preparedStatement = null;
		
		try{
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, id);
				
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
		} finally{
			closeAll(null,preparedStatement);
		}
	}

	public void update(Computer c, Connection connection) {
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		String query = "UPDATE "+table+" SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?";
		
		try{
			
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, c.getName());
			
			if(c.getIntroduced()!=null){
				preparedStatement.setDate(2,  new java.sql.Date(c.getIntroduced().getTime()));
			}else{
				preparedStatement.setNull(2, Types.TIMESTAMP);
			}
			
			if(c.getDiscontinued()!=null){
				preparedStatement.setDate(3, new java.sql.Date(c.getDiscontinued().getTime()));
			}else{
				preparedStatement.setNull(3, Types.TIMESTAMP);
			}
			
			if(c.getCompany() != null){
				preparedStatement.setLong(4, c.getCompany().getId());
			}else{
				preparedStatement.setNull(4, Types.BIGINT);
			}
			
			preparedStatement.setLong(5, c.getId());
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
		} finally{
			closeAll(results, preparedStatement);
		}
	}
	
	public int computerNumberByFilter(String filter, Connection connection) {
		int count = 0;
		
		String query = "SELECT count(*) AS countComputer FROM company AS ca "
				+ "RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id "
				+ "WHERE cu.name LIKE '%"+filter+"%' OR ca.name LIKE '%"+filter+"%'";
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			results = preparedStatement.executeQuery();
			if(results.next()){
				count = results.getInt("countComputer");
			}
		} catch (SQLException e) {
		} finally{
			closeAll(results,preparedStatement);
		}

		return count;
	}
	
	private void closeAll(ResultSet rs,PreparedStatement ps){
		try {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	synchronized public static ComputerDAO getInstance(){
		if(instance == null){
			instance = new ComputerDAO();
		}
		return instance;
	}

}
