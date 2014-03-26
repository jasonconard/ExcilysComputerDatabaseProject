package com.excilys.project.computerdatabase.services;

import java.sql.SQLException;

import com.excilys.project.computerdatabase.common.Page;
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

			pc.setListElement(computerDAO.retrieveAllByWrapper(pc));
			pc.setNumber(computerDAO.numberByFilter(pc.getFilter()));
			
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
	
	public Computer getComputer(long computerId){
		String message = "Computer searching with computerId("+computerId+")";
		Computer computer = null;
		
		try{
			ConnectionManager.INSTANCE.getConnection();

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
	
	public int insert(Computer computer){
		String message = "Computer insert";
		int id = -1;
		try{
			ConnectionManager.INSTANCE.getConnection();
			
			id = computerDAO.insert(computer);
			
			ConnectionManager.INSTANCE.commit(message);
		}catch(SQLException sqle){
			ConnectionManager.INSTANCE.rollback(message);
		}finally{
			ConnectionManager.INSTANCE.closeConnection();
		}
		return id;
	}
	
	public void update(Computer computer){
		String message = "Computer update";
		try{
			ConnectionManager.INSTANCE.getConnection();
			
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

			computerNumber = computerDAO.numberByFilter(filter);

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
