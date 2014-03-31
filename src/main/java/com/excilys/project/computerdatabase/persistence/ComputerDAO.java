package com.excilys.project.computerdatabase.persistence;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.domain.Computer;

@Repository
public class ComputerDAO {
	
	public static ComputerDAO instance = null;
	synchronized public static ComputerDAO getInstance(){
		if(instance == null){
			instance = new ComputerDAO();
		}
		return instance;
	}
	
	@Autowired
	ConnectionManager connectionManager;
	public void setConnectionManager(ConnectionManager connectionManager){
		this.connectionManager = connectionManager;
	}
	
	private static final String table = "computer";

	public List<Computer> retrieveAllByWrapper(Page<Computer> pc) throws SQLException{
		Connection connection = connectionManager.getConnection();
		List<Computer> alc = new ArrayList<Computer>();

		String like = "";
		if( pc.getFilter() != null ){
			like += pc.getFilter();
		}

		String order = "";
		if( pc.getColumn() != null ){
			order += pc.getColumn();
		}

		String direction = "";
		if( pc.getDirection() != null ){
			direction += pc.getDirection();
		}
		
		if(!direction.equals("ASC") && !direction.equals("DESC")){
			direction = "ASC";
		}

		int idBegin = pc.getNumero() * Page.NBLINEPERPAGES; 
		int nbLines = Page.NBLINEPERPAGES; 

		StringBuilder query = new StringBuilder();
		
		query.append("SELECT cu.*, ca.name AS company_name FROM computer AS cu ")
		     .append("LEFT OUTER JOIN company AS ca ON cu.company_id = ca.id ");
		
		if(like.length()>0){
			query.append("WHERE cu.name LIKE ? OR ca.name LIKE ? ");
		}
		
		query.append("ORDER BY ")
			 .append(order)
			 .append(" ")
		     .append(direction)
		     .append(" LIMIT ?, ?");
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;

		preparedStatement = connection.prepareStatement(query.toString());
		
		if(like.length()>0){
			preparedStatement.setString(1, "%"+like+"%");
			preparedStatement.setString(2, "%"+like+"%");
			preparedStatement.setInt   (3, idBegin);
			preparedStatement.setInt   (4, nbLines);
		}else{
			preparedStatement.setInt   (1, idBegin);
			preparedStatement.setInt   (2, nbLines);
		}
		
		
		results = preparedStatement.executeQuery();
		
		while(results.next()){
			long id = results.getLong("id");
			String name = results.getString("name");
			Date introduced = results.getDate("introduced");
			Date discontinued = results.getDate("discontinued");
			
			long companyId = results.getLong("company_id");
			String companyName = results.getString("company_name");
			
			LocalDate introducedLD = null;
			if(introduced != null){
				introducedLD = LocalDate.fromDateFields(introduced);
			}
			LocalDate discontinuedLD = null;
			if(discontinued != null){
				discontinuedLD = LocalDate.fromDateFields(discontinued);
			}
			
			alc.add(
				new Computer.ComputerBuilder(id, name)
					.introduced(introducedLD)
					.discontinued(discontinuedLD)
					.company(
						new Company.CompanyBuilder(companyId)
							.name(companyName)
							.build()
						)
					.build()
			);
		}
		
		closeAll(results,preparedStatement);

		return alc;
	}
	
	public Computer retrieveByComputerId(long idComputer) throws SQLException {
		Connection connection = connectionManager.getConnection();
		Computer computer = null;

		StringBuilder query = new StringBuilder();
		query.append("SELECT cu.*, ca.name AS name2 FROM company AS ca ")
		     .append("RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id ")
		     .append("WHERE cu.id = ?");
		
		ResultSet results = null;
		PreparedStatement preparedStatement = null;

		preparedStatement = connection.prepareStatement(query.toString());
		preparedStatement.setLong(1, idComputer);
		
		results = preparedStatement.executeQuery();
		
		if(results.next()){
			long id = results.getLong("id");
			String name = results.getString("name");
			Date introduced = results.getDate("introduced");
			Date discontinued = results.getDate("discontinued");
			long companyId = results.getLong("company_id");
			String companyName = results.getString("name2");
			
			LocalDate introducedLD = null;
			if(introduced != null){
				introducedLD = LocalDate.fromDateFields(introduced);
			}
			LocalDate discontinuedLD = null;
			if(discontinued != null){
				discontinuedLD = LocalDate.fromDateFields(discontinued);
			}
			
			
			computer = new Computer.ComputerBuilder(id, name)
			.introduced(introducedLD)
			.discontinued(discontinuedLD)
			.company(
					new Company.CompanyBuilder(companyId)
					.name(companyName)
					.build()
					)
					.build();
		}
		closeAll(results, preparedStatement);

		return computer;
	}

