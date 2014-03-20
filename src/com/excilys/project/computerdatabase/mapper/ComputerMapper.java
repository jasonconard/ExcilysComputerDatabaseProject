package com.excilys.project.computerdatabase.mapper;

import com.excilys.project.computerdatabase.common.UsefulFunctions;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.domain.Computer.ComputerBuilder;
import com.excilys.project.computerdatabase.dto.ComputerDTO;

public class ComputerMapper {
	public static Computer dtoToObject(ComputerDTO cdto){

		java.util.Date introduced = null;
		if(cdto.getIntroduced().length()>0){
			UsefulFunctions.stringToDate(cdto.getIntroduced());;
		}
		java.util.Date discontinued = null;
		if(cdto.getDiscontinued().length()>0){
			UsefulFunctions.stringToDate(cdto.getDiscontinued());
		}
		
		Computer c = new ComputerBuilder(cdto.getId(), cdto.getName())
						.introduced(introduced)
						.discontinued(discontinued)
						.company(cdto.getCompany())
						.build();
		
		return c;
	}
}
