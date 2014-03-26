package com.excilys.project.computerdatabase.mapper;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.excilys.project.computerdatabase.dto.ComputerDTO;

import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.domain.Company.CompanyBuilder;
import com.excilys.project.computerdatabase.domain.Computer.ComputerBuilder;


public class ComputerMapper {
	
	public static Computer dtoToObject(ComputerDTO cdto){

		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		LocalDate introduced = null;
		LocalDate discontinued = null;
		
		if(cdto.getIntroduced().length()>0){
			introduced = dtf.parseLocalDate(cdto.getIntroduced());
		}
		
		if(cdto.getDiscontinued().length()>0){
			discontinued = dtf.parseLocalDate(cdto.getDiscontinued());
		}
		
		Computer c = new ComputerBuilder(cdto.getId(), cdto.getName())
						.introduced(introduced)
						.discontinued(discontinued)
						.company( 
								new CompanyBuilder(cdto.getCompanyId())
								.name(cdto.getCompanyName())
								.build()
						)
						.build();
		
		return c;
	}
	
	public static ComputerDTO objectToDto(Computer c){
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		String introduced = "";
		if(c.getIntroduced()!=null){
			introduced = formatter.print(c.getIntroduced());
		}

		String discontinued = "";
		if(c.getDiscontinued()!=null){
			discontinued = formatter.print(c.getDiscontinued());
		}
		
		ComputerDTO cdto = new ComputerDTO.ComputerDTOBuilder(c.getId(),c.getName())
		.introduced(introduced)
		.discontinued(discontinued)
		.companyId(c.getCompany().getId())
		.companyName(c.getCompany().getName())
		.build();
		
		return cdto;
	}
}
