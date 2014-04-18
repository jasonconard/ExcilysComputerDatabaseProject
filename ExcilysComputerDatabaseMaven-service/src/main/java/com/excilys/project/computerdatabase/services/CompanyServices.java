package com.excilys.project.computerdatabase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.persistence.CompanyDAO;

@Service
public class CompanyServices {
	
	@Autowired
	CompanyDAO companyDAO;
	public void setCompanyDAO(CompanyDAO companyDAO){
		this.companyDAO = companyDAO;
	}

	public List<Company> getAllCompanies(){
		return companyDAO.retrieveAll();
	}

	public Company getCompany(long idCompany){
		return companyDAO.retrieveByCompanyId(idCompany);
	}
}
