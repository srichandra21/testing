package helpers;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
//import org.ini4j.Ini;
import org.openqa.selenium.WebDriver;

public class Util {

	static Calendar cc = null;
	

	public static String homePath = "";
	public static WebDriver driver;

	public static void setUpUtils(WebDriver uDriver){
		driver=uDriver;		
	}

	public static String getValue(String key) throws IOException{
		homePath=new File(".").getCanonicalPath();
		Properties prop=new Properties();        
		prop.load(new FileInputStream(homePath+"//configure.properties"));
		String val=prop.getProperty(key);    
		return val;
	}

	public static String getCurrentDate(String format){
		Calendar cal = Calendar.getInstance();
		cc = cal;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		return sdf.format(date);
	}


	public static String getCurrentDatenTime(String format){
		Calendar cal = Calendar.getInstance();
		cc = cal;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}

	public String generateBatchNum() {
		String code=null;
		try {
			do{
				int rnum = (int)(Math.random()*9999);
				while(String.valueOf(rnum).length()< 4||(rnum<8000&&rnum>9999)){
					rnum = (int)(Math.random()*9999);
				}
				code=String.valueOf(rnum);
			}while(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}

	public static long getLastsetTimeinmili(){
		if(cc==null){
			Calendar cal = Calendar.getInstance();
			cc = cal;
		}
		return cc.getTimeInMillis();
	}

	public static String getFormattedTime(long time){
		long timeMillis = time;
		long time1 = timeMillis / 1000;
		String seconds = Integer.toString((int)(time1 % 60));
		String minutes = Integer.toString((int)((time1 % 3600) / 60));
		String hours = Integer.toString((int)(time1 / 3600));
		for (int i = 0; i < 2; i++) {
			if (seconds.length() < 2) {
				seconds = "0" + seconds;
			}
			if (minutes.length() < 2) {
				minutes = "0" + minutes;
			}
			if (hours.length() < 2) {
				hours = "0" + hours;
			}
		}
		return hours+": "+minutes+": "+seconds;
	}

	

	public static String dateConverter(String excelDate){	
		String formatedDate = null;
		try{
			DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
			Date date = (Date)formatter.parse(excelDate);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			formatedDate = (cal.get(Calendar.MONTH) + 1) + "/"+cal.get(Calendar.DATE) + "/" +         cal.get(Calendar.YEAR);
		}catch(Exception e){
			e.printStackTrace();	
		}
		return formatedDate;
	}

	public static String dateConverter(String excelDate,String dateFormat){
		Date myDate = new Date(excelDate);
		String date ="";
		try{
			DateFormat formatter = new SimpleDateFormat(dateFormat);
			date = formatter.format(myDate);
			date = date.replace("-", "/");		
		}catch(Exception e){
			return "";
		}

		return date;
	}	

	public static void savePDFtoLocal(String hrefAttribute,String filePath) throws Exception {
		URL u = new URL(hrefAttribute);
		String filename = filePath;

		URLConnection uc = u.openConnection();
		String contentType = uc.getContentType();
		int contentLength = uc.getContentLength();
		if (contentType.startsWith("text/") || contentLength == -1) {
			throw new IOException("This is not a binary file.");
		}
		InputStream raw = uc.getInputStream();
		InputStream in = new BufferedInputStream(raw);
		byte[] data = new byte[contentLength];
		int bytesRead = 0;
		int offset = 0;
		while (offset < contentLength) {
			bytesRead = in.read(data, offset, data.length - offset);
			if (bytesRead == -1)
				break;
			offset += bytesRead;
		}
		in.close();

		if (offset != contentLength) {
			throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
		}

		FileOutputStream out = new FileOutputStream(filename);
		out.write(data);
		out.flush();
		out.close();
	}

	public static String calculateDate(String excelValue,String typeOfDate){
		try{
			String dateValue = null;
			String year = "";
			String month = "";
			String day = "";  
			String [] values = {"",""};
//			System.out.println(excelValue);
			if(excelValue.contains("+")) {
				values = excelValue.split("\\+");
			}
			if(excelValue.contains("-")){
//				System.out.println("came to -");
				values = excelValue.split("-");
			}

			if(values[1].length() == 9){
				year = values[1].substring(0,2);
				month = values[1].substring(3,5);
				day = values[1].substring(6,8);
			} else if(values[1].length() == 6){
				String first3 = values[1].substring(0,3);
//				String second3 =  values[1].substring(3,6);
				if(first3.contains("Y")){
					year = values[1].substring(0,2);
					month = values[1].substring(3,5);                      
				}else if(first3.contains("M")){
					month = values[1].substring(0,2);                     
					day = values[1].substring(3,5);                       
				}                 
			} else if(values[1].length() == 3){
				if(values[1].contains("Y")){
					year = values[1].substring(0,2);
				}else if(values[1].contains("M")){         
					month = values[1].substring(0,2);               
				}else if (values[1].contains("D")){
					day =values[1].substring(0,2);
				}           
			} else if(values[1].length() == 4){
				if(values[1].contains("Y")){
					year = values[1].substring(0,3);
				}else if(values[1].contains("M")){         
					month = values[1].substring(0,3);               
				}else if (values[1].contains("D")){
					day =values[1].substring(0,3);
				}           
			} else if(values[1].length() == 5){
				if(values[1].contains("Y")){
					year = values[1].substring(0,4);
				}else if(values[1].contains("M")){         
					month = values[1].substring(0,4);               
				}else if (values[1].contains("D")){
					day =values[1].substring(0,4);
				}           
			} else if(values[1].length() == 2){
				if(values[1].contains("Y")){
					year = values[1].substring(0,1);
				}else if(values[1].contains("M")){         
					month = values[1].substring(0,1);               
				}else if (values[1].contains("D")){
					day =values[1].substring(0,1);
				}           
			}

			if(day==""){
				day="0";
			}
			if(month==""){
				month = "0";
			}

			if(year==""){
				year="0";
			}

//			System.out.println(day+"  "+month+"  " +year);
			int finalDay = Integer.parseInt(day);
			int finalMonth = Integer.parseInt(month);
			int finalYear = Integer.parseInt(year);
			if(excelValue.contains("-")){
				finalDay = - Integer.parseInt(day);
				finalMonth = - Integer.parseInt(month);
				finalYear = - Integer.parseInt(year);
			}
//			System.out.println(finalDay+"  "+finalMonth+"  " +finalYear);
			if(typeOfDate.equals("PCDate")){                
				dateValue = addDaysToSysDate(finalDay,finalMonth,finalYear);
			}else if (typeOfDate.equals("FieldName")){
				dateValue = addDaysToFieldValue("",Integer.parseInt(day),Integer.parseInt(month),Integer.parseInt(year));
			}
			return dateValue;
		}catch(Exception e){
//			e.printStackTrace();
			return "";
		}
	}

	public  static   String addDaysToSysDate(int noOfDay, int noOfMonths, int noOfYear) {
		try{
//			System.out.println("Passed values are "+noOfDay +"  "+noOfMonths+"   "+noOfYear);
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date1=null;
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, noOfDay);		
			calendar.add(Calendar.MONTH, noOfMonths);
			calendar.add(Calendar.YEAR, noOfYear);
			return sdf.format(calendar.getTime());
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	public  static   String addDaysToFieldValue(String fieldDate,int noOfDay, int noOfMonths, int noOfYear) {
//		System.out.println("Fielddate is "+fieldDate);	
		try{			
			String [] values = fieldDate.split("/");
			String day = values[1];
			String month = values[0];
			String year = values[2];
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//			System.out.println("day, month, year "+ month +day+year);
			Calendar calendar = Calendar.getInstance(); 
			calendar.set(Calendar.DATE, Integer.parseInt(day));		
			calendar.set(Calendar.MONTH, Integer.parseInt(month)-1);		
			calendar.set(Calendar.YEAR, Integer.parseInt(year));		
			calendar.add(Calendar.DATE, noOfDay);		
			calendar.add(Calendar.MONTH, noOfMonths);
			calendar.add(Calendar.YEAR, noOfYear);
//			System.out.println("sdf.format(calendar.getTime()) "+  sdf.format(calendar.getTime()));
			return sdf.format(calendar.getTime());}catch(Exception e){
				e.printStackTrace();
				return "";
			}
	}

	public  static String calculateGroupDate(String excelValue,DataProvider data){
		try{
			String dateValue = null;
			String year = "";
			String month = "";
			String day = "";		
//			System.out.println("excelValue  "+excelValue);
//			System.out.println("excelValue length "+excelValue.length());
			String exlGroupName ="";
			String exlfieldName ="";
			try{
				String [] values  = excelValue.split("\\|");

//				System.out.println("Value of the values[0]" + values[0]);
//				System.out.println("Value of the values[1]" + values[1]);

				exlGroupName = values[0];

				String []values1 = 	values[1].split("\\+");		 
				exlfieldName = values1[0];


				if(values1[1].length() == 9){
					year = values1[1].substring(0,2);
					month = values1[1].substring(3,5);
					day = values1[1].substring(6,8);
				}
				else if(values1[1].length() == 6){
					String first3 = values1[1].substring(0,3);
					String second3 =  values1[1].substring(3,6);
					if(first3.contains("Y")){
						year = values1[1].substring(0,2);
						month = values1[1].substring(3,5);             		 
					}else if(first3.contains("M")){
						month = values1[1].substring(0,2);            		
						day = values1[1].substring(3,5);   
//						System.out.println("Month and day are "+ month  + day);
					}            	
				}

				else if(values1[1].length() == 3){

					if(values1[1].contains("Y")){
						year = values1[1].substring(0,2);

					}else if(values1[1].contains("M")){    	 

						month = values1[1].substring(0,2);   		

					}else if (values1[1].contains("D")){

						day =values1[1].substring(0,2);
					}		
				}
			}catch(Exception e){
				e.printStackTrace();
				dateValue = "";
			}

			if(day==""){
				day="0";
			}
			if(month==""){
				month = "0";
			}
			if(year==""){
				year="0";
			}
			String fieldDate = null;

			fieldDate = (String) data.get(exlfieldName); 

			dateValue =	addDaysToFieldValue( fieldDate, Integer.parseInt(day),Integer.parseInt(month),Integer.parseInt(year));
			return dateValue;
		}catch (Exception e){
			e.printStackTrace();
			return "";
		}
	}
}
