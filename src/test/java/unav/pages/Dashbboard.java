package unav.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import reports.Report;
import reports.Status;

public class Dashbboard {

	WebDriver driver;		
	@FindBy(xpath="//span[text()='AWSWL123']") WebElement workload;
	@FindBy(xpath="//mat-radio-button[@id='mat-radio-3']/label/div/div") WebElement radio_appMod;
	@FindBy(xpath="//button/span[contains(text(),'Define Workload')]") WebElement btn_defineWorkload;
	@FindBy(xpath="//button/span/span") WebElement user;
	@FindBy(xpath="//button/span[text()='Logout']") WebElement logout;
	
	public Dashbboard(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);

    }
	
	public void selectWorkload() {
		workload.click();
	}
	
	public void createAppWL() throws InterruptedException {
		try {
		Report.log("Create Application workload", Status.BUSINESSSTEP);
		radio_appMod.click();
		Report.log("Application Modernization radio button is selected", Status.DONE);
		btn_defineWorkload.click();
		Report.log("Define Workload button is selected", Status.DONE);
		Thread.sleep(5000);
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}		
	}
	
	public void createCloudNativeWL() throws InterruptedException {
		try {
		Report.log("Create Application workload", Status.BUSINESSSTEP);
		Report.log("Cloud Native radio button is selected", Status.DONE);
		btn_defineWorkload.click();
		Report.log("Define Workload button is selected", Status.DONE);
		Thread.sleep(5000);
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}		
	}
	
	public void logout() {
		try {
		Report.log("Logout of the appication", Status.BUSINESSSTEP);
		user.click();		
		 new Actions(driver).moveToElement(logout).click().build().perform();
		 Report.log("Logout clicked", Status.DONE);
		}catch(Exception e) {
			 Report.log(e.getMessage(), Status.FAIL);
		}
		
	}
	
	
	
}
