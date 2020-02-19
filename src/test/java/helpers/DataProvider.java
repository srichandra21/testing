package helpers;

import java.io.File;
import java.util.*;
//import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import helpers.DataProvider;
import helpers.ExcelHelper;


public class DataProvider {

	// Test Data HashMap
	private HashMap<String, String> data = new HashMap<String, String>();

	// Returns Group Name Iterator
	public  Iterator groupNameIterator(){	
		Set<String> groupNames = data.keySet();
		Iterator groupNamesIterator = groupNames.iterator();
		return groupNamesIterator;
	}

	//	To merge two data HashMaps
	
	public void merge(DataProvider dp){		
		//data.putAll(dp.getDataHashMap());

		Iterator groupNameIt = dp.groupNameIterator();
		while(groupNameIt.hasNext()){
			String groupName = groupNameIt.next().toString();
				String value = dp.get(groupName);
				set(groupName,value);
			}	
		}
	

	/* To get a Value from the testData */
	public String get(String group) {
		try{
			if (data.containsKey(group) ) {
				return data.get(group).toString();
				
			} else {
				return "";
			}}catch(Exception e){
				e.printStackTrace();
				return "";			
			}
	}


	//Overides Clone method of Object
	@Override
	public DataProvider clone(){
		DataProvider tmp = new DataProvider();
		tmp.data = (HashMap<String,String>)this.data.clone();
		return  tmp;

	}

	// Returns a Data Provider with requested Group Name 
	/*
	 * @SuppressWarnings("unchecked") public DataProvider getGroup(String grpName) {
	 * DataProvider tmp = new DataProvider();
	 * 
	 * if (data.containsKey(grpName)) { tmp.setGroup(grpName, (HashMap<String,
	 * String>) data.get(grpName) .clone()); } else { tmp.setGroup(grpName, new
	 * HashMap<String, String>()); }
	 * 
	 * return tmp; }
	 */

	// Sets Data HashMap with the supplied Group Name HashMap
	/*
	 * @SuppressWarnings("unchecked") public synchronized void setGroup(String
	 * grpName, HashMap<String, String> group) {
	 * 
	 * if (data.containsKey(grpName)) { data.get(grpName).putAll(group); } else {
	 * data.put(grpName, (HashMap<String, String>) group.clone()); } }
	 */

	// Sets Data HashMap with the supplied Group Name Data Provider	
	/*
	 * public synchronized void setGroup(String grpName, DataProvider tmp) {
	 * HashMap<String,String> tmpGroup = tmp.getgroup(grpName); if(tmpGroup!=null){
	 * if (data.containsKey(grpName)) { data.get(grpName).putAll(tmpGroup); } else {
	 * data.put(grpName, (HashMap<String, String>) tmpGroup.clone()); } } }
	 */

	// Returns a Group HashMap. It is Private may change to public later
	/*
	 * private synchronized HashMap<String,String> getgroup(String groupName){
	 * HashMap<String,String> tmpGroup = null; if(data.containsKey(groupName))
	 * {tmpGroup = data.get(groupName);} return tmpGroup ; }
	 */

	/* To set testData with Group Name, Key , Value dynamically */
	public synchronized void set(String key, String value) {
		/*
		 * if (!data.containsKey(group)) { data.put(group, new HashMap<String,
		 * String>()); }
		 */
		data.put(key, value);
	}

	/* To Load Excel Data in to testData HashMap */
	public synchronized void loadFromExcel(String fileName, String sheetName, String testCase ) throws Exception {			
		ExcelHelper ex = new ExcelHelper();	
		XSSFSheet mySheet = ex.getsheet(fileName, sheetName);
		int rowNum = ex.findRow(mySheet, testCase.toLowerCase().trim());
		//int displacementrownumber = ex.findDisplaceMentrow(mySheet, rowNum, testCase.toLowerCase().trim(), iteration.toLowerCase().trim());
		if (rowNum > -1 /* && displacementrownumber > -1 */ ){
			merge(ex.load(mySheet,rowNum).clone());		
			
		}
	}

	/*	// To load Excel data in to test Data HashMap
	public synchronized void loadFromExcel(String fileName, String sheetName, String testCase,int iteration ) {		
		ExcelHelper ex = new ExcelHelper();
		XSSFSheet mySheet = ex.getsheet(fileName, sheetName);
		int rowNum = ex.findRow(mySheet, testCase);	
		if(rowNum>-1){	
		merge(ex.load(mySheet,rowNum,iteration).clone());

		}
	}*/
	// Returns Boolean of Group Name present or not
	public boolean containsGroup(String value){		
		boolean contains = false;		
		Set <String> keys = data.keySet();
		Iterator <String> keysIterator = keys.iterator();
		while (keysIterator.hasNext()){
			if(value.contains(keysIterator.next().toString())){
				contains = true;
				break;
			}
		}
		return contains;
	}

	// This is for testing purpose later we will remove 
	/*public synchronized void displayDataHashMap() {
		Set<String> groupNames = data.keySet();
		Iterator groupNamesIterator = groupNames.iterator();
		while (groupNamesIterator.hasNext()) {
			String groupName = (String) groupNamesIterator.next();
			//			System.out.println("The data for the Group Name   " + groupName+ " : ");
			Set<String> columnNames = data.get(groupName).keySet();
			Iterator columnNamesIterator = columnNames.iterator();
			while (columnNamesIterator.hasNext()) {
				String columnName = (String) columnNamesIterator.next();
				String dataValue = (String) data.get(groupName).get(columnName);
				//				System.out.println("ColumnName :  " + columnName+ " and  Data  Value :  " + dataValue);

			}
		}
	}	*/

	// To load from INI file
	/*
	 * public void loadFromIni(String fileName){ merge(IniHelper.load(fileName)); }
	 * 
	 * //Save Data Map to INI public void saveToIni(String folderName,String
	 * fileName){ if(!new File(folderName).isDirectory()) new
	 * File(folderName).mkdirs(); IniHelper.save(folderName+"\\"+fileName,clone());
	 * }
	 */

}
