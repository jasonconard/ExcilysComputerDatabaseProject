package com.excilys.project.computerdatabase.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;

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
public class AddComputerController{

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
	
	@InitBinder
	public void initBinderAll(WebDataBinder binder) {
		binder.addValidators(new ComputerValidator());
	}

	private static List<Company> allCompany = null;
	
	@RequestMapping(value = "AddComputer", method = RequestMethod.GET)
	public String showAddComputer(
			ModelMap model,
			@ModelAttribute("dto") @Valid ComputerDTO computerDTO, 
			BindingResult result
		){
		
		allCompany = companyServices.getAllCompanies();
		model.addAttribute("allCompany", allCompany);	
		return "addComputer";
	}

	@RequestMapping(value = "AddComputer", method = RequestMethod.POST)
	public ModelAndView postAddComputer(	
			@ModelAttribute("dto") @Valid ComputerDTO computerDTO, 
			BindingResult result,
			@RequestParam("company") String companyIdString
		) throws ServletException, IOException{
		
		setCompanyToDto(companyIdString, computerDTO);
		
		ModelAndView mav = new ModelAndView();
		
		/* Validation case */
		if(!result.hasErrors()){
			Computer neoComputer = computerMapper.dtoToObject(computerDTO);
			long id = computerServices.insert(neoComputer);
			if(id >= 0){
				mav.getModelMap().addAttribute("message", "add");
				mav.getModelMap().addAttribute("computerIdMessage",id);
				mav.setViewName("redirect:DashBoard");
			}
		/* Error case */
		}else{		
			allCompany = companyServices.getAllCompanies();
			mav.getModelMap().addAttribute("allCompany", allCompany);
			mav.getModelMap().addAttribute("error", result.hasErrors());
			mav.setViewName("addComputer");
		}
		return mav;
	}
	
	public void setCompanyToDto(String companyIdString, ComputerDTO computerDTO) {
		long companyId = UsefulFunctions.stringToLong(companyIdString,0);
		Company company = companyServices.getCompany(companyId);
		String companyName = null;
		if(company != null){
			companyName = company.getName();
		}

		computerDTO.setCompanyId(companyId);
		computerDTO.setCompanyName(companyName);
	}
}
