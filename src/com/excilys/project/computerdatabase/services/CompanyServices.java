package com.excilys.project.computerdatabase.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.persistence.CompanyDAO;
import com.excilys.project.computerdatabase.persistence.ConnectionManager;

public class CompanyServices {

	public static CompanyServices instance = null;

	private CompanyDAO companyDAO = CompanyDAO.getInstance();
	private LogsServices logsServices = LogsServices.getInstance();

	public List<Company> getAllCompanies(){
		//ThreadLocal<Connection> tlc = new ThreadLocal<Connection>();
		Connection connection = null;
		List<Company> companies = null;
		try{
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);

			//traitement des différentes instructions composant la transaction
			companies = companyDAO.retrieveAll(connection);
			if(companies!=null){	  
				connection.commit(); // c'est ici que l'on valide la transaction
				connection.setAutoCommit(true);
				logsServices.insert("Companies searching completed", "Complete");
			}else{
				connection.rollback();
				logsServices.insert("Companies searching error : companies is null", "Error");
			}
			
		}catch(SQLException e){
			try{connection.rollback();}catch(Exception e2){}
			logsServices.insert("Companies searching error", "Error");
		}finally{
			try{connection.close();}catch(Exception e){}
		}

		return companies;
	}

	public Company getCompany(long idCompany){
		Connection connection = null;
		Company company = null;

		try{
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);

			company = companyDAO.retrieveByCompanyId(idCompany, connection);

			if(company!=null || idCompany == 0){	  
				connection.commit();
				connection.setAutoCommit(true);
				logsServices.insert("Company searching completed", "Complete");
			}else{
				connection.rollback();
				logsServices.insert("Company searching error : no company for id : "+idCompany, "Error");
			}
		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
			logsServices.insert("Company searching error", "Error");
		}finally{
			try{connection.close();}catch(Exception e){}
		}

		return company;
	}

	public void insert(Company company){
		Connection connection = null;

		try{
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);

			companyDAO.insert(company, connection);

			connection.commit();
			connection.setAutoCommit(true);
			logsServices.insert("Company insertion completed", "Complete");
		}catch(SQLException sqle){
			try{connection.rollback();}catch(Exception e){}
			logsServices.insert("Company insertion error", "Error");
		}finally{
			try{connection.close();}catch(Exception e){}
		}
	}

	synchronized public static CompanyServices getInstance(){
		if(instance == null){
			instance = new CompanyServices();
		}
		return instance;
	}
}
