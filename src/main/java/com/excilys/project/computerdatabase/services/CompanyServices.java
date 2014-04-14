package com.excilys.project.computerdatabase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.persistence.CompanyDAO;

@Service
@Transactional
public class CompanyServices {
	
	public static CompanyServices instance = null;
	synchronized public static CompanyServices getInstance(){
		if(instance == null){
			instance = new CompanyServices();
		}
		return instance;
	}
	
	@Autowired
	CompanyDAO companyDAO;
	public void setCompanyDAO(CompanyDAO companyDAO){
		this.companyDAO = companyDAO;
	}

	@Transactional(readOnly = true)
	public List<Company> getAllCompanies(){
		List<Company> companies = null;

		companies = companyDAO.retrieveAll();

		return companies;
	}

	@Transactional(readOnly = true)
	public Company getCompany(long idCompany){
		Company company = null;
		
		company = companyDAO.retrieveByCompanyId(idCompany);

		return company;
	}

	@Transactional
	public void insert(Company company){
		
		companyDAO.insert(company);
		
	}
}
