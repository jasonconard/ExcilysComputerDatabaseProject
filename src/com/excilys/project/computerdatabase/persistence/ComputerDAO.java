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

public class ComputerDAO {
	
	private static final String table = "computer";
	
	public static ComputerDAO instance = null;
	
	public List<Computer> retrieveAll(){
		Connection con = ConnectionManager.getConnection();
		
		List<Computer> alc = new ArrayList<Computer>();
		
		String query = "SELECT * FROM "+table;
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = con.prepareStatement(query);
			results = preparedStatement.executeQuery();
			
			while(results.next()){
				long id = results.getLong("id");
				String name = results.getString("name");
				Date introduced = results.getDate("introduced");
				Date discontinued = results.getDate("discontinued");
				long companyId = results.getLong("company_id");
				alc.add(
						new Computer.ComputerBuilder(id, name)
						.introduced(introduced)
			            .discontinued(discontinued)
			            .company(
			            		new Company.CompanyBuilder(companyId)
			            		.build()
			            		)
			            .build()
				);
			}
			
		} catch (SQLException e) {
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(results,preparedStatement,con);
		}
		
		return alc;
	}
	
	public List<Computer> retrieveAllWithCompanyName(){
		Connection con = ConnectionManager.getConnection();
		
		List<Computer> alc = new ArrayList<Computer>();
		
		String query = "SELECT cu.*, ca.name AS name2 FROM company AS ca "
				+ "RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id ";
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = con.prepareStatement(query);
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
			results.close();
			preparedStatement.close();
		} catch (SQLException e) {
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(results, preparedStatement, con);
		}
		
		return alc;
	}
	
	public List<Computer> retrieveAllWithCompanyNameOrderBy(String order, String direction){
		Connection con = ConnectionManager.getConnection();
		
		List<Computer> alc = new ArrayList<Computer>();
		
		String query = "SELECT cu.*, ca.name AS name2 FROM company AS ca "
				+ "RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id "
				+ "ORDER BY "+ order + " "+direction;
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = con.prepareStatement(query);
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
			results.close();
			preparedStatement.close();
		} catch (SQLException e) {
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(results, preparedStatement, con);
		}
		
		return alc;
	}
	
	public List<Computer> retrieveAllWithCompanyNameLike(String like){
		Connection con = ConnectionManager.getConnection();
		
		List<Computer> alc = new ArrayList<Computer>();
		
		String query = "SELECT cu.*, ca.name AS name2 FROM company AS ca "
				+ "RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id "
				+ "WHERE cu.name LIKE '%"+like+"%' OR ca.name LIKE '%"+like+"%'";
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = con.prepareStatement(query);
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
			            .build());
			}
		} catch (SQLException e) {
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(results,preparedStatement,con);
		}

		return alc;
	}
	
	public List<Computer> retrieveAllWithCompanyNameLikeOrder(String like, String order, String direction){
		Connection con = ConnectionManager.getConnection();
		
		List<Computer> alc = new ArrayList<Computer>();
		
		String query = "SELECT cu.*, ca.name AS name2 FROM company AS ca "
				+ "RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id "
				+ "WHERE cu.name LIKE '%"+like+"%' OR ca.name LIKE '%"+like+"%'"
				+ "ORDER BY "+ order + " "+direction;
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = con.prepareStatement(query);
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
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(results,preparedStatement,con);
		}

		return alc;
	}
	
	public Company retrieveCompanyByComputerId(long idComputer){
		Connection con = ConnectionManager.getConnection();
		
		Company company = null;
		
		String query = "SELECT ca.* FROM company AS ca INNER JOIN "+table+" AS cu ON cu.company_id = ca.id WHERE cu.id = "+idComputer;
		
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
			results.close();
			preparedStatement.close();
		} catch (SQLException e) {
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(results,preparedStatement,con);
		}
		
		return company;
	}
	
	public Computer retrieveByComputerId(long idComputer) {
		Connection con = ConnectionManager.getConnection();
		
		Computer computer = null;
		
		String query = "SELECT cu.*, ca.name AS name2 FROM company AS ca "
				+ "RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id "
				+ "WHERE cu.id = ?";
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = con.prepareStatement(query);
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
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(results, preparedStatement, con);
		}
		
		return computer;
	}
	
	public void insert(Computer computer){
		Connection con = ConnectionManager.getConnection();
		
		String query = "INSERT INTO "+table+" VALUES(?,?,?,?,?)";
		
		PreparedStatement preparedStatement = null;
		
		try{
			preparedStatement = con.prepareStatement(query);
			
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
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(null,preparedStatement,con);
		}
	}
	
	public void delete(long id) {
		Connection con = ConnectionManager.getConnection();
		
		String query = "DELETE FROM "+table+" WHERE id = ?";
		
		PreparedStatement preparedStatement = null;
		
		try{
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setLong(1, id);
				
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("SQL query problem : "+query);
		} finally{
			closeAll(null,preparedStatement,con);
		}
	}

	public void update(Computer c) {
		Connection con = ConnectionManager.getConnection();
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;
		
		String query = "UPDATE "+table+" SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?";
		try{
			
			
			preparedStatement = con.prepareStatement(query);
			
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
			System.out.println("SQL query problem : "+query);
		} finally{
			closeAll(results, preparedStatement, con);
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
	
	synchronized public static ComputerDAO getInstance(){
		if(instance == null){
			instance = new ComputerDAO();
		}
		return instance;
	}
}
