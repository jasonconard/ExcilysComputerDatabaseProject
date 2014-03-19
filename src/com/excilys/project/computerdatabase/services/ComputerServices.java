package com.excilys.project.computerdatabase.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.persistence.ComputerDAO;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;

public class ComputerServices {
	
	public static ComputerServices instance = null;
	
	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	private LogsServices logsServices = LogsServices.getInstance();
	
	public Page<Computer> getAllComputers(Page<Computer> pc){
		Connection connection = null;
		try{
			connection = ConnectionManager.getConnection(); 
			connection.setAutoCommit(false);

			pc.setList  (computerDAO.retrieveAllWithCompanyNameByWrapper(pc, connection));
			pc.setNumber(computerDAO.computerNumberByFilter(pc.getFilter(),connection));

			
			if(pc.getList()!=null){	  
				connection.commit();
				connection.setAutoCommit(true);
				logsServices.insert("Computers searching completed", "Complete");
			}else{
				connection.rollback();
				logsServices.insert("Computers searching error : computers is null", "Error");
			}
		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
			logsServices.insert("Computers searching error", "Error");
		}finally{
			try{connection.close();}catch(Exception e){}
		}
		
		return pc;
	}
	
	public Company getCompany(long computerId){
		Connection connection = null;
		Company company = null;
		
		try{
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);

			company = computerDAO.retrieveCompanyByComputerId(computerId, connection);

			if(company!=null){	  
				connection.commit();
				connection.setAutoCommit(true);
				logsServices.insert("Company searching with computerId("+computerId+") completed", "Complete");
			}else{
				connection.rollback();
				logsServices.insert("Company searching with computerId("+computerId+") error : company does not exists", "Error");
			}
		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
			logsServices.insert("Company searching with computerId("+computerId+") error", "Error");
		}finally{
			try{connection.close();}catch(Exception e){}
		}
		
		return company;
	}
	
	public Computer getComputer(long computerId){
		Connection connection = null;
		Computer computer = null;
		
		try{
			connection = ConnectionManager.getConnection(); 
			connection.setAutoCommit(false);

			computer = computerDAO.retrieveByComputerId(computerId, connection);

			if(computer!=null){	  
				connection.commit();
				connection.setAutoCommit(true);
				logsServices.insert("Computer searching with computerId("+computerId+") completed", "Complete");
			}else{
				connection.rollback();
				logsServices.insert("Computer searching with computerId("+computerId+") error : computer does not exists", "Error");
			}
		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
			logsServices.insert("Computer searching with computerId("+computerId+") error", "Error");
		}finally{
			try{connection.close();}catch(Exception e){}
		}
		
		return computer;
	}
	
	public void insert(Computer computer){
		Connection connection = null;
				
		try{
			connection = ConnectionManager.getConnection(); 
			connection.setAutoCommit(false);

			computerDAO.insert(computer, connection);
  
			connection.commit();
			connection.setAutoCommit(true);
			logsServices.insert("Computer insertion completed", "Complete");
		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
			logsServices.insert("Computer insertion error", "Error");
		}finally{
			try{connection.close();}catch(Exception e){}
		}
	}
	
	public void update(Computer computer){
		Connection connection = null;
		
		try{
			connection = ConnectionManager.getConnection(); 
			connection.setAutoCommit(false);

			computerDAO.update(computer, connection);
			
			connection.commit();
			connection.setAutoCommit(true);
			logsServices.insert("Computer update completed", "Complete");			
		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
			logsServices.insert("Computer update error", "Error");
		}finally{
			try{connection.close();}catch(Exception e){}
		}
	}
	
	public void delete(long computerId){
		Connection connection = null;
		
		try{
			connection = ConnectionManager.getConnection(); 
			connection.setAutoCommit(false);

			computerDAO.delete(computerId, connection);
			
			connection.commit();
			connection.setAutoCommit(true);
			logsServices.insert("Computer deleting completed", "Complete");
		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
			logsServices.insert("Computer deleting error", "Error");
		}finally{
			try{connection.close();}catch(Exception e){}
		}
		
	}

	public int getComputerNumber(String filter) {
		Connection connection = null;
		int computerNumber = -1;
		
		try{
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);

			 computerNumber = computerDAO.computerNumberByFilter(filter,connection);

			if(computerNumber>=0){	  
				connection.commit();
				connection.setAutoCommit(true);
				logsServices.insert("Computer number searching completed", "Complete");
			}else{
				connection.rollback();
				logsServices.insert("Computer number searching error : negative number", "Error");
			}
		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
			logsServices.insert("Computer number searching error", "Error");
		}finally{
			try{connection.close();}catch(Exception e){}
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
