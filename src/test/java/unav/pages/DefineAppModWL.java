package unav.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helpers.DataProvider;
import reports.Report;
import reports.Status;

public class DefineAppModWL {
	WebDriver driver;
	@FindBy(id="mat-input-3") WebElement wlName;
	@FindBy(id="mat-input-4") WebElement wlDesc;
	@FindBy(xpath="//div/mat-radio-group/mat-radio-button[1]/label/div[1]") WebElement solType_App;
	@FindBy(xpath="//div/mat-radio-group/mat-radio-button[2]/label/div[1]") WebElement solType_Port;
	@FindBy(xpath="//mat-select[@id='mat-select-2']/div/div[2]/div") WebElement select_portfolioWL;
	@FindBy(xpath="//mat-form-field[1]/div/div/div/mat-select/div/div[2]") WebElement select_ClientName;
	@FindBy(xpath="//mat-form-field[2]/div/div/div/mat-select/div/div[2]") WebElement select_ClientRegion;
	@FindBy(xpath="//mat-select[@id='mat-select-3']/div/div[2]/div") WebElement select_appType;
	@FindBy(xpath="//button/span[contains(text(),'Define Workload')]") WebElement btn_defWL;
	//@FindBy(xpath="//div[@id='cdk-overlay-0']/div/div//descendant::mat-option/span[contains(text(),'"+dp.get("abc")+"')]") WebElement portfolioWL_option;
	
	public DefineAppModWL(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);

    }
	
	
	public void enterAppWLDetails(DataProvider dp) {
		try {
	    Report.log("Enter Application details in Define Application Modernization Workload page", Status.BUSINESSSTEP);
	    wlName.sendKeys(dp.get("WorkloadName"));
	    Report.log("Workload name is entered as "+dp.get("WorkloadName"), Status.DONE);
	    wlDesc.sendKeys(dp.get("WorkloadDesc"));
	    Report.log("Workload Description is entered as "+dp.get("WorkloadDesc"), Status.DONE);
		Thread.sleep(3000);
		solType_App.click();
		 Report.log("Solution Type is selected as Application", Status.DONE);
		Thread.sleep(3000);
		select_portfolioWL.click();
		driver.findElement(By.xpath(
				"//div[@id='cdk-overlay-0']/div/div//descendant::mat-option/span[contains(text(),'"+dp.get("PortfolioWL")+"')]"))
				.click();
		 Report.log("Portfolio workload is selected as "+dp.get("PortfolioWL"), Status.DONE);
		select_appType.click();		
		driver.findElement(
				By.xpath("//div[@id='cdk-overlay-1']/div/div//descendant::mat-option/span[contains(text(),'"+dp.get("AppType")+"')]"))
				.click();
		 Report.log("Application Type is selected as "+dp.get("AppType"), Status.DONE);
		btn_defWL.click();
		 Report.log("Define Workload button is clicked", Status.DONE);
		Thread.sleep(3000);
		if(driver.getPageSource().contains(dp.get("WorkloadName")+" Workload created")) {
		Report.log(dp.get("WorkloadName")+" Workload created successfully", Status.PASS);
		}else
			Report.log(dp.get("WorkloadName")+" Workload not created" , Status.FAIL);
		}
		catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}
	}
	
	
	public void enterPortWLDetails(DataProvider dp) {
		try {
	    Report.log("Enter PortFolio details in Define Application Modernization Workload page", Status.BUSINESSSTEP);
	    wlName.sendKeys(dp.get("WorkloadName"));
	    Report.log("Workload name is entered as "+dp.get("WorkloadName"), Status.DONE);
	    wlDesc.sendKeys(dp.get("WorkloadDesc"));
	    Report.log("Workload Description is entered as "+dp.get("WorkloadDesc"), Status.DONE);
		Thread.sleep(3000);
		solType_Port.click();
		 Report.log("Solution Type is selected as PortFolio", Status.DONE);
		Thread.sleep(3000);
		select_ClientName.click();
		driver.findElement(By.xpath(
				"//div[@id='cdk-overlay-0']/div/div//descendant::mat-option/span[contains(text(),'"+dp.get("ClientName").trim()+"')]"))
				.click();
		 Report.log("Client Name is selected as "+dp.get("ClientName"), Status.DONE);
		select_ClientRegion.click();		
		driver.findElement(
				By.xpath("//div[@id='cdk-overlay-1']/div/div//descendant::mat-option/span[contains(text(),'"+dp.get("ClientRegion").trim()+"')]"))
				.click();
		 Report.log("Client Region is selected as "+dp.get("ClientRegion"), Status.DONE);
		btn_defWL.click();
		 Report.log("Define Workload button is clicked", Status.DONE);
		Thread.sleep(3000);
		if(driver.getPageSource().contains(dp.get("WorkloadName")+" Workload created")) {
		Report.log(dp.get("WorkloadName")+" Workload created successfully", Status.PASS);
		}else
			Report.log(dp.get("WorkloadName")+" Workload not created" , Status.FAIL);
		}
		catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}
	}
	
	
	
}
