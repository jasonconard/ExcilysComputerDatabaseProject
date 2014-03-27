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
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditComputer() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idString = request.getParameter("computerId");

		String name = request.getParameter("name");
		String introducedDateString =  request.getParameter("introduced");
		String discontinuedDateString =  request.getParameter("discontinued");
		String companyIdString =  request.getParameter("company");

		if(idString == null){
			
			idString = request.getParameter("computerId");
			
		}else if(idString.length()>0){
			
			long id = Long.parseLong(idString);
			Computer computer = ComputerServices.INSTANCE.getComputer(id);
			ComputerDTO cdto = ComputerMapper.objectToDto(computer);

			if(name!=null){
				cdto.setName(name);
			}

			if(introducedDateString!=null){
				cdto.setIntroduced(introducedDateString);
			}

			if(discontinuedDateString!=null){
				cdto.setDiscontinued(discontinuedDateString);
			}

			if(companyIdString!=null){
				long companyId = Long.parseLong(companyIdString);
				if(companyId > 0){
					Company company = CompanyServices.INSTANCE.getCompany(companyId); 
					cdto.setCompanyId(company.getId());
					cdto.setCompanyName(company.getName());
				}
			}

			if(cdto!=null){
				request.setAttribute("computer",cdto);
			}
		}

		List<Company> allCompany = null;
		allCompany = CompanyServices.INSTANCE.getAllCompanies();
		request.setAttribute("allCompany", allCompany);
		request.getRequestDispatcher("WEB-INF/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Parameters searching
		String idString = request.getParameter("computerId");
		String name = request.getParameter("name");
		String introducedDateString =  request.getParameter("introduced");
		String discontinuedDateString =  request.getParameter("discontinued");
		String companyIdString =  request.getParameter("company");

		/* Company searching by ID */
		long companyId = Long.parseLong(companyIdString);
		
		Company company = CompanyServices.INSTANCE.getCompany(companyId);

		long id = 0;
		id = Integer.parseInt(idString);

		String companyName = null;
		if(company != null){
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
			ComputerServices.INSTANCE.update(neoComputer);
			message.append("Computer modified");
			response.sendRedirect("DashBoard?message=update&computerIdMessage="+id);
		}else{
			request.setAttribute("error", error);
			request.setAttribute("computerId", ""+id);
			doGet(request, response);
		}

	}

}
