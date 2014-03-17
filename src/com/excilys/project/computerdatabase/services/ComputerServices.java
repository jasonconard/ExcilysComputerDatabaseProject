package com.excilys.project.computerdatabase.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.persistence.ComputerDAO;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;
import com.excilys.project.computerdatabase.persistence.WrapperComputer;

public class ComputerServices {
	
	public static ComputerServices instance = null;
	
	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	
	public List<Computer> getAllComputers(WrapperComputer wc){
		Connection connection = ConnectionManager.getConnection();
		List<Computer> computers = computerDAO.retrieveAllWithCompanyNameByWrapper(wc, connection);
		
		close(connection);
		
		return computers;
	}
	
	public Company getCompany(long computerId){
		Connection connection = ConnectionManager.getConnection();
		Company company = computerDAO.retrieveCompanyByComputerId(computerId, connection);
		
		close(connection);
		
		return company;
	}
	
	public Computer getComputer(long computerId){
		Connection connection = ConnectionManager.getConnection();
		Computer computer = computerDAO.retrieveByComputerId(computerId, connection);
		
		close(connection);
		
		return computer;
	}
	
	public void insert(Computer computer){
		Connection connection = ConnectionManager.getConnection();
		
		computerDAO.insert(computer, connection);
		
		close(connection);
	}
	
	public void update(Computer computer){
		Connection connection = ConnectionManager.getConnection();
		
		computerDAO.update(computer, connection);
		
		close(connection);
	}
	
	public void delete(long computerId){
		Connection connection = ConnectionManager.getConnection();
		
		computerDAO.delete(computerId, connection);
		
		close(connection);
	}

	public int getComputerNumber(String filter) {
		Connection connection = ConnectionManager.getConnection();
		
		int computerNumber = computerDAO.computerNumberByFilter(filter,connection);
		
		close(connection);
		
		return computerNumber;
	}
	
	private void close(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	synchronized public static ComputerServices getInstance(){
		if(instance == null){
			instance = new ComputerServices();
		}
		return instance;
	}
}
