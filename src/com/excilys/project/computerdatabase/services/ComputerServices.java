package com.excilys.project.computerdatabase.services;

import java.sql.SQLException;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.persistence.ComputerDAO;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;

public class ComputerServices {
	
	public static ComputerServices instance = null;
	
	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	
	public Page<Computer> getAllComputers(Page<Computer> pc){
		String message = "Computers searching";
		try{
			ConnectionManager.INSTANCE.getConnection();
			ConnectionManager.INSTANCE.startTransaction();

			pc.setListElement(computerDAO.retrieveAllWithCompanyNameByWrapper(pc));
			pc.setNumber(computerDAO.computerNumberByFilter(pc.getFilter()));
			
			if(pc.getListElement()!=null){	  
				ConnectionManager.INSTANCE.commit(message);
			}else{
				ConnectionManager.INSTANCE.rollback(message+" (computers is null)");
			}
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(message);
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}
		
		return pc;
	}
	
	public Company getCompany(long computerId){
		String message = "Companies searching with computerId("+computerId+")";
		Company company = null;
		
		try{
			ConnectionManager.INSTANCE.getConnection();
			ConnectionManager.INSTANCE.startTransaction();

			company = computerDAO.retrieveCompanyByComputerId(computerId);

			if(company!=null){	  
				ConnectionManager.INSTANCE.commit(message);
			}else{
				ConnectionManager.INSTANCE.rollback(message+" (company does not exists)");
			}
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(message);
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}
		
		return company;
	}
	
	public Computer getComputer(long computerId){
		String message = "Computer searching with computerId("+computerId+")";
		Computer computer = null;
		
		try{
			ConnectionManager.INSTANCE.getConnection();
			ConnectionManager.INSTANCE.startTransaction();

			computer = computerDAO.retrieveByComputerId(computerId);

			if(computer!=null){	  
				ConnectionManager.INSTANCE.commit(message);
			}else{
				ConnectionManager.INSTANCE.rollback(message+" (computer does not exists)");
			}
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(message);
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}
		
		return computer;
	}
	
	public void insert(Computer computer){
		String message = "Computer insert";
		try{
			ConnectionManager.INSTANCE.getConnection();
			ConnectionManager.INSTANCE.startTransaction();
			computerDAO.insert(computer);
			ConnectionManager.INSTANCE.commit(message);
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(message);
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}
	}
	
	public void update(Computer computer){
		String message = "Computer update";
		try{
			ConnectionManager.INSTANCE.getConnection();
			ConnectionManager.INSTANCE.startTransaction();
			computerDAO.update(computer);
			ConnectionManager.INSTANCE.commit(message);		
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(message);
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}
	}
	
	public void delete(long computerId){
		String message = "Computer removal";
		try{
			ConnectionManager.INSTANCE.getConnection();
			ConnectionManager.INSTANCE.startTransaction();
			computerDAO.delete(computerId);
			ConnectionManager.INSTANCE.commit(message);
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(message);
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}
		
	}

	public int getComputerNumber(String filter) {
		String message = "Computer number searching";
		int computerNumber = -1;
		
		try{
			ConnectionManager.INSTANCE.getConnection();
			ConnectionManager.INSTANCE.startTransaction();
			computerNumber = computerDAO.computerNumberByFilter(filter);

			if(computerNumber>=0){	  
				ConnectionManager.INSTANCE.commit(message);
			}else{
				ConnectionManager.INSTANCE.rollback(message+" (negative number)");
			}
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(message);
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}
		
		return computerNumber;
	}
	
	synchronized public static ComputerServices getInstance(){
		if(instance == null){
			instance = new ComputerServices();
		}
		return instance;
	}
}
