package com.excilys.project.computerdatabase.persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.excilys.project.computerdatabase.domain.Company;

@Repository
public class CompanyDAO {
	
	@PersistenceContext(unitName="entityManagerFactory")
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Company> retrieveAll(){
		return ((List<Company>)entityManager.createQuery("SELECT company FROM Company as company").getResultList());
	}

	public Company retrieveByCompanyId(long idCompany){
		return entityManager.find(Company.class, idCompany);
	}

}