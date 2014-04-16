package com.excilys.project.computerdatabase.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.domain.Computer.ComputerBuilder;

@SuppressWarnings("rawtypes")
public class ComputerRowMapper implements RowMapper{
	
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

			Computer computer = new Computer(new ComputerBuilder(rs.getLong("id"),rs.getString("name")));
			
			Date introduced = rs.getDate("introduced");
			Date discontinued = rs.getDate("discontinued");
			LocalDate introducedLD = null;
			if(introduced != null){
				introducedLD = LocalDate.fromDateFields(introduced);
			}
			LocalDate discontinuedLD = null;
			if(discontinued != null){
				discontinuedLD = LocalDate.fromDateFields(discontinued);
			}
			
			computer.setIntroduced(introducedLD);
			computer.setDiscontinued(discontinuedLD);
			
			Company company = new Company();
			
			company.setId(rs.getLong("company_id"));
			company.setName(rs.getString("company_name"));
			
			computer.setCompany(company);
			
			return computer;
		}
}