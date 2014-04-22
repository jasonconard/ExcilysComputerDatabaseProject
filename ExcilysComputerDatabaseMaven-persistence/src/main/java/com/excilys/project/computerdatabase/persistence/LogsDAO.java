package com.excilys.project.computerdatabase.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.excilys.project.computerdatabase.domain.Logs;

@Repository
public class LogsDAO {

	@PersistenceContext(unitName="entityManagerFactory")
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Logs> retrieveAll(){
		return ((List<Logs>)entityManager.createQuery("SELECT logs FROM Logs as logs").getResultList());
	}

	public Logs retrieveByLogId(long idLog){
		return entityManager.find(Logs.class, idLog);
	}

	public void insert(String description, String type){
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ")
	     .append("logs")
	     .append(" VALUES(0,")
	     .append(description)
	     .append(",")
	     .append(type)
	     .append(",LOCALTIME())");	
		entityManager.createQuery(query.toString());
	}
}