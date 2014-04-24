package com.excilys.project.computerdatabase.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.excilys.project.computerdatabase.common.OrderBy;
import com.excilys.project.computerdatabase.common.ComputerPage;
import com.excilys.project.computerdatabase.common.UsefulFunctions;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.mapper.ComputerMapper;
import com.excilys.project.computerdatabase.services.CompanyServices;
import com.excilys.project.computerdatabase.services.ComputerServices;
import com.excilys.project.computerdatabase.validator.ComputerValidator;

@Controller
public class ComputerController{

	@Autowired
	ComputerServices computerServices;
	public void setComputerServices(ComputerServices computerServices) {
		this.computerServices = computerServices;
	}

	@Autowired
	CompanyServices companyServices;
	public void setCompanyServices(CompanyServices companyServices) {
		this.companyServices = companyServices;
	}
	
	@Autowired
	ComputerMapper computerMapper;
	public void setComputerMapper(ComputerMapper computerMapper){
		this.computerMapper = computerMapper;
	}
	
	@Autowired
	private ResourceBundleMessageSource messageSource;	
	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@InitBinder
	public void initBinderAll(WebDataBinder binder) {
		binder.addValidators(new ComputerValidator());
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(){
		return "redirect:DashBoard";
	}

	@RequestMapping(value = "DashBoard", method = RequestMethod.GET)
	public String listComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		/* From ADD and EDIT Computer Servlet */
		String idAttString = request.getParameter("computerIdMessage");
		Computer lastEdit = null;
		lastEdit = computerServices.getComputer(UsefulFunctions.stringToLong(idAttString, 0));

		String message = request.getParameter("message");
		String computerIdMessage = request.getParameter("computerIdMessage");
		if(message!=null){
			if(message.equals("update")){
				request.setAttribute("message", messageSource.getMessage("controller.dashboard.updated", null, response.getLocale()));
			}else if(message.equals("add")){
				request.setAttribute("message", messageSource.getMessage("controller.dashboard.added", null, response.getLocale()));
			}else if(message.equals("unavailable")){
				request.setAttribute("error", messageSource.getMessage("controller.dashboard.unavailable", null, response.getLocale())+computerIdMessage+".");
			}
		}

		/* Deleting managment */
		String idString = request.getParameter("computerId");		
		String delete 	= request.getParameter("delete");

		if(idString != null && delete!=null && delete.equals("delete")){
			if(UsefulFunctions.isANumber(idString)){
				long id = Long.parseLong(idString);
				computerServices.delete(id);
				return "dashboard";
			}
		}

		/* Order By managment */
		String order = request.getParameter("order");

		if(order == null || order.length()==0){
			order = "name";
		}

		String dir = request.getParameter("dir");
		if(dir == null || (!dir.equals("ASC") && !dir.equals("DESC"))){
			dir = "ASC";
		}

		/* Searching managment */
		String search = null;
		if(lastEdit == null){
			search = request.getParameter("search");
		}else{
			search = lastEdit.getName();
		}

		if(search==null){
			search = "";
		}

		/* Pagination managment */
		String idPageString = request.getParameter("page");

		int idPage = 0;
		if(idPageString != null && idPageString.length() != 0){
			try{
				idPage = Integer.parseInt(idPageString);
			}catch(NumberFormatException e){
				idPage = 0;
			}
		}		

		Page<Computer> pageComputer;
		Pageable page = new ComputerPage(idPage, 15, OrderBy.get(order, dir));
		pageComputer = computerServices.getAllComputers(page, search);
		long nbPage = pageComputer.getNumberOfElements()/15;

		request.setAttribute("nbPage", nbPage);

		idPage = pageComputer.getNumber();
		
		if(idPage<pageComputer.getTotalPages()-1){
			request.setAttribute("nextPage", idPage+1);
		}else{
			request.setAttribute("nextPage", -1);
		}
		if(idPage>=1){
			request.setAttribute("lastPage", idPage-1);
		}else{
			request.setAttribute("lastPage", -1);
		}

		
		request.setAttribute("page", pageComputer);
		request.setAttribute("filter", search);
		request.setAttribute("column", order);
		request.setAttribute("direction", dir);

		/* Redirection */
		return "dashboard";
	}
	
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {
 
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Hello World");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");
 
		return model;
 
	}
	
	@RequestMapping(value = "ErrorPage404", method = RequestMethod.GET)
	public String error404(){
		return "404";
	}
	
	@RequestMapping(value = "ErrorPage500", method = RequestMethod.GET)
	public String error500(){
		return "500";
	}
}
