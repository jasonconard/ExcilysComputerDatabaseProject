package com.excilys.project.computerdatabase.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.project.computerdatabase.common.Page;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.services.CompanyServices;
import com.excilys.project.computerdatabase.services.ComputerServices;

/**
 * Servlet implementation class DashBoard
 */
public class DashBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ComputerServices computerServices = ComputerServices.getInstance();
	CompanyServices companyServices = CompanyServices.getInstance();

	private static final String ATTR_COMPA            = "company";
	private static final String ATTR_COMPU_ID         = "computerId";
	private static final String ATTR_COMPU_ID_MESSAGE = "computerIdMessage";
	private static final String ATTR_DELETE           = "delete";
	private static final String ATTR_ORDER            = "order";
	private static final String ATTR_ORDER_CU_NAME    = "cu.name";
	private static final String ATTR_ORDER_CA_NAME    = "ca.name";
	private static final String ATTR_DIR              = "dir";
	private static final String ATTR_ASC              = "ASC";
	private static final String ATTR_SEARCH           = "search";
	private static final String ATTR_PAGE             = "page";
	private static final String ATTR_NB_PAGE          = "nbPage";
	private static final String ATTR_NEXT_PAGE        = "nextPage";
	private static final String ATTR_LAST_PAGE        = "lastPage";
	private static final String ATTR_NUM              = "numero";
	private static final String ATTR_UPDATE_MESSAGE   = "Computer updated !";
	private static final String ATTR_ADD_MESSAGE      = "Computer added !";
	private static final String ATTR_ADD              = "add";
	private static final String ATTR_UPDATE           = "update";
	private static final String ATTR_MESSAGE          = "message";
	
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
		String idAttString = request.getParameter(ATTR_COMPU_ID_MESSAGE);
		Computer lastEdit = null;
		if(idAttString!=null){
			lastEdit = computerServices.getComputer(Long.parseLong(idAttString));
		}
		
		String message = request.getParameter(ATTR_MESSAGE);
		if(message!=null){
			if(message.equals(ATTR_UPDATE)){
				request.setAttribute(ATTR_MESSAGE, ATTR_UPDATE_MESSAGE);
			}else if(message.equals(ATTR_ADD)){
				request.setAttribute(ATTR_MESSAGE, ATTR_ADD_MESSAGE);
			}
		}
			

		/* Deleting managment */
		String idString = request.getParameter(ATTR_COMPU_ID);		
		String delete 	= request.getParameter(ATTR_DELETE);

		if(idString != null && delete!=null && delete.equals(ATTR_DELETE)){
			long id = Long.parseLong(idString);
			computerServices.delete(id);
		}


		/* Order By managment */
		String order = request.getParameter(ATTR_ORDER);

		if(order == null || order.length()==0){
			order = ATTR_ORDER_CU_NAME;
		}else if(order.equals(ATTR_COMPA)){
			order = ATTR_ORDER_CA_NAME;
		}

		String dir = request.getParameter(ATTR_DIR);
		if(dir == null || dir.length()==0){
			dir = ATTR_ASC;
		}

		/* Searching managment */
		String search = null;
		if(lastEdit == null){
			search = request.getParameter(ATTR_SEARCH);
		}else{
			search = lastEdit.getName();
		}

		if(search==null){
			search = "";
		}

		/* Pagination managment */
		String idPageString = request.getParameter(ATTR_PAGE);

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

		page = computerServices.getAllComputers(page);

		int nbPage = page.getNumber()/Page.NBLINEPERPAGES+1;

		request.setAttribute(ATTR_NB_PAGE, nbPage);

		if(idPage<nbPage){
			request.setAttribute(ATTR_NEXT_PAGE, idPage+1);
		}else{
			request.setAttribute(ATTR_NEXT_PAGE, -1);
		}
		if(idPage>1){
			request.setAttribute(ATTR_LAST_PAGE, idPage-1);
		}else{
			request.setAttribute(ATTR_LAST_PAGE, -1);
		}

		request.setAttribute(ATTR_PAGE, page);
		request.setAttribute(ATTR_NUM, page.getNumero()+1);

		/* Redirection */
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
	}
}
