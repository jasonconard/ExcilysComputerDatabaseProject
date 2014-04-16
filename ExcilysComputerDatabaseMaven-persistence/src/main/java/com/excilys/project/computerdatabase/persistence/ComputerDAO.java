package com.excilys.project.computerdatabase.persistence;

import java.util.List;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.dto.ComputerDTO;
import com.excilys.project.computerdatabase.mapper.ComputerMapper;
import com.excilys.project.computerdatabase.rowmapper.ComputerDTORowMapper;
import com.excilys.project.computerdatabase.rowmapper.ComputerRowMapper;
import com.jolbox.bonecp.BoneCPDataSource;

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
	ComputerMapper computerMapper;
	public void setComputerMapper(ComputerMapper computerMapper){
		this.computerMapper = computerMapper;
	}
	
	@Autowired
	BoneCPDataSource ds;
	
	private static final String table = "computer";

	public List<ComputerDTO> retrieveAllByWrapper(Page<ComputerDTO> pc){
			
			if(!pc.getDirection().equals("ASC") && !pc.getDirection().equals("DESC")){
				pc.setDirection("ASC");
			}
	
			int idBegin = pc.getNumero() * Page.NBLINEPERPAGES; 
			int nbLines = Page.NBLINEPERPAGES; 
	
			StringBuilder query = new StringBuilder();
			
			query.append("SELECT cu.*, ca.name AS company_name FROM computer AS cu ")
			     .append("LEFT OUTER JOIN company AS ca ON cu.company_id = ca.id ");
			
			if(pc.getFilter().length()>0){
				query.append("WHERE cu.name LIKE ? OR ca.name LIKE ? ");
			}
			
			query.append("ORDER BY ")
				 .append(pc.getColumn())
				 .append(" ")
			     .append(pc.getDirection())
			     .append(" LIMIT ?, ?");
			
			Object[] obj;
			if(pc.getFilter().length()>0){
				obj = new Object[4];
				obj[0] =  "%"+pc.getFilter()+"%";
				obj[1] =  "%"+pc.getFilter()+"%";
				obj[2] =  idBegin;
				obj[3] =  nbLines;
			}else{
				obj = new Object[2];
				obj[0] =  idBegin;
				obj[1] =  nbLines;
			}
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
			
			
			@SuppressWarnings("unchecked")
			List<ComputerDTO> alc = jdbcTemplate.query(query.toString(), obj, new ComputerDTORowMapper());
			
			return alc;
	}
	
	public Computer retrieveByComputerId(long idComputer){
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT cu.*, ca.name AS company_name FROM company AS ca ")
		     .append("RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id ")
		     .append("WHERE cu.id = ?");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		@SuppressWarnings("unchecked")
		List<Computer> alc = jdbcTemplate.query(query.toString(), new Object[] { idComputer }, new ComputerRowMapper());
		
		Computer computer = null;
		if(alc.size() > 0){
			computer = alc.get(0);
		}
		return computer;
	}

	public int insert(Computer computer){
		int id = 0;
		
		try{
			
			Connection connection = ds.getConnection();
		
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
			connection.close();
		}catch (SQLException e) {}
		
		return id;
	}

	public void delete(long id){
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM ")
			     .append(table)
			     .append(" WHERE id = ?");
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
			jdbcTemplate.update(query.toString(), new Object[]{ id });		
	}

	public void update(Computer c){
		StringBuilder query = new StringBuilder();
		query.append("UPDATE ")
		     .append(table)
		     .append(" SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?");
	 
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
	 
		java.sql.Date introduced = null;
		if(c.getIntroduced()!=null) introduced =  new java.sql.Date(c.getIntroduced().toDate().getTime());
		
		java.sql.Date discontinued = null;
		if(c.getDiscontinued()!=null) discontinued = new java.sql.Date(c.getDiscontinued().toDate().getTime());
		
		long companyId = 0;
		if(c.getCompany()!=null) companyId = c.getCompany().getId();
		
		Object[] params = {c.getName(), introduced, discontinued, companyId, c.getId()};
		int[] types = {Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT};

		jdbcTemplate.update(query.toString(), params, types);
	}

	public int numberByFilter(String filter){
		int count = 0;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT count(*) AS countComputer FROM company AS ca ")
	     .append("RIGHT OUTER JOIN computer AS cu ON cu.company_id = ca.id ")
	     .append("WHERE cu.name LIKE ? OR ca.name LIKE ?");
		
		count = (Integer) jdbcTemplate.queryForObject(query.toString(), new Object[]{ '%'+filter+'%', '%'+filter+'%'}, Integer.class);
		
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
