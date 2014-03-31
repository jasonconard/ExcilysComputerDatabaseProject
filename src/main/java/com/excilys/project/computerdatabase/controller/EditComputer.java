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
public class EditComputer extends HttpServlet {
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
			long id = -1;
			try{
				id = Long.parseLong(idString);				
			}catch(NumberFormatException e){
				response.sendRedirect("DashBoard?message=unavailable&computerIdMessage="+idString);
			}
			
			Computer computer = computerServices.getComputer(id);
			
			if(computer != null){
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
					try{
						long companyId = Long.parseLong(companyIdString);
						if(companyId > 0){
							Company company = companyServices.getCompany(companyId); 
							cdto.setCompanyId(company.getId());
							cdto.setCompanyName(company.getName());
						}
					}catch(NumberFormatException e){
					}
	
				}
	
				if(cdto!=null){
					request.setAttribute("computer",cdto);
				}
				
				List<Company> allCompany = null;
				allCompany = companyServices.getAllCompanies();
				request.setAttribute("allCompany", allCompany);
				request.getRequestDispatcher("WEB-INF/editComputer.jsp").forward(request, response);
			}else{
				response.sendRedirect("DashBoard?message=unavailable&computerIdMessage="+idString);
			}
		}

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

		Company company = companyServices.getCompany(companyId);

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
			computerServices.update(neoComputer);
			message.append("Computer modified");
			response.sendRedirect("DashBoard?message=update&computerIdMessage="+id);
		}else{
			request.setAttribute("error", error);
			request.setAttribute("computerId", ""+id);
			doGet(request, response);
		}

	}

}
