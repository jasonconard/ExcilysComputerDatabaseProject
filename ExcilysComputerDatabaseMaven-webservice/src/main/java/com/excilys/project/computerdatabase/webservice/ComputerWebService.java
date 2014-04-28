package com.excilys.project.computerdatabase.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.services.ComputerServices;

@WebService
public class ComputerWebService {

	@Autowired
	ComputerServices computerServices;

	@WebMethod
	public List<Computer> findAll() {
		return computerServices.findAll();
	}
}