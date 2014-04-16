package com.excilys.project.computerdatabase.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.project.computerdatabase.dto.ComputerDTO;

@Component
public class ComputerValidator implements Validator {
	
	@Bean(name = "messageSource")
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
	    bean.setBasename("computerDatabase");
	    return bean;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors e) {
		
		ComputerDTO computerDTO = (ComputerDTO) target;

		if(computerDTO != null){
			
			if(computerDTO.getName()!=null){
				if(computerDTO.getName().trim().length() == 0){
					e.rejectValue("name","validator.computer.name");
				}
			}
			
			Boolean datesHaveNoError = true;
			if(computerDTO.getIntroduced() != null && computerDTO.getIntroduced().length() > 0 && !testDate(computerDTO.getIntroduced())){
				e.rejectValue("introduced","validator.computer.introduced");
				datesHaveNoError = false;
			}

			if(computerDTO.getDiscontinued() != null && computerDTO.getDiscontinued().length() > 0 && !testDate(computerDTO.getDiscontinued())){
				e.rejectValue("discontinued","validator.computer.discontinued");
				datesHaveNoError = false;
			}

			if(computerDTO.getIntroduced() != null            	&&
					computerDTO.getDiscontinued() != null    	&&
					datesHaveNoError                           	&& 
					computerDTO.getIntroduced().length() == 10 	&& 
					computerDTO.getDiscontinued().length() == 10){
				if(!dateLaterThan(computerDTO.getDiscontinued(), computerDTO.getIntroduced())){
					e.rejectValue("discontinued","validator.computer.discoLTintro");
				}
			}
		}
	}
	
	private boolean dateLaterThan(String discontinued, String introduced) {
		boolean dateLater = false;

		String yearDisString  = discontinued.substring(0,4);
		String monthDisString = discontinued.substring(5,7);
		String dayDisString   = discontinued.substring(8,10);
		String yearIntString  = introduced.substring(0,4);
		String monthIntString = introduced.substring(5,7);
		String dayIntString   = introduced.substring(8,10);

		int yearDis  = Integer.parseInt(yearDisString);
		int monthDis = Integer.parseInt(monthDisString);
		int dayDis   = Integer.parseInt(dayDisString);
		int yearInt  = Integer.parseInt(yearIntString);
		int monthInt = Integer.parseInt(monthIntString);
		int dayInt   = Integer.parseInt(dayIntString);

		if(yearDis > yearInt){
			dateLater = true;
		}else if(yearDis == yearInt){
			if(monthDis > monthInt){
				dateLater = true;
			}else if(monthDis == monthInt){
				if(dayDis > dayInt){
					dateLater = true;
				}
			}
		}

		return dateLater;
	}

	public boolean testDate(String date){
		return date.matches(messageSource().getMessage("regexp.date", null, LocaleContextHolder.getLocale()));
	}
}
