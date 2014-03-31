package com.excilys.project.computerdatabase.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
	
	@Autowired
	ComputerServices computerServices;

	@Autowired
	CompanyServices companyServices;
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
	}
	
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
		request.setAttribute("allCompany", allCompany);
		
		String name = request.getParameter("name");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyIdString =  request.getParameter("company");
		if(name != null){
			request.setAttribute("name", name);
		}
		if(introduced != null){
			request.setAttribute("introduced", introduced);
		}
		if(discontinued != null){
			request.setAttribute("discontinued", discontinued);
		}
		if(companyIdString != null){
			request.setAttribute("companyId", companyIdString);
		}
		
		request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Parameters searching
		String name = request.getParameter("name");
		String introducedDateString =  request.getParameter("introduced");
		String discontinuedDateString =  request.getParameter("discontinued");
		String companyIdString =  request.getParameter("company");

		/* Company searching by ID */
		long companyId = Long.parseLong(companyIdString);
		Company company = companyServices.getCompany(companyId);

		long id = 0;
		
		String companyName = null;
		if(company!=null){
			companyName = company.getName();
		}
		
		ComputerDTO cdto = new ComputerDTO.ComputerDTOBuilder(id, name)
		.introduced(introducedDateString)
		.discontinued(discontinuedDateString)
		.companyId(companyId)
		.companyName(companyName)
		.build();

		String error = ComputerValidator.validate(cdto);
		
		
		/* Validation case */
		if(error.length()==0){
			Computer neoComputer = ComputerMapper.dtoToObject(cdto);
			StringBuilder message = new StringBuilder();
			id = computerServices.insert(neoComputer);
			if(id >= 0){
				message.append("Computer added");
				response.sendRedirect("DashBoard?message=add&computerIdMessage="+id);
			}else{
				request.setAttribute("error", "Database error, please contact the support.");
				request.setAttribute("computerId", ""+id);
				doGet(request, response);
			}
			
		}else{
			request.setAttribute("error", error);
			request.setAttribute("computerId", ""+id);
			doGet(request, response);
		}
	}
}
