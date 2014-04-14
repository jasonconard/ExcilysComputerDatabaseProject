package com.excilys.project.computerdatabase.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.dto.ComputerDTO;
import com.excilys.project.computerdatabase.persistence.ComputerDAO;

@Service
@Transactional
public class ComputerServices {
	
	public static ComputerServices instance = null;
	synchronized public static ComputerServices getInstance(){
		if(instance == null){
			instance = new ComputerServices();
		}
		return instance;
	}
	
	@Autowired
	ComputerDAO computerDAO;
	public void setComputerDAO(ComputerDAO computerDAO){
		this.computerDAO = computerDAO;
	}
	
	@Transactional(readOnly = true)
	public Page<ComputerDTO> getAllComputers(Page<ComputerDTO> page){
		
		page.setListElement(computerDAO.retrieveAllByWrapper(page));
		page.setNumber(computerDAO.numberByFilter(page.getFilter()));
			
		return page;
	}
	
	@Transactional(readOnly = true)
	public Computer getComputer(long computerId){
		
		Computer computer = null;
		
		computer = computerDAO.retrieveByComputerId(computerId);

		return computer;
		
	}
	
	@Transactional
	public int insert(Computer computer){
		int id = -1;			
		
		id = computerDAO.insert(computer);
			
		return id;
	}
	
	@Transactional
	public void update(Computer computer){
			
		computerDAO.update(computer);
		
	}
	
	@Transactional
	public void delete(long computerId){

		computerDAO.delete(computerId);
		
	}

	@Transactional(readOnly = true)
	public int getComputerNumber(String filter) {
		int computerNumber = -1;
		
		computerNumber = computerDAO.numberByFilter(filter);

		return computerNumber;
	}
}