	public int insert(Computer computer) throws SQLException{
		Connection connection = connectionManager.getConnection();
		int id = 0;
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ")
		     .append(table)
		     .append(" VALUES(?,?,?,?,?)");
		
		PreparedStatement preparedStatement = null;
		
		preparedStatement = connection.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);

		preparedStatement.setLong  (1, 0);
		preparedStatement.setString(2, computer.getName());
		if(computer.getIntroduced() != null){
			preparedStatement.setDate(3, new java.sql.Date(computer.getIntroduced().toDate().getTime()));
		}else{
			preparedStatement.setNull(3, Types.TIMESTAMP);
		}

		if(computer.getDiscontinued() != null){
			preparedStatement.setDate(4, new java.sql.Date(computer.getDiscontinued().toDate().getTime()));
		}else{
			preparedStatement.setNull(4, Types.TIMESTAMP);
		}

		if(computer.getCompany() != null){
			preparedStatement.setLong(5, computer.getCompany().getId());
		}else{
			preparedStatement.setNull(5, Types.BIGINT);
		}
		preparedStatement.executeUpdate();
		
		
		ResultSet rs = preparedStatement.getGeneratedKeys();
		if(rs.next()){
			id = rs.getInt(1);
		}
		
		closeAll(rs,preparedStatement);
		return id;
	}

	public void delete(long id) throws SQLException {
		Connection connection = connectionManager.getConnection();
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ")
		     .append(table)
		     .append(" WHERE id = ?");
		
		PreparedStatement preparedStatement = null;

		preparedStatement = connection.prepareStatement(query.toString());

		preparedStatement.setLong(1, id);

		preparedStatement.executeUpdate();

		closeAll(null,preparedStatement);
	}

	public void update(Computer c) throws SQLException {
		Connection connection = connectionManager.getConnection();
		ResultSet results = null;
		PreparedStatement preparedStatement = null;

		StringBuilder query = new StringBuilder();
		query.append("UPDATE ")
		     .append(table)
		     .append(" SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?");

		preparedStatement = connection.prepareStatement(query.toString());

		preparedStatement.setString(1, c.getName());

		if(c.getIntroduced()!=null){
			preparedStatement.setDate(2, new java.sql.Date(c.getIntroduced().toDate().getTime()));
		}else{
			preparedStatement.setNull(2, Types.TIMESTAMP);
		}

		if(c.getDiscontinued()!=null){
			preparedStatement.setDate(3, new java.sql.Date(c.getDiscontinued().toDate().getTime()));
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

		closeAll(results, preparedStatement);
	}

	public int numberByFilter(String filter) throws SQLException {
		Connection connection = connectionManager.getConnection();
		int count = 0;

		StringBuilder query = new StringBuilder();
		query.append("SELECT count(*) AS countComputer FROM company AS ca ")
		     .append("RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id ")
		     .append("WHERE cu.name LIKE '%")
		     .append(filter)
		     .append("%' OR ca.name LIKE '%")
		     .append(filter)
		     .append("%'");

		ResultSet results = null;
		PreparedStatement preparedStatement = null;

		preparedStatement = connection.prepareStatement(query.toString());
		results = preparedStatement.executeQuery();
		
		if(results.next()){
			count = results.getInt("countComputer");
		}

		closeAll(results,preparedStatement);

		return count;
	}

	private void closeAll(ResultSet rs,PreparedStatement ps) throws SQLException{

		if(rs!=null){
			rs.close();
		}
		if(ps!=null){
			ps.close();
		}
		
	}

}
