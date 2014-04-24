package com.excilys.project.computerdatabase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.repository.CompanyRepository;

@Service
@Transactional
public class CompanyServices {
		
	@Autowired
	CompanyRepository companyRepository;


	@Transactional(readOnly = true)
	public List<Company> getAllCompanies(){
		return companyRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Company getCompany(long idCompany){
		return companyRepository.findOne(idCompany);
	}
}
