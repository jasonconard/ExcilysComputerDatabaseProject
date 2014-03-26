package com.excilys.project.computerdatabase.validator;

import com.excilys.project.computerdatabase.dto.ComputerDTO;

public class ComputerValidator {

	public static String validate(ComputerDTO neoComputer) {
		StringBuilder error = new StringBuilder("");
		
		if(neoComputer.getName().trim().length() == 0){
			error.append("A computer must have a name.<br/>");
		}
		
		Boolean datesHaveNoError = true;
		if(neoComputer.getIntroduced().length() > 0 && !testDate(neoComputer.getIntroduced())){
			error.append("Introduced date format not correct.<br/>");
			datesHaveNoError = false;
		}
		
		if(neoComputer.getDiscontinued().length() > 0 && !testDate(neoComputer.getDiscontinued())){
			error.append("Discontinued date format not correct.<br/>");
			datesHaveNoError = false;
		}
		
		if(datesHaveNoError && neoComputer.getIntroduced().length() == 10 && neoComputer.getDiscontinued().length() == 10){
			if(!dateLaterThan(neoComputer.getDiscontinued(), neoComputer.getIntroduced())){
				error.append("Discontinued have to be later than introduced date");
			}
		}
				
		return error.toString();
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
