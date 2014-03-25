package com.excilys.project.computerdatabase.mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.excilys.project.computerdatabase.common.UsefulFunctions;
import com.excilys.project.computerdatabase.domain.Company.CompanyBuilder;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.domain.Computer.ComputerBuilder;
import com.excilys.project.computerdatabase.dto.ComputerDTO;

public class ComputerMapper {
	
	public static Computer dtoToObject(ComputerDTO cdto){

		java.util.Date introduced = null;
		if(cdto.getIntroduced().length()>0){
			introduced = UsefulFunctions.stringToDate(cdto.getIntroduced());
		}
		java.util.Date discontinued = null;
		if(cdto.getDiscontinued().length()>0){
			discontinued = UsefulFunctions.stringToDate(cdto.getDiscontinued());
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
		String introduced = "";
		if(c.getIntroduced()!=null){
			DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
			introduced = df.format(c.getIntroduced());
		}

		String discontinued = "";
		if(c.getDiscontinued()!=null){
			DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
			discontinued = df.format(c.getDiscontinued());
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
