package unav.pages;

import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helpers.DataProvider;
import helpers.Util;
import reports.Report;
import reports.Status;

public class WorkLoad {
	WebDriver driver;
	
	@FindBy(xpath="//button/span/div/div[contains(text(),'Generate Report')]") WebElement generaterpt;
	@FindBy(xpath="//*[text()='PDF']") WebElement rpt_PDF;
	@FindBy(xpath="//*[text()='Word']") WebElement rpt_Word;
	@FindBy(xpath="//button/span[contains(text(),'Delete Workload')]") WebElement btn_delete;
	@FindBy(xpath="//button/span[contains(text(),'Yes')]") WebElement alrt_delete;
	
	
	public WorkLoad(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);

    }	
	
	
	public void deleteWorkload() {
		try {
			
			Report.log("Delete Workload", Status.BUSINESSSTEP);
		btn_delete.click();
		Report.log("Click Delete workload button", Status.DONE);
		 driver.switchTo().window(driver.getWindowHandle());
		 alrt_delete.click();
		 
		 Thread.sleep(3000);
		 driver.switchTo().defaultContent();
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}
	}

	public void generatePDFReport(DataProvider dp) {
		try {
			
			Report.log("Generate PDF report", Status.BUSINESSSTEP);
		  new Actions(driver).moveToElement(generaterpt).perform();	 
		  rpt_PDF.click();
		  Thread.sleep(10000);
		  Robot robot = new Robot(); robot.keyPress(KeyEvent.VK_CONTROL);
		  robot.keyPress(KeyEvent.VK_T); robot.keyRelease(KeyEvent.VK_CONTROL);
		  robot.keyRelease(KeyEvent.VK_T);
		  Thread.sleep(2000);
		  System.out.println("1");
		  ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		  System.out.println("2");
		  driver.switchTo().window(tabs.get(1)); System.out.println("3");
		  Thread.sleep(10000);
		  String downloadPath=Util.getValue("reportDownloadPath");
		  String PDFfilePath="file:///".concat(downloadPath).concat(dp.get("WorkloadName")).concat(".pdf");
		  System.out.println("4");
		  driver.get(PDFfilePath);	
		  System.out.println("5");
		  Thread.sleep(5000);
		  Report.log("PDF Report generated successfully", Status.PASS);
		  driver.close(); //Switch back to your original tab	
		 // ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(0));
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}
		deletePDFReports(dp);
	}
	
	public void generateWordReport(DataProvider dp) {
		try {
			Report.log("Generate Word report", Status.BUSINESSSTEP);
		  new Actions(driver).moveToElement(generaterpt).perform();	
		  rpt_Word.click();
		  Thread.sleep(10000);
		  String filePath=Util.getValue("reportDownloadPath").concat(dp.get("WorkloadName")).concat(".docx");
		  File file1 = new File(filePath);   
		  if(!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not  
		  {  
		  System.out.println("not supported");  
		  return;  
		  }  
		  Desktop desktop = Desktop.getDesktop();  
		  if(file1.exists())    {     //checks file exists or not  
		  desktop.open(file1); 
		 
		  System.out.println("doc file opened");
		  }
		  Thread.sleep(5000);
		  Robot robot = new Robot(); robot.keyPress(KeyEvent.VK_ALT);
		  robot.keyPress(KeyEvent.VK_F4); robot.keyRelease(KeyEvent.VK_ALT);
		  robot.keyRelease(KeyEvent.VK_F4);
		  Report.log("Word Report generated successfully", Status.PASS);
		 // Thread.sleep(5000);
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}
		deleteWordReports(dp);
	}
		/*
		 * robot.keyPress(KeyEvent.VK_CONTROL); robot.keyPress(KeyEvent.VK_W);
		 * robot.keyRelease(KeyEvent.VK_CONTROL); robot.keyRelease(KeyEvent.VK_W);
		 */
		public void deleteWordReports(DataProvider dp) {
			try {
			 String filePath=Util.getValue("reportDownloadPath").concat(dp.get("WorkloadName")).concat(".docx");
			  File file1 = new File(filePath); 
		  if(file1.delete()) {				  
			  System.out.println("doc file deleted"); }		
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}
	}
	
	
	public void deletePDFReports(DataProvider dp) {
		try {
		 
		  String filePath=Util.getValue("reportDownloadPath").concat(dp.get("WorkloadName")).concat(".pdf");
		  File file = new File(filePath);
		 // System.out.println(file.getPath());
		  if(file.delete()) {		  
			  System.out.println("pdf file deleted"); 
		  }
		  }catch(Exception e) {
			  Report.log(e.getMessage(), Status.FAIL);
		  }
		
	}
}
