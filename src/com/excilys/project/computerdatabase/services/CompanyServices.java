package com.excilys.project.computerdatabase.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.persistence.CompanyDAO;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;

public class CompanyServices {
	
	public static CompanyServices instance = null;
	
	private CompanyDAO companyDAO = CompanyDAO.getInstance();
	
	public List<Company> getAllCompanies(){
		Connection connection = ConnectionManager.getConnection();
		List<Company> companies = companyDAO.retrieveAll(connection);
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return companies;
	}
	
	public Company getCompany(long idCompany){
		Connection connection = ConnectionManager.getConnection();
		Company company = companyDAO.retrieveByCompanyId(idCompany, connection);
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return company;
	}
	
	public void insert(Company company){
		Connection connection = ConnectionManager.getConnection();
		
		companyDAO.insert(company, connection);
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	synchronized public static CompanyServices getInstance(){
		if(instance == null){
			instance = new CompanyServices();
		}
		return instance;
	}
}
