package com.excilys.project.computerdatabase.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.project.computerdatabase.dto.ComputerDTO;

public class ComputerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors e) {

		//ValidationUtils.rejectIfEmpty(e, "name", "name.empty");

		ComputerDTO computerDTO = (ComputerDTO) target;

		if(computerDTO != null){
			if(computerDTO.getName()!=null){
				if(computerDTO.getName().trim().length() == 0){
					e.rejectValue("name","invalid","A computer must have a name");
				}
			}

			Boolean datesHaveNoError = true;
			if(computerDTO.getIntroduced() != null && computerDTO.getIntroduced().length() > 0 && !testDate(computerDTO.getIntroduced())){
				e.rejectValue("introduced","invalid","Introduced date format is not correct.");
				datesHaveNoError = false;
			}

			if(computerDTO.getDiscontinued() != null && computerDTO.getDiscontinued().length() > 0 && !testDate(computerDTO.getDiscontinued())){
				e.rejectValue("discontinued","invalid","Discontinued date format is not correct.");
				datesHaveNoError = false;
			}

			if(computerDTO.getIntroduced() != null            	&&
					computerDTO.getDiscontinued() != null    	&&
					datesHaveNoError                           	&& 
					computerDTO.getIntroduced().length() == 10 	&& 
					computerDTO.getDiscontinued().length() == 10){
				if(!dateLaterThan(computerDTO.getDiscontinued(), computerDTO.getIntroduced())){
					e.rejectValue("discontinued", "invalid", "Discontinued date has to be later than introduced date.");
				}
			}
		}
	}
	
	private static boolean dateLaterThan(String discontinued, String introduced) {
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

	public static boolean testDate(String date){

		StringBuilder regexp = new StringBuilder();
		regexp.append("^");
		regexp.append("(19|20)\\d\\d[- /.]"); //Years
		regexp.append("(");
		regexp.append("(0[13578]|1[02])[- /.](0[1-9]|[12][0-9]|3[01])"); //Month with 31 days
		regexp.append("|");
		regexp.append("(0[469]|11])[- /.](0[1-9]|[12][0-9]|30)"); //Month with 30 days
		regexp.append("|");
		regexp.append("(02)[- /.](0[1-9]|[12][0-9])"); //February case (no leap year management)
		regexp.append(")");
		regexp.append("$");

		return date.matches(regexp.toString());
	}
}
