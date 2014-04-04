package com.excilys.project.computerdatabase.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.common.UsefulFunctions;
import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.dto.ComputerDTO;
import com.excilys.project.computerdatabase.mapper.ComputerMapper;
import com.excilys.project.computerdatabase.services.CompanyServices;
import com.excilys.project.computerdatabase.services.ComputerServices;
import com.excilys.project.computerdatabase.validator.ComputerValidator;

/**
 * Servlet implementation class DashBoard
 */
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
			order = "cu.name";
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

		int idPage = 1;
		if(idPageString != null && idPageString.length() != 0){
			try{
				idPage = Integer.parseInt(idPageString);
			}catch(NumberFormatException e){
				idPage = 1;
			}
		}		

		Page<ComputerDTO> page = new Page<ComputerDTO>();
		page.setNumero(idPage-1);
		page.setColumn(order);
		page.setDirection(dir);
		page.setFilter(search);

		page = computerServices.getAllComputers(page);

		int nbPage = page.getNumber()/Page.NBLINEPERPAGES+1;

		request.setAttribute("nbPage", nbPage);

		if(idPage<nbPage){
			request.setAttribute("nextPage", idPage+1);
		}else{
			request.setAttribute("nextPage", -1);
		}
		if(idPage>1){
			request.setAttribute("lastPage", idPage-1);
		}else{
			request.setAttribute("lastPage", -1);
		}

		request.setAttribute("page", page);
		request.setAttribute("numero", page.getNumero()+1);

		/* Redirection */
		return "dashboard";
	}

	@RequestMapping(value = "EditComputer", method = RequestMethod.GET)
	public String showEditComputer(
			Model model,
			@ModelAttribute("dto") @Valid ComputerDTO computerDTO, 
			BindingResult result,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws ServletException, IOException{
		
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
				model.addAttribute("message","unavailable");
				model.addAttribute("computerIdMessage",idString);
				return "redirect:DashBoard";
			}

			Computer computer = computerServices.getComputer(id);

			if(computer != null){
				computerDTO = computerMapper.objectToDto(computer);
				if(name != null){	computerDTO.setName(name);	}
				if(introducedDateString != null){ computerDTO.setIntroduced(introducedDateString); 	}
				if(discontinuedDateString != null){ 	computerDTO.setDiscontinued(discontinuedDateString); }
				if(companyIdString != null){
					try{
						long companyId = Long.parseLong(companyIdString);
						if(companyId > 0){
							Company company = companyServices.getCompany(companyId); 
							computerDTO.setCompanyId(company.getId());
							computerDTO.setCompanyName(company.getName());
						}
					}catch(NumberFormatException e){}
				}

				if(computerDTO != null){
					request.setAttribute("computer",computerDTO);
				}

				List<Company> allCompany = null;
				allCompany = companyServices.getAllCompanies();
				request.setAttribute("allCompany", allCompany);
				
			}else{
				
				model.addAttribute("message","unavailable");
				model.addAttribute("computerIdMessage",idString);
				return "redirect:DashBoard";
				
			}

		}
		
		request.setAttribute("error", result.hasErrors());
		return "editComputer";
	}

	@RequestMapping(value = "EditComputer", method = RequestMethod.POST)
	public String postEditComputer(
			Model model,
			@ModelAttribute("dto") @Valid ComputerDTO computerDTO, 
			BindingResult result,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws ServletException, IOException{
		
		// Parameters searching
		String idString = request.getParameter("computerId");
		String name = request.getParameter("name");
		String introducedDateString =  request.getParameter("introduced");
		String discontinuedDateString =  request.getParameter("discontinued");
		String companyIdString =  request.getParameter("company");

		/* Company searching by ID */
		long companyId = UsefulFunctions.stringToLong(companyIdString, 0);
		Company company = companyServices.getCompany(companyId);
		
		long id = UsefulFunctions.stringToLong(idString, 0);

		String companyName = null;
		if(company != null){
			companyName = company.getName();
		}
		computerDTO.setCompanyName(companyName);
		computerDTO.setCompanyId(companyId);

		/* Validation case */
		if(!result.hasErrors()){
			Computer neoComputer = computerMapper.dtoToObject(computerDTO);
			computerServices.update(neoComputer);
			model.addAttribute("message", "update");
			model.addAttribute("computerIdMessage", id);
			return "redirect:DashBoard";
		}

		/* Error case */
		model.addAttribute("name", name);
		model.addAttribute("introduced", introducedDateString);
		model.addAttribute("discontinued", discontinuedDateString);
		model.addAttribute("company", companyIdString);

		return "redirect:AddComputer";
	}

	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String error404(HttpServletRequest request, HttpServletResponse response){
		String language = request.getParameter("language");
		if(language!=null)response.setLocale(new Locale(language));
		return "404";
	}

}
