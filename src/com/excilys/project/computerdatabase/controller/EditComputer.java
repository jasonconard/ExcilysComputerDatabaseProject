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
       
	CompanyServices companyServices = CompanyServices.getInstance();
	ComputerServices computerServices = ComputerServices.getInstance();
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
		String introducedDateString =  request.getParameter("introducedDate");
		String discontinuedDateString =  request.getParameter("discontinuedDate");
		String companyIdString =  request.getParameter("company");
		
		if(idString == null){
			idString = request.getParameter("idComputer");
		}
		if(idString != null && idString.length()>0){
			long id = Long.parseLong(idString);
			Computer computer = computerServices.getComputer(id);
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
					Company company = companyServices.getCompany(companyId); 
					cdto.setCompany(company);
				}
			}
			
			if(cdto!=null){
				request.setAttribute("computer",cdto);
			}
		}
		
		List<Company> allCompany = null;
		allCompany = companyServices.getAllCompanies();
		request.setAttribute("allCompany", allCompany);
		request.getRequestDispatcher("WEB-INF/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Searching for all companies		
		List<Company> allCompany = null;
		allCompany = companyServices.getAllCompanies();
		request.setAttribute("allCompany", allCompany);
		
		// Parameters searching
		String idString = request.getParameter("idComputer");
		String name = request.getParameter("name");
		String introducedDateString =  request.getParameter("introducedDate");
		String discontinuedDateString =  request.getParameter("discontinuedDate");
		String companyIdString =  request.getParameter("company");
				
		/* Company searching by ID */
		long companyId = Long.parseLong(companyIdString);
		Company company = companyServices.getCompany(companyId);
		
		long id = Long.parseLong(idString);
		
		ComputerDTO cdto = new ComputerDTO.ComputerDTOBuilder(id, name)
		.introduced(introducedDateString)
		.discontinued(discontinuedDateString)
		.company(company)
		.build();
			
		String error = ComputerValidator.validate(cdto);
		
		/* Validation case */
		if(error.length()==0){
			Computer neoComputer = ComputerMapper.dtoToObject(cdto);
			computerServices.update(neoComputer);
			String message = "Computer modified";
			request.setAttribute("message", message);
		}
		
		/* Error case */
		if(error.length()>0){
			request.setAttribute("error", error);
		}
		
		doGet(request,response);
		/*if(idString != null && idString.length()>0){
			long idRetrieve = Long.parseLong(idString);
			Computer computer = computerDao.retrieveByComputerId(idRetrieve);
			if(computer!=null){
				request.setAttribute("computer",computer);
			}
		}
		*/
		//response.sendRedirect("EditComputer");
		//request.getRequestDispatcher("EditComputer").forward(request, response);
	}

}
