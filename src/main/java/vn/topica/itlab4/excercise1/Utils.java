package vn.topica.itlab4.excercise1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static Date stringToDate(String date1) {
		
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}
	
	public static String dateToString(Date date) {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
		String stringDate = dateFormat.format(date);
		
		return stringDate;
	}
	
	
	public static String standardizedString(String givenString) {
	    String[] arr = givenString.toLowerCase().split("\\s+");
	    StringBuffer sb = new StringBuffer();

	    for (int i = 0; i < arr.length; i++) {
	        sb.append(Character.toUpperCase(arr[i].charAt(0)))
	            .append(arr[i].substring(1)).append(" ");
	    }          
	    return sb.toString().trim();
	}
}
