package com.excilys.project.computerdatabase.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.mapper.ComputerMapper;

@Repository
public class ComputerDAO {
	
	@Autowired
	ComputerMapper computerMapper;
	public void setComputerMapper(ComputerMapper computerMapper){
		this.computerMapper = computerMapper;
	}
	
	@PersistenceContext(unitName="entityManagerFactory")
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Computer> retrieveAllByWrapper(Page<Computer> pc){
			
			if(!pc.getDirection().equals("ASC") && !pc.getDirection().equals("DESC")){
				pc.setDirection("ASC");
			}
	
			int idBegin = pc.getNumero() * Page.NBLINEPERPAGES; 
			int nbLines = Page.NBLINEPERPAGES; 
	
			String order = "ORDER BY "+pc.getColumn()+" "+pc.getDirection();
			return entityManager.createQuery("SELECT computer FROM Computer as computer "
					+ "LEFT JOIN computer.company company WITH company.name LIKE :search WHERE computer.name LIKE :search " + order)
					.setParameter("search", "%" + pc.getFilter() + "%")
					.setFirstResult(idBegin)
					.setMaxResults(nbLines)
					.getResultList();
	}
	
	public Computer retrieveByComputerId(long idComputer){
		return entityManager.find(Computer.class, idComputer);
	}

	public long insert(Computer computer){
		entityManager.persist(computer);
		return computer.getId();
	}

	public void delete(long id){
		Computer computer = entityManager.find(Computer.class, id);
		entityManager.remove(computer);	
	}

	public void update(Computer computer){
		entityManager.merge(computer);
	}

	public long numberByFilter(String filter){
		return (Long)entityManager.createQuery("SELECT COUNT(computer) FROM Computer as computer LEFT JOIN computer.company company WITH company.name LIKE :search WHERE computer.name LIKE :search")
				.setParameter("search", "%" + filter + "%")
				.getSingleResult();
	}

}
