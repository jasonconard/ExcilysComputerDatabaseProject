package com.excilys.project.computerdatabase.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.services.ComputerServices;

/**
 * Servlet implementation class DashBoard
 */
public class DashBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
			lastEdit = ComputerServices.INSTANCE.getComputer(Long.parseLong(idAttString));
		}
		
		String message = request.getParameter("message");
		if(message!=null){
			if(message.equals("update")){
				request.setAttribute("message", "Computer updated !");
			}else if(message.equals("add")){
				request.setAttribute("message", "Computer added !");
			}
		}
			

		/* Deleting managment */
		String idString = request.getParameter("computerId");		
		String delete 	= request.getParameter("delete");

		if(idString != null && delete!=null && delete.equals("delete")){
			long id = Long.parseLong(idString);
			ComputerServices.INSTANCE.delete(id);
		}


		/* Order By managment */
		String order = request.getParameter("order");

		if(order == null || order.length()==0){
			order = "cu.name";
		}else if(order.equals("company")){
			order = "ca.name";
		}

		String dir = request.getParameter("dir");
		if(dir == null || dir.length()==0){
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
			idPage = Integer.parseInt(idPageString);
			if(idPage<1){
				idPage = 1;
			}
		}		

		Page<Computer> page = new Page<Computer>();
		page.setNumero(idPage-1);
		page.setColumn(order);
		page.setDirection(dir);
		page.setFilter(search);

		page = ComputerServices.INSTANCE.getAllComputers(page);

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
