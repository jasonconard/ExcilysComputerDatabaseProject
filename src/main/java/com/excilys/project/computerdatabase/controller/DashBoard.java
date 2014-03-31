package com.excilys.project.computerdatabase.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.services.ComputerServices;

/**
 * Servlet implementation class DashBoard
 */
public class DashBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ComputerServices computerServices;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
	}
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashBoard() {
		super();
	}
	
	
	
	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		

		/* From ADD and EDIT Computer Servlet */
		String idAttString = request.getParameter("computerIdMessage");
		Computer lastEdit = null;
		if(idAttString!=null){
			try{
				lastEdit = computerServices.getComputer(Long.parseLong(idAttString));
			}catch(NumberFormatException e){
			}
		}
		
		String message = request.getParameter("message");
		String computerIdMessage = request.getParameter("computerIdMessage");
		if(message!=null){
			if(message.equals("update")){
				request.setAttribute("message", "Computer updated !");
			}else if(message.equals("add")){
				request.setAttribute("message", "Computer added !");
			}else if(message.equals("unavailable")){
				request.setAttribute("error", "Computer ("+computerIdMessage+") unavailable !");
			}
		}
			

		/* Deleting managment */
		String idString = request.getParameter("computerId");		
		String delete 	= request.getParameter("delete");

		if(idString != null && delete!=null && delete.equals("delete")){
			try{
				long id = Long.parseLong(idString);
				computerServices.delete(id);
			}catch(NumberFormatException e){}
			
			
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

		Page<Computer> page = new Page<Computer>();
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
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
	}
}
