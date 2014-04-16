package com.excilys.project.computerdatabase.mapper;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.excilys.project.computerdatabase.dto.ComputerDTO;
import com.excilys.project.computerdatabase.domain.Computer;
import com.excilys.project.computerdatabase.domain.Company.CompanyBuilder;
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
	
	public Computer dtoToObject(ComputerDTO cdto){
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
	
	public ComputerDTO objectToDto(Computer c){
		String pattern = messageSource.getMessage("view.addComputer.dateFormat", null, LocaleContextHolder.getLocale());
		//DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		
		String introduced = "";
		if(c.getIntroduced()!=null){
			introduced = c.getIntroduced().toString(pattern);
			//introduced = formatter.print(c.getIntroduced());
		}

		String discontinued = "";
		if(c.getDiscontinued()!=null){
			discontinued = c.getDiscontinued().toString(pattern);
			//discontinued = formatter.print(c.getDiscontinued());
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
