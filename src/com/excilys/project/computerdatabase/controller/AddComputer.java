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
		String introduced = request.getParameter("introducedDate");
		String discontinued = request.getParameter("discontinuedDate");
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
		/* Parameters retrieving */
		String name = request.getParameter("name");
		String introducedDateString =  request.getParameter("introducedDate");
		String discontinuedDateString =  request.getParameter("discontinuedDate");
		String companyIdString =  request.getParameter("company");
		
		Company company = null;
		
		/* Company searching by ID */
		long companyId = Long.parseLong(companyIdString);
		company = companyServices.getCompany(companyId);
		
		ComputerDTO cdto = new ComputerDTO.ComputerDTOBuilder(0, name)
		.introduced(introducedDateString)
		.discontinued(discontinuedDateString)
		.company(company)
		.build();
		
		String error = ComputerValidator.validate(cdto);
		
		/* Validation case */
		if(error.length()==0){
			Computer computer = ComputerMapper.dtoToObject(cdto);
			computerServices.insert(computer);
			String message = "Computer Added";
			request.setAttribute("message", message);
		}
		
		/* Error case */
		if(error.length()>0){
			request.setAttribute("error", error);
		}
		
		/* Loading AddComputer page */
		doGet(request,response);
		
		/*List<Company> allCompanies = null;
		allCompanies = companyDao.selectAllCompany();
		request.setAttribute("allCompany", allCompanies);
		*/
		//request.getRequestDispatcher("AddComputer").forward(request, response);
		//response.sendRedirect("AddComputer");
		
		
	}

}
