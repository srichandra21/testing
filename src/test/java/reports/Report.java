package reports;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;


import helpers.TestCase;
import helpers.Util;
import reports.ReportTemplate.Caller;

public class Report {

	static volatile String rootFolder=null;
	static volatile HashMap<String, Report> reports = null;
	private ArrayList<ReportTemplate> reportList =new ArrayList<ReportTemplate>();
	volatile boolean initStatus = false;
	Caller caller = Caller.SUITE;
	private int testCasePassCntr,testCaseFailCntr,testCaseWarningsCounter;
	private TestCase tc;
	protected static volatile WebDriver driver;

	public static synchronized Report getReportForThread() {
		if(reports == null){
			reports = new HashMap<String, Report>();
		}

		String threadName = Thread.currentThread().getName();
		if (!reports.containsKey(threadName)) {
			reports.put(threadName, new Report());
		}

		if(rootFolder==null){
			rootFolder = "report/" + "Run_"
			+ Util.getCurrentDatenTime("yyyyMMdd") + "_"
			+ Util.getCurrentDatenTime("HHmmss");
		}
		return reports.get(threadName);
	}

	public void initReport() {
		if(initStatus) return;

		synchronized(this){
			if(initStatus) return;

			reportList.add(new HTMLReport());
			
			if(rootFolder==null){
				rootFolder = "report/" + "Run_"
				+ Util.getCurrentDatenTime("yyyyMMdd") + "_"
				+ Util.getCurrentDatenTime("HHmmss");
			}

			initStatus = true;
		}
	}

	/*public void startSuite(Caller caller, String Not_Used) {
		startSuite(caller);
	}*/

	public void startSuite(Caller caller) {
		if(!initStatus) initReport();

		for (int i = 0; i < reportList.size(); i++) {
			reportList.get(i).startSuite(caller, rootFolder);
		}
	}

	public void endSuite(Caller caller) {
		for (int i = 0; i < reportList.size(); i++) {
			reportList.get(i).endSuite(caller);
		}

	}

	public static synchronized void startTest(TestCase tc,WebDriver driver) {
		Report activeReport = getReportForThread();
		activeReport.setUpUtils(driver);
		if (!activeReport.initStatus) {
			activeReport.caller = Caller.TEST; //override
			activeReport.startSuite(activeReport.caller);
		}	
		activeReport.testCasePassCntr=0;
		activeReport.testCaseFailCntr=0;
		activeReport.testCaseWarningsCounter=0;
		activeReport.tc=tc;
		for (int i = 0; i < activeReport.reportList.size(); i++) {
			activeReport.reportList.get(i).startTest(tc);
		}
		if(activeReport.tc.getAttribute("resultPath").trim().equals(""))
			activeReport.tc.setAttribute("resultPath", rootFolder);

	}

	public static synchronized  void endTest(TestCase tc) {
		Report activeReport = getReportForThread();
		String tcStatus="Fail";
		if(activeReport.testCaseFailCntr>0){
			tcStatus="Fail";
		}else if(activeReport.testCaseWarningsCounter>0){
			tcStatus="Warning";
		}else if(activeReport.testCasePassCntr>0){
			tcStatus="Pass";
		}
		for (int i = 0; i < activeReport.reportList.size(); i++) {
			activeReport.reportList.get(i).endTest(tc);
		}
		activeReport.tc.setAttribute("tcStatus", tcStatus);
	}

	public void addToFooter(String name, String value){

	}

	public static synchronized void addDetailsToFooter(String name, String value){
		Report activeReport = getReportForThread();
		for (int i = 0; i < activeReport.reportList.size(); i++) {
			activeReport.reportList.get(i).addToFooter(name, value);
		}

	}

	public void setTittle(String tittle){

	}

	public static synchronized void setReportTittle(String tittle){

		Report activeReport = getReportForThread();
		for (int i = 0; i < activeReport.reportList.size(); i++) {
			activeReport.reportList.get(i).setTittle(tittle);
		}
	}

	public void step(String message, Status status) {
		switch(status){
		case Pass: ++testCasePassCntr; break;
		case PASS: ++testCasePassCntr; break;
		case FAIL: ++testCaseFailCntr; break;
		case Fail: ++testCaseFailCntr; break;
		case WARN: ++testCaseWarningsCounter; break;
		}
		for (int i = 0; i < reportList.size(); i++) {
			reportList.get(i).step(message, status);
		}
	}

	public static synchronized void log(String message, Status status) {
		Report activeReport = getReportForThread();
		activeReport.step(message, status);
	}

	public static synchronized void addAttachement(String message, String file,Status status) {
		Report activeReport = getReportForThread();
		for (int i = 0; i < activeReport.reportList.size(); i++) {
			activeReport.reportList.get(i).addAttachement(message, file);
		}
	}

	public static void takeScreenShot(String path){
		File scrFile1=null;
		try{
			Augmenter augmenter = new Augmenter(); 
			TakesScreenshot ts = (TakesScreenshot) augmenter.augment(driver);
			scrFile1 = ts.getScreenshotAs(OutputType.FILE);
		}catch(Exception e){
			try{
				scrFile1 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			}catch(Exception e1){
				scrFile1=null;
			}
		}
		try {
			FileUtils.copyFile(scrFile1, new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			File f=new File(path);
			if(!(f.exists())){
				takeScreenShot1(path);
			}
		}
	}

	public static void takeScreenShot1(String path){
		try{  
			//Get the screen size  
			Toolkit toolkit = Toolkit.getDefaultToolkit();  
			Dimension screenSize = toolkit.getScreenSize();  
			Rectangle rect = new Rectangle(0, 0,screenSize.width,screenSize.height);  
			Robot robot = new Robot();  
			BufferedImage image = robot.createScreenCapture(rect);  
			File file;  

			//Save the screenshot as a png  
			file = new File(path);  
			ImageIO.write(image, "png", file);  
		}catch (Exception e) {  
		}  
	}

	private void setUpUtils(WebDriver uDriver){
		driver=uDriver;
	}

}
