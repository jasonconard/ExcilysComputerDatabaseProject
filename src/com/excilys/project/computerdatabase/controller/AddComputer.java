package com.excilys.project.computerdatabase.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.dto.ComputerDTO;
import com.excilys.project.computerdatabase.mapper.ComputerMapper;
import com.excilys.project.computerdatabase.services.CompanyServices;
import com.excilys.project.computerdatabase.services.ComputerServices;
import com.excilys.project.computerdatabase.validator.ComputerValidator;

/**
 * Servlet implementation class AddComputer
 */
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	CompanyServices companyServices = CompanyServices.getInstance();
	ComputerServices computerServices = ComputerServices.getInstance();
	
	private static final String ATTR_ALL_COMPANY = "allCompany";
	private static final String ATTR_NAME = "name";
	private static final String ATTR_INTR = "introduced";
	private static final String ATTR_DISC = "discontinued";
	private static final String ATTR_COMPA = "company";
	private static final String ATTR_COMPA_ID = "companyId";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> allCompany = null;
		allCompany = companyServices.getAllCompanies();
		request.setAttribute(ATTR_ALL_COMPANY, allCompany);
		
		String name = request.getParameter(ATTR_NAME);
		String introduced = request.getParameter(ATTR_INTR);
		String discontinued = request.getParameter(ATTR_DISC);
		String companyIdString =  request.getParameter(ATTR_COMPA);
		if(name != null){
			request.setAttribute(ATTR_NAME, name);
		}
		if(introduced != null){
			request.setAttribute(ATTR_INTR, introduced);
		}
		if(discontinued != null){
			request.setAttribute(ATTR_DISC, discontinued);
		}
		if(companyIdString != null){
			request.setAttribute(ATTR_COMPA_ID, companyIdString);
		}
		
		request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Searching for all companies		
		List<Company> allCompany = null;
		allCompany = companyServices.getAllCompanies();
		request.setAttribute(ATTR_ALL_COMPANY, allCompany);

		// Parameters searching
		String name = request.getParameter(ATTR_NAME);
		String introducedDateString =  request.getParameter(ATTR_INTR);
		String discontinuedDateString =  request.getParameter(ATTR_DISC);
		String companyIdString =  request.getParameter(ATTR_COMPA);

		/* Company searching by ID */
		long companyId = Long.parseLong(companyIdString);
		Company company = companyServices.getCompany(companyId);

		long id = 0;
		
		ComputerDTO cdto = new ComputerDTO.ComputerDTOBuilder(id, name)
		.introduced(introducedDateString)
		.discontinued(discontinuedDateString)
		.company(company)
		.build();

		String error = ComputerValidator.validate(cdto);
		
		
		/* Validation case */
		if(error.length()==0){
			Computer neoComputer = ComputerMapper.dtoToObject(cdto);
			StringBuilder message = new StringBuilder();
			id = computerServices.insert(neoComputer);
			message.append("Computer added");
			response.sendRedirect("DashBoard?message=add&computerIdMessage="+id);
		}else{
			request.setAttribute("error", error);
			request.setAttribute("computerId", ""+id);
			doGet(request, response);
		}
	}
}
