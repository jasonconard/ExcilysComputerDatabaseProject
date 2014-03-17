package com.excilys.project.computerdatabase.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.persistence.WrapperComputer;
import com.excilys.project.computerdatabase.services.ComputerServices;

/**
 * Servlet implementation class DashBoard
 */
public class DashBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ComputerServices computerServices = ComputerServices.getInstance();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashBoard() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		/* Deleting managment */
		String idString = request.getParameter("computerId");
		String delete = request.getParameter("delete");
		if(idString != null && delete!=null && delete.equals("delete")){
			long id = Long.parseLong(idString);
			computerServices.delete(id);
		}
		
		
		/* Order By managment */
		String order = request.getParameter("order");
		String visibleOrder = order; // The order parameter visible by client
		
		if(order == null || order.length()==0){
			order = "cu.name";
			visibleOrder = "name";
		}else if(order.equals("company")){
			order = "ca.name";
		}else{
			order = "cu."+order;
		}
		
		String dir = request.getParameter("dir");
		if(dir == null || dir.length()==0){
			dir = "ASC";
		}
		
		request.setAttribute("order", visibleOrder);
		request.setAttribute("dir", dir);
		
		/* Searching managment */
		String search = request.getParameter("search");
		if(search==null){
			search = "";
		}
		int nbComputer = computerServices.getComputerNumber(search);
		
		/* Pagination managment */
		String idPageString = request.getParameter("page");
		
		int idPage = 1;
		
		if(idPageString != null && idPageString.length() != 0){
			idPage = Integer.parseInt(idPageString);
			if(idPage<1){
				idPage = 1;
			}
		}
		
		int nbPage = nbComputer/WrapperComputer.NBLINEPERPAGES+1;
		//int indLineMin = (idPage-1)*NBLINEPERPAGES;
		//int indLineMax = indLineMin+NBLINEPERPAGES-1;
		
		request.setAttribute("idPage", idPage);
		request.setAttribute("nbPage", nbPage);
		//request.setAttribute("indLineMin", indLineMin);
		//request.setAttribute("indLineMax", indLineMax);
		
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
		
		
		List<Computer> allComputer = null;
		WrapperComputer params = new WrapperComputer(idPage-1, order, dir, search);
		
		allComputer = computerServices.getAllComputers(params);
		
		
		request.setAttribute("search", search);
		request.setAttribute("allComputer", allComputer);
		request.setAttribute("nbComputer", nbComputer);
		
		/* Redirection */
		request.getRequestDispatcher("dashboard.jsp").forward(request, response);
	}
}
