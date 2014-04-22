package com.excilys.project.computerdatabase.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.domain.QComputer;
import com.excilys.project.computerdatabase.mapper.ComputerMapper;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class ComputerDAO {

	@Autowired
	ComputerMapper computerMapper;
	public void setComputerMapper(ComputerMapper computerMapper){
		this.computerMapper = computerMapper;
	}

	@PersistenceContext(unitName="entityManagerFactory")
	EntityManager entityManager;

	public List<Computer> retrieveAllByWrapper(Page<Computer> pc){

			if(!pc.getDirection().equals("ASC") && !pc.getDirection().equals("DESC")){
				pc.setDirection("ASC");
			}
			
			int idBegin = pc.getNumero() * Page.NBLINEPERPAGES; 			
			int nbLines = Page.NBLINEPERPAGES; 
			
			String column = pc.getColumn();
			if(column.equals("name")){
				column = "computer.name";
			}
//			
			JPAQuery query = new JPAQuery(entityManager);

			String filter = pc.getFilter();
			
			QComputer computer = QComputer.computer;
			query = query.from(computer).leftJoin(computer.company);
			if(!"".equals(pc.getFilter())){
				filter = new StringBuilder("%").append(filter).append("%").toString();
				query = query.where(computer.name.like(filter).or(computer.company.name.like(filter)));
			}
			Ordered myOrder = new Ordered(pc.getDirection(),pc.getColumn());
			query = query.orderBy(myOrder.getMySpecifier());
			query = query.limit(nbLines);
			query = query.offset(idBegin);
			return query.list(computer);
			
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
		JPAQuery query = new JPAQuery(entityManager);
		QComputer computer = QComputer.computer;
		query = query.from(computer);
		if(!"".equals(filter)){
			filter = new StringBuilder("%").append(filter).append("%").toString();
			query = query.where(computer.name.like(filter).or(computer.company.name.like(filter)));
		}
		return query.count();
	}

}