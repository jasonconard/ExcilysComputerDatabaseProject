package com.excilys.project.computerdatabase.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.project.computerdatabase.domain.Company;

@Repository
public class CompanyDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<Company> retrieveAll(){
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("FROM Company").list();
	}

	public Company retrieveByCompanyId(long idCompany){
		Session session = sessionFactory.getCurrentSession();
		Company company = (Company)session.get(Company.class, idCompany);
		return company;
	}

}