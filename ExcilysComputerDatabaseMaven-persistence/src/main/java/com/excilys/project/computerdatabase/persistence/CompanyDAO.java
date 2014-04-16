package com.excilys.project.computerdatabase.persistence;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Company> retrieveAll(){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		StringBuilder query = new StringBuilder("SELECT * FROM ").append(table).append(" ORDER BY name");
		return jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper(Company.class));
	}

	public Company retrieveByCompanyId(long idCompany){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		StringBuilder query = new StringBuilder("SELECT ca.* FROM company AS ca WHERE ca.id = ?");
		return (Company) jdbcTemplate.queryForObject(query.toString(), new Object[] {idCompany}, Company.class);
	}

	public void insert(Company c){
		StringBuilder query = new StringBuilder("INSERT INTO ").append(table).append(" VALUES(?,?)");	 
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);		
		Object[] params = {c.getId(), c.getName()};
		int[] types = {Types.BIGINT, Types.VARCHAR};

		jdbcTemplate.update(query.toString(), params, types);
	}

}
