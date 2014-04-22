package com.excilys.project.computerdatabase.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.project.computerdatabase.domain.Logs;

@Repository
public class LogsDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<Logs> retrieveAll(){
		Session session = sessionFactory.getCurrentSession();
		return  session.createQuery("FROM company").list(); 
	}

	public Logs retrieveByLogId(long idLog){
		Session session = sessionFactory.getCurrentSession();
		Logs logs = (Logs)session.get(Logs.class, idLog);
		return logs;
	}

	public long insert(Logs logs){
		logs.setDateLogs(new LocalDate(LocalDate.now()));
		Session session = sessionFactory.getCurrentSession();
		session.save(logs);
		return logs.getId();
	}
}
