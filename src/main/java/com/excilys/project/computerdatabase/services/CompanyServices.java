package com.excilys.project.computerdatabase.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.persistence.CompanyDAO;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;

@Service
public class CompanyServices {
	
	public static CompanyServices instance = null;
	synchronized public static CompanyServices getInstance(){
		if(instance == null){
			instance = new CompanyServices();
		}
		return instance;
	}
	
	@Autowired
	ConnectionManager connectionManager;
	public void setConnectionManager(ConnectionManager connectionManager){
		this.connectionManager = connectionManager;
	}
	
	@Autowired
	CompanyDAO companyDAO;
	public void setCompanyDAO(CompanyDAO companyDAO){
		this.companyDAO = companyDAO;
	}

	public List<Company> getAllCompanies(){
		String message = "Companies searching";
		List<Company> companies = null;
		try{
			connectionManager.getConnection();

			companies = companyDAO.retrieveAll();
			
			if(companies!=null){	  
				connectionManager.commit(message);
			}else{
				connectionManager.rollback(message+" (companies is null)");
			}
		}catch(SQLException e){
			connectionManager.rollback(message);
		}finally{
			connectionManager.closeConnection();
		}

		return companies;
	}

	public Company getCompany(long idCompany){
		String message = "Company searching";
		Company company = null;

		try{
			connectionManager.getConnection();

			company = companyDAO.retrieveByCompanyId(idCompany);

			if(company!=null || idCompany == 0){	  
				connectionManager.commit(message);
			}else{
				connectionManager.rollback(message+" (company is null)");
			}
		}catch(SQLException sqle){
			connectionManager.rollback(message);
		}finally{
			connectionManager.closeConnection();
		}

		return company;
	}

	public void insert(Company company){
		String message = "Company insert";
		try{
			connectionManager.getConnection();

			companyDAO.insert(company);

			connectionManager.commit(message);
		}catch(SQLException sqle){
			connectionManager.rollback(message);
		}finally{
			connectionManager.closeConnection();
		}
	}
}
