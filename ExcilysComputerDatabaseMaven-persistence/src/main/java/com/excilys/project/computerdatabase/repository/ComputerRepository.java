package com.excilys.project.computerdatabase.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.excilys.project.computerdatabase.domain.Computer;

public interface ComputerRepository extends JpaRepository<Computer, Long> { 
	public static final String SELECT_QUERY = "select c from Computer c left join c.company cy where c.name like :search or cy.name like :search";
	public static final String COUNT_QUERY = "select count(c.id) from Computer c left join c.company cy where c.name like :search or cy.name like :search";

	@Query(value = SELECT_QUERY, countQuery = COUNT_QUERY)
	public Page<Computer> findAll(@Param("search") String search, Pageable pageable);
}
