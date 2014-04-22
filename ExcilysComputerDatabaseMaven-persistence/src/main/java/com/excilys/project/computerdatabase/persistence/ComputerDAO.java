package com.excilys.project.computerdatabase.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
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
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Computer> retrieveAllByWrapper(Page<Computer> pc){
	
			int idBegin = pc.getNumero() * Page.NBLINEPERPAGES; 
			int nbLines = Page.NBLINEPERPAGES; 
			
			String column = "name";
			if(pc.getColumn()!=null){
				column = pc.getColumn();
			}
			
			Order order = null;
			if(pc.getDirection().equals("DESC")){
				order = Order.desc(column);
			}else{
				order = Order.asc(column);
			}
			
			Session session = sessionFactory.getCurrentSession();
			List<Computer> computers = session.createCriteria(Computer.class)
						.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN)
						.addOrder(order)
						.add(Restrictions.or(Restrictions.like("name", "%"+pc.getFilter()+"%"), Restrictions.like("company.name", "%"+pc.getFilter()+"%")))
						.setMaxResults(nbLines)
						.setFirstResult(idBegin).list();
			return computers;
	}
	
	public Computer retrieveByComputerId(long idComputer){
		Session session = sessionFactory.getCurrentSession();
		Computer computer = (Computer)session.get(Computer.class, idComputer);
		return computer;
	}

	public long insert(Computer computer){
		Session session = sessionFactory.getCurrentSession();
		session.save(computer);
		return computer.getId();
	}

	public void delete(long id){
		Session session = sessionFactory.getCurrentSession();
		Computer computer = (Computer)session.get(Computer.class, id);
		session.delete(computer);
	}

	public void update(Computer computer){
		Session session = sessionFactory.getCurrentSession();
		session.update(computer);
	}

	public long numberByFilter(String filter){
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Computer.class)
				.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.or(Restrictions.like("name", "%"+filter+"%"), Restrictions.like("company.name", "%"+filter+"%")))
				.list().size();
	}

}
