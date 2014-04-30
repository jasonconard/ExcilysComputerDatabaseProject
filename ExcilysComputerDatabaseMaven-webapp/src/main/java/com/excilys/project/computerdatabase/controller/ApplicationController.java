package com.excilys.project.computerdatabase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(){
		return "redirect:DashBoard";
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
	
	@RequestMapping(value = "ErrorPage403", method = RequestMethod.GET)
	public String error403(){
		return "403";
	}
	
	@RequestMapping(value = "ErrorPage500", method = RequestMethod.GET)
	public String error500(){
		return "500";
	}
	
}
