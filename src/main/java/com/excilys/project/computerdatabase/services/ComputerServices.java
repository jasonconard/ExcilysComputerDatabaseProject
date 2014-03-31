package com.excilys.project.computerdatabase.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.persistence.ComputerDAO;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;

@Service
public class ComputerServices {
	
	public static ComputerServices instance = null;
	synchronized public static ComputerServices getInstance(){
		if(instance == null){
			instance = new ComputerServices();
		}
		return instance;
	}
	
	@Autowired
	ConnectionManager connectionManager;
	public void setConnectionManager(ConnectionManager connectionManager){
		this.connectionManager = connectionManager;
	}
	
	@Autowired
	ComputerDAO computerDAO;
	public void setComputerDAO(ComputerDAO computerDAO){
		this.computerDAO = computerDAO;
	}
	
	public Page<Computer> getAllComputers(Page<Computer> pc){
		String message = "Computers searching";
		try{
			connectionManager.getConnection();

			pc.setListElement(computerDAO.retrieveAllByWrapper(pc));
			pc.setNumber(computerDAO.numberByFilter(pc.getFilter()));
			
			if(pc.getListElement()!=null){	  
				connectionManager.commit(message);
			}else{
				connectionManager.rollback(message+" (computers is null)");
			}
		}catch(SQLException sqle){
			connectionManager.rollback(message);
		}finally{
			connectionManager.closeConnection();
		}
		
		return pc;
	}
	
	public Computer getComputer(long computerId){
		String message = "Computer searching with computerId("+computerId+")";
		Computer computer = null;
		
		try{
			connectionManager.getConnection();

			computer = computerDAO.retrieveByComputerId(computerId);

			if(computer!=null){	  
				connectionManager.commit(message);
			}else{
				connectionManager.rollback(message+" (computer does not exists)");
			}
		}catch(SQLException sqle){
			connectionManager.rollback(message);
		}finally{
			connectionManager.closeConnection();
		}
		
		return computer;
	}
	
	public int insert(Computer computer){
		String message = "Computer insert";
		int id = -1;
		try{
			connectionManager.getConnection();
			
			id = computerDAO.insert(computer);
			
			connectionManager.commit(message);
		}catch(SQLException sqle){
			connectionManager.rollback(message);
		}finally{
			connectionManager.closeConnection();
		}
		return id;
	}
	
	public void update(Computer computer){
		String message = "Computer update";
		try{
			connectionManager.getConnection();
			
			computerDAO.update(computer);
			
			connectionManager.commit(message);		
		}catch(SQLException sqle){
			connectionManager.rollback(message);
		}finally{
			connectionManager.closeConnection();
		}
	}
	
	public void delete(long computerId){
		String message = "Computer removal";
		try{
			connectionManager.getConnection();
			
			computerDAO.delete(computerId);
			
			connectionManager.commit(message);
		}catch(SQLException sqle){
			connectionManager.rollback(message);
		}finally{
			connectionManager.closeConnection();
		}
		
	}

	public int getComputerNumber(String filter) {
		String message = "Computer number searching";
		int computerNumber = -1;
		
		try{
			connectionManager.getConnection();

			computerNumber = computerDAO.numberByFilter(filter);

			if(computerNumber>=0){	  
				connectionManager.commit(message);
			}else{
				connectionManager.rollback(message+" (negative number)");
			}
		}catch(SQLException sqle){
			connectionManager.rollback(message);
		}finally{
			connectionManager.closeConnection();
		}
		
		return computerNumber;
	}
}
