package com.excilys.project.computerdatabase.common;

public class UsefulFunctions {

	public static long stringToLong(String myString, long myLong){
		if(myString != null && myString.matches("\\d*")){
			myLong = Integer.parseInt(myString);
		}
		return myLong;
	}
	
	public static boolean isANumber(String myString){
		return myString != null && myString.matches("\\d*");
	}
}
