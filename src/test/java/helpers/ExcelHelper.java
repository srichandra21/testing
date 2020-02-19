package helpers;

import java.io.*;
import java.sql.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell.*;
import org.apache.poi.ss.usermodel.*;
import helpers.*;

public class ExcelHelper {	

	public final int testDataNoOfRows = 60000;
	public final int testDataColumncount = 999;
	public final int testCaseColumn = 1;
	public final int testDataColumn = 0;
	public final int executeTestCasecolumn = 2;
	public final int iterationColumn = 1;
	FormulaEvaluator evaluator; 

	public  int findDisplaceMentrow(XSSFSheet sheet, int testcaserow, String testCase, String primaryKey){	
		for (int i = testcaserow; i < testDataNoOfRows; i++) {	
			String tstCase = getCellData(sheet, i,testCaseColumn).toLowerCase().trim();
			if(!tstCase.equals(testCase))
				return -1;
			String iteration = getCellData(sheet, i,iterationColumn).toLowerCase().trim();
			if (!iteration.equals(primaryKey))	continue;
			return i-testcaserow;
		}
		return -1;
	}

	public synchronized String getCellData(XSSFSheet mySheet, int row, int col){
		String returnVal="";
		try{
			XSSFCell cell= mySheet.getRow(row).getCell(col);
			switch(cell.getCellType()){			
			case STRING:			
				returnVal = cell.getStringCellValue();			
				break;
			case NUMERIC:				
				returnVal = ""+cell.getNumericCellValue();
				break;
			case BOOLEAN:				
				returnVal = ""+cell.getBooleanCellValue();
				break;
			default:
				returnVal = "";
			break;
			}
		} catch(Exception e){
			returnVal = "";
		}

		return returnVal;
	}

	public  synchronized DataProvider load(XSSFSheet mySheet, int i ) {
		DataProvider dp = new DataProvider();		
		for (int k = testDataColumn; k < testDataColumncount; k++) {				

			//String groupName = getCellData(mySheet, i,k);
			String columnName = getCellData(mySheet, 0,k);
			String data = "";
			// To get Date related data
			if( (!columnName.isEmpty()) && columnName.substring(0,3).equals("dt_")){
				data = getDateCellData(mySheet, i,k,dp);
			}else if(!columnName.isEmpty() && columnName.equalsIgnoreCase("Recommendations")){
				
				data = getFormulaCellData(mySheet, i,k);
			}
			else{
				data = getCellData(mySheet, i,k);
				}

			if( !columnName.isEmpty()){
				dp.set(columnName, data);
			}
		}
		return dp;
	}
	
	private String getFormulaCellData(XSSFSheet mySheet,int row , int col) {
		String returnVal="";
		try{
			XSSFCell cell1= mySheet.getRow(row).getCell(col);
			XSSFCell cell = (XSSFCell) evaluator.evaluateInCell(cell1);
			switch(cell.getCellType()){			
			case STRING:			
				returnVal = cell.getStringCellValue();			
				break;
			case NUMERIC:				
				returnVal = ""+cell.getNumericCellValue();
				break;
			case BOOLEAN:				
				returnVal = ""+cell.getBooleanCellValue();
				break;
			default:
				returnVal = "";
			break;
			}
		} catch(Exception e){
			returnVal = "";
		}

		return returnVal;
	}
	

	private  String getDateCellData(XSSFSheet mySheet,int row , int col,DataProvider dp){		
		
		String data = "";		
		try{	
			if(mySheet.getRow(row).getCell(col)!=null){
				if(mySheet.getRow(row).getCell(col).getCellType()==CellType.STRING){
					if(mySheet.getRow(row).getCell(col).getStringCellValue().contains("PCDate")){
						data =  Util.calculateDate(mySheet.getRow(row).getCell(col).getStringCellValue(),"PCDate"); 
					}else if(mySheet.getRow(row).getCell(col).getStringCellValue().contains("APPDate")) {
						data = Util.calculateDate(mySheet.getRow(row).getCell(col).getStringCellValue(),"APPDate");
					}else if (dp.containsGroup(mySheet.getRow(row).getCell(col).getStringCellValue())){
						data = Util.calculateGroupDate(mySheet.getRow(row).getCell(col).getStringCellValue(),dp.clone());
					}else{			    	
						data = Util.dateConverter(getCellData(mySheet, row,col),Util.getValue("dateFormat"));
					}		
				}
				else if((HSSFDateUtil.isCellDateFormatted(mySheet.getRow(row).getCell(col)))){			
					data = Util.dateConverter(mySheet.getRow(row).getCell(col).getDateCellValue().toString(),Util.getValue("dateFormat"));				
				}else{
					data = "";
				}
			}
		}catch(Exception e){
			data = "";
		}
		return data;
	}

	public int findRow(XSSFSheet sheet, String primaryKey){		
		for (int i = 0; i < testDataNoOfRows; i++) {
			String xlTCName = getCellData(sheet, i,testCaseColumn);
			if (!xlTCName.toLowerCase().trim().equals(primaryKey))	continue;
			System.out.println("row in excel:"+i);
			return i;
			
		}
		return -1;
	}

	public synchronized XSSFSheet getsheet(String fileName,String sheetName) throws Exception{
		FileInputStream myInput;
		XSSFWorkbook workbook;
		XSSFSheet mySheet = null;
		myInput = new FileInputStream(fileName);
		workbook = new XSSFWorkbook(myInput);
		mySheet = workbook.getSheet(sheetName); 
		evaluator= workbook.getCreationHelper().createFormulaEvaluator();
		//evaluator.evaluateAll();
		return mySheet;
	}

	public synchronized Connection getSheetConnection(String filePath) throws SQLException, ClassNotFoundException{
		Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con= java.sql.DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+filePath+";READONLY=FALSE");
		return con;
	}

	public synchronized String getSheetData(Connection con, String query,String columnName) throws SQLException{
		DataProvider dp= new DataProvider();
		ResultSet rs =null;
		Statement stmnt = null;
		String data = null;

		stmnt = con.createStatement();
		rs= stmnt.executeQuery(query);
		if(rs!=null){
			while(rs.next()){
				dp.set(columnName, rs.getString(columnName));
			}
		}else{
			dp.set(columnName, "");
		}		
		return data;
	}

	public static void closeConnection(Connection con) throws SQLException {
		con.close();
	}

}
