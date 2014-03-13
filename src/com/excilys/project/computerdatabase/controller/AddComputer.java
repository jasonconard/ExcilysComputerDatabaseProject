package com.excilys.project.computerdatabase.controller;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.persistence.CompanyDAO;
import com.excilys.project.computerdatabase.persistence.ComputerDAO;

/**
 * Servlet implementation class AddComputer
 */
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	CompanyDAO companyDao = CompanyDAO.getInstance();
	ComputerDAO computerDao = ComputerDAO.getInstance();
	
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
		allCompany = companyDao.retrieveAll();
		request.setAttribute("allCompany", allCompany);
		request.getRequestDispatcher("addComputer.jsp").forward(request, response);
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
		
		Date introducedDate = null;
		Date discontinuedDate = null;
		Company company = null;
		
		
		/* Company searching by ID */
		long companyId = Long.parseLong(companyIdString);
		company = companyDao.retrieveByCompanyId(companyId);
		
		
		/* Error searching */
		String error = ""; 
		if(introducedDateString!=null && introducedDateString.length()>0){
			introducedDate = UsefulFunctions.stringToDate(introducedDateString);
			if(introducedDate == null){
				error += "Introduced date is not correct ("+introducedDateString+").<br/>";
			}
		}
		if(discontinuedDateString!=null && discontinuedDateString.length()>0){
			discontinuedDate = UsefulFunctions.stringToDate(discontinuedDateString);
			if(introducedDate == null){
				error += "Discontinued date is not correct ("+discontinuedDateString+").<br/>";
			}
		}
		if( name==null || name.length()==0 ){
			error += "Computer name is required.<br/>";
		}
		
		
		/* Validation case */
		if(error.length()==0){
			Computer computer = null;
			if(company!=null){
				computer = new Computer.ComputerBuilder(0, name)
					.introduced(introducedDate)
		            .discontinued(discontinuedDate)
		            .company(
		            		new Company.CompanyBuilder(company.getId())
		            		.name(company.getName())
		            		.build()
		            		)
		            .build();
			}else{
				computer = new Computer.ComputerBuilder(0, name)
					.introduced(introducedDate)
		            .discontinued(discontinuedDate)
		            .build();
			}
			computerDao.insert(computer);
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
		
		request.getRequestDispatcher("addComputer.jsp").forward(request, response);*/
	}

}
