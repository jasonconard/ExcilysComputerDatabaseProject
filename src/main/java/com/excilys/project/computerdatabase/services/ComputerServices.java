package com.excilys.project.computerdatabase.services;

import java.sql.SQLException;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.persistence.ComputerDAO;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;

public enum ComputerServices {
	INSTANCE;
	
	public Page<Computer> getAllComputers(Page<Computer> pc){
		String message = "Computers searching";
		try{
			ConnectionManager.INSTANCE.getConnection();

			pc.setListElement(ComputerDAO.INSTANCE.retrieveAllByWrapper(pc));
			pc.setNumber(ComputerDAO.INSTANCE.numberByFilter(pc.getFilter()));
			
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

			computer = ComputerDAO.INSTANCE.retrieveByComputerId(computerId);

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
			
			id = ComputerDAO.INSTANCE.insert(computer);
			
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
			
			ComputerDAO.INSTANCE.update(computer);
			
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
			
			ComputerDAO.INSTANCE.delete(computerId);
			
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

			computerNumber = ComputerDAO.INSTANCE.numberByFilter(filter);

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
}
