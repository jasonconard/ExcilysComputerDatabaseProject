package com.excilys.project.computerdatabase.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.persistence.ComputerDAO;

@Service
@Transactional
public class ComputerServices {
	
	@Autowired
	ComputerDAO computerDAO;
	public void setComputerDAO(ComputerDAO computerDAO){
		this.computerDAO = computerDAO;
	}
	
	@Transactional(readOnly = true)
	public Page<Computer> getAllComputers(Page<Computer> page){
		page.setListElement(computerDAO.retrieveAllByWrapper(page));
		page.setNumber(computerDAO.numberByFilter(page.getFilter()));
		return page;
	}
	
	@Transactional(readOnly = true)
	public Computer getComputer(long computerId){
		return computerDAO.retrieveByComputerId(computerId);
	}
	
	@Transactional
	public long insert(Computer computer){
		return computerDAO.insert(computer);
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
	public long getComputerNumber(String filter) {
		return computerDAO.numberByFilter(filter);
	}
}
