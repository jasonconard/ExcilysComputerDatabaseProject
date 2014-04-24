package com.excilys.project.computerdatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.excilys.project.computerdatabase.domain.Company;

public interface CompanyRepository  extends JpaRepository<Company, Long> {
	
}
