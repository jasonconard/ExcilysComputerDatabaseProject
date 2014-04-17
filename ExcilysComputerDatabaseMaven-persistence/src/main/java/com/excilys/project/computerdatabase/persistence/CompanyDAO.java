package com.excilys.project.computerdatabase.persistence;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.project.computerdatabase.domain.Company;

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
	JdbcTemplate jdbcTemplate;
	
	private static final String table = "company";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Company> retrieveAll(){
		StringBuilder query = new StringBuilder("SELECT * FROM ").append(table).append(" ORDER BY name");
		return jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper(Company.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Company retrieveByCompanyId(long idCompany){
		StringBuilder query = new StringBuilder("SELECT ca.* FROM company AS ca WHERE ca.id = ?");
		List<Company> lc = jdbcTemplate.query(query.toString(), new Object[]{idCompany}, new BeanPropertyRowMapper(Company.class));
		Company company = null;
		if(lc.size()>0){
			company = lc.get(0);
		}
		return company;
	}

	public void insert(Company c){
		StringBuilder query = new StringBuilder("INSERT INTO ").append(table).append(" VALUES(?,?)");	 
		Object[] params = {c.getId(), c.getName()};
		int[] types = {Types.BIGINT, Types.VARCHAR};

		jdbcTemplate.update(query.toString(), params, types);
	}

}
