package com.excilys.project.computerdatabase.services;

import java.sql.SQLException;
import java.util.List;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.persistence.CompanyDAO;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;

public enum CompanyServices {
	INSTANCE;

	public List<Company> getAllCompanies(){
		String message = "Companies searching";
		List<Company> companies = null;
		try{
			ConnectionManager.INSTANCE.getConnection();

			companies = CompanyDAO.INSTANCE.retrieveAll();
			
			if(companies!=null){	  
				ConnectionManager.INSTANCE.commit(message);
			}else{
				ConnectionManager.INSTANCE.rollback(message+" (companies is null)");
			}
		}catch(SQLException e){
			ConnectionManager.INSTANCE.rollback(message);
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}

		return companies;
	}

	public Company getCompany(long idCompany){
		String message = "Company searching";
		Company company = null;

		try{
			ConnectionManager.INSTANCE.getConnection();

			company = CompanyDAO.INSTANCE.retrieveByCompanyId(idCompany);

			if(company!=null || idCompany == 0){	  
				ConnectionManager.INSTANCE.commit(message);
			}else{
				ConnectionManager.INSTANCE.rollback(message+" (company is null)");
			}
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(message);
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}

		return company;
	}

	public void insert(Company company){
		String message = "Company insert";
		try{
			ConnectionManager.INSTANCE.getConnection();

			CompanyDAO.INSTANCE.insert(company);

			ConnectionManager.INSTANCE.commit(message);
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(message);
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}
	}
}
