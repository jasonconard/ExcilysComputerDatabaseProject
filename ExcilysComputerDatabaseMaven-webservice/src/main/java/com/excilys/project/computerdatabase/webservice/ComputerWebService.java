package com.excilys.project.computerdatabase.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.mapper.ComputerMapper;
import com.excilys.project.computerdatabase.services.ComputerServices;

@Component
@Path("/webservices")
public class ComputerWebService {

	@Autowired
	public ComputerServices computerService;

	@Autowired
	public ComputerMapper computerMapper;

	@GET
	@Produces("application/xml")
	public List<Computer> findAll() {
		return computerService.findAll();
	}
}