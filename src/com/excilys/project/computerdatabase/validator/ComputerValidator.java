package com.excilys.project.computerdatabase.validator;

import com.excilys.project.computerdatabase.dto.ComputerDTO;

public class ComputerValidator {

	public static String validate(ComputerDTO neoComputer) {
		StringBuilder error = new StringBuilder("");
		
		if(neoComputer.getName().trim().length() == 0){
			error.append("A computer must have a name.\n");
		}
		
		if(neoComputer.getIntroduced().length() > 0 && !testDate(neoComputer.getIntroduced())){
			error.append("Discontinued date format not correct.\n");
		}
		
		if(neoComputer.getDiscontinued().length() > 0 && !testDate(neoComputer.getDiscontinued())){
			error.append("Discontinued date format not correct.\n");
		}
				
		return error.toString();
	}
	
	public static boolean testDate(String date){
		boolean testDate = false;
		if(date.length()==10){
			String yearString = date.substring(0,4);
			String monthString = date.substring(5,7);
			String dayString = date.substring(8,10);
			
			int year = Integer.parseInt(yearString);
			int month = Integer.parseInt(monthString);
			int day = Integer.parseInt(dayString);
			
			if(year>=1000 && year<=9999){
				if(month>0 && month<=12){
					if(day>0){
						if(month==1 && day<= 31){
							testDate = true;
						}else if(month==2 && ((year%4) == 0 && day <= 29) || ((year%4) != 0 && day <= 28)){
							testDate = true;
						}else if(month==3 && day<= 31){
							testDate = true;
						}else if(month==4 && day<= 30){
							testDate = true;
						}else if(month==5 && day<= 31){
							testDate = true;
						}else if(month==6 && day<= 30){
							testDate = true;
						}else if(month==7 && day<= 31){
							testDate = true;
						}else if(month==8 && day<= 31){
							testDate = true;
						}else if(month==9 && day<= 30){
							testDate = true;
						}else if(month==10 && day<= 31){
							testDate = true;
						}else if(month==11 && day<= 30){
							testDate = true;
						}else if(month==12 && day<= 31){
							testDate = true;
						}
					}
				}
			}
		}
		return testDate;
	}

	
}
