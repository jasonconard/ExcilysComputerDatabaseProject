package com.excilys.project.computerdatabase.mapper;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.excilys.project.computerdatabase.dto.ComputerDTO;
import com.excilys.project.computerdatabase.domain.Company;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.domain.Computer.ComputerBuilder;

@Component
public class ComputerMapper {
	
	@Autowired
	private ResourceBundleMessageSource messageSource;	
	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public Computer dtoToObject(ComputerDTO cdto, Company company){
		String pattern = messageSource.getMessage("view.addComputer.dateFormat", null, LocaleContextHolder.getLocale());
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		
		LocalDate introduced = null;
		LocalDate discontinued = null;
		
		if(cdto.getIntroduced().length()>0){
			introduced = formatter.parseLocalDate(cdto.getIntroduced());
		}
		
		if(cdto.getDiscontinued().length()>0){
			discontinued = formatter.parseLocalDate(cdto.getDiscontinued());
		}
		
		Computer computer = new ComputerBuilder(cdto.getId(), cdto.getName())
						.introduced(introduced)
						.discontinued(discontinued)
						.company(company)
						.build();
		
		return computer;
	}
	
	public ComputerDTO objectToDto(Computer computer){
		String pattern = messageSource.getMessage("view.addComputer.dateFormat", null, LocaleContextHolder.getLocale());
		
		String introduced = "";
		if(computer.getIntroduced()!=null){
			introduced = computer.getIntroduced().toString(pattern);
		}

		String discontinued = "";
		if(computer.getDiscontinued()!=null){
			discontinued = computer.getDiscontinued().toString(pattern);
		}
		
		long companyId = 0;
		String companyName = null;
		if(computer.getCompany()!=null){
			companyId = computer.getCompany().getId();
			companyName = computer.getCompany().getName();
		}
		
		ComputerDTO cdto = new ComputerDTO.ComputerDTOBuilder(computer.getId(),computer.getName())
		.introduced(introduced)
		.discontinued(discontinued)
		.companyId(companyId)
		.companyName(companyName)
		.build();
		
		return cdto;
	}
}
