package com.excilys.project.computerdatabase.persistence;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.domain.Computer;

public class ComputerDAO {

	private static final String table = "computer";

	public static ComputerDAO instance = null;

	public List<Computer> retrieveAllWithCompanyNameByWrapper(Page<Computer> pc) throws SQLException{
		Connection connection = ConnectionManager.INSTANCE.getConnection();
		List<Computer> alc = new ArrayList<Computer>();

		String like = "";
		if(pc.getFilter()!=null){
			like +=pc.getFilter();
		}

		String order = "";
		if(pc.getColumn()!=null){
			order +=pc.getColumn();
		}

		String direction = "";
		if(pc.getDirection()!=null){
			direction +=pc.getDirection();
		}

		int idBegin = pc.getNumero()*Page.NBLINEPERPAGES; 
		int nbLines   = Page.NBLINEPERPAGES; 

		StringBuilder query = new StringBuilder("SELECT cu.*, ca.name AS name2 FROM company AS ca ")
		.append("RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id ")
		.append("WHERE cu.name LIKE '%"+like+"%' OR ca.name LIKE '%").append(like).append("%'")
		.append("ORDER BY ").append(order).append(" ").append(direction)
		.append(" LIMIT ").append(idBegin).append(", ").append(nbLines);

		ResultSet results = null;
		PreparedStatement preparedStatement = null;

		preparedStatement = connection.prepareStatement(query.toString());
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
		
		closeAll(results,preparedStatement);

		return alc;
	}

	public Company retrieveCompanyByComputerId(long idComputer) throws SQLException{
		Connection connection = ConnectionManager.INSTANCE.getConnection();
		Company company = null;

		StringBuilder query = new StringBuilder("SELECT ca.* FROM company AS ca INNER JOIN ")
		.append(table).append("AS cu ON cu.company_id = ca.id WHERE cu.id = ")
		.append(idComputer);

		ResultSet results = null;
		PreparedStatement preparedStatement = null;

		preparedStatement = connection.prepareStatement(query.toString());
		results = preparedStatement.executeQuery();
		
		if(results.next()){
			long id = results.getLong("id");
			String name = results.getString("name");
			company = new Company.CompanyBuilder(id).name(name).build();
		}

		closeAll(results,preparedStatement);

		return company;
	}

	public Computer retrieveByComputerId(long idComputer) throws SQLException {
		Connection connection = ConnectionManager.INSTANCE.getConnection();
		Computer computer = null;

		StringBuilder query = new StringBuilder("SELECT cu.*, ca.name AS name2 FROM company AS ca ")
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
		
		closeAll(results, preparedStatement);

		return computer;
	}

	public int insert(Computer computer) throws SQLException{
		Connection connection = ConnectionManager.INSTANCE.getConnection();
		int id = 0;
		
		StringBuilder query = new StringBuilder("INSERT INTO ")
		.append(table).append(" VALUES(?,?,?,?,?)");

		PreparedStatement preparedStatement = null;
		
		preparedStatement = connection.prepareStatement("START TRANSACTION");
		preparedStatement.executeUpdate();
		
		preparedStatement = connection.prepareStatement(query.toString());

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
		
		preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID() AS idComputer");
		ResultSet rs = preparedStatement.executeQuery();
		if(rs.next()){
			id = rs.getInt("idComputer");
		}
		
		preparedStatement = connection.prepareStatement("COMMIT");
		preparedStatement.executeUpdate();
		
		closeAll(rs,preparedStatement);
		return id;
	}

	public void delete(long id) throws SQLException {
		Connection connection = ConnectionManager.INSTANCE.getConnection();
		StringBuilder query = new StringBuilder("DELETE FROM ").append(table).append(" WHERE id = ?");
		
		PreparedStatement preparedStatement = null;

		preparedStatement = connection.prepareStatement(query.toString());

		preparedStatement.setLong(1, id);

		preparedStatement.executeUpdate();

		closeAll(null,preparedStatement);
	}

	public void update(Computer c) throws SQLException {
		Connection connection = ConnectionManager.INSTANCE.getConnection();
		ResultSet results = null;
		PreparedStatement preparedStatement = null;

		StringBuilder query = new StringBuilder("UPDATE ").append(table)
		.append(" SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?");

		preparedStatement = connection.prepareStatement(query.toString());

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

		closeAll(results, preparedStatement);
	}

	public int computerNumberByFilter(String filter) throws SQLException {
		Connection connection = ConnectionManager.INSTANCE.getConnection();
		int count = 0;

		StringBuilder query = new StringBuilder("SELECT count(*) AS countComputer FROM company AS ca ")
		.append("RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id ")
		.append("WHERE cu.name LIKE '%").append(filter)
		.append("%' OR ca.name LIKE '%").append(filter).append("%'");

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

	synchronized public static ComputerDAO getInstance(){
		if(instance == null){
			instance = new ComputerDAO();
		}
		return instance;
	}

}
