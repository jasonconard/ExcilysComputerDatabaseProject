package com.excilys.project.computerdatabase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.repository.ComputerRepository;

@Service
@Transactional
public class ComputerServices {
	
	@Autowired
	ComputerRepository computerRepository;
	
	@Transactional(readOnly = true)
	public Page<Computer> getAllComputers(Pageable page, String filter){
		Page<Computer> pageComputer = computerRepository.findAll("%"+filter+"%",page);
		return pageComputer;
	}
	
	@Transactional(readOnly = true)
	public List<Computer> findAll() {
		 return computerRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Computer getComputer(long computerId){
		return computerRepository.findOne(computerId);
	}
	
	@Transactional
	public long insert(Computer computer){
		computerRepository.save(computer);
		return computer.getId();
	}
	
	@Transactional
	public void update(Computer computer){	
		computerRepository.save(computer);
	}
	
	@Transactional
	public void delete(long computerId){
		computerRepository.delete(computerId);
	}
}
