package unav.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helpers.DataProvider;
import reports.Report;
import reports.Status;

public class DefineCloudNativeWL {
	WebDriver driver;
	
	@FindBy(id="mat-input-3") WebElement wlName;
	@FindBy(id="mat-input-4") WebElement wlDesc;
	@FindBy(xpath="//mat-select[@id='mat-select-0']/div/div[2]/div") WebElement select_CloudProv;
	@FindBy(xpath="//mat-select[@id='region']/div/div[2]/div") WebElement select_Region;
	@FindBy(xpath="//mat-select[@id='mat-select-2']/div/div[2]/div") WebElement select_IndusType;
	@FindBy(xpath="//mat-select[@id='industry']/div/div[2]/div") WebElement select_Indus;	
	@FindBy(xpath="//div/mat-radio-group/mat-radio-button[1]/label/div[1]/div[1]") WebElement env_Prod;
	@FindBy(xpath="//div/mat-radio-group/mat-radio-button[2]/label/div[1]/div[1]") WebElement env_PreProd;
	@FindBy(id="mat-input-5") WebElement AccId;
	@FindBy(xpath="//button/span[contains(text(),'Define Workload')]") WebElement btn_defWL;
	
	public DefineCloudNativeWL(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }
	
	public void enterCloudNativeWLDetails(DataProvider dp) {
		try {
	    Report.log("Enter Workload details in Define Cloud Native Workload page", Status.BUSINESSSTEP);
	    wlName.sendKeys(dp.get("WorkloadName"));
	    System.out.println("workload name:"+dp.get("WorkloadName"));
	    Report.log("Workload name is entered as "+dp.get("WorkloadName"), Status.DONE);
	    wlDesc.sendKeys(dp.get("WorkloadDesc"));
	    Report.log("Workload Description is entered as "+dp.get("WorkloadDesc"), Status.DONE);
		select_CloudProv.click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//mat-option/span[contains(text(),'"+dp.get("CloudProvider")+"')]")).click();
		Report.log("Cloud Provider is selected as "+dp.get("CloudProvider"), Status.DONE);
		Thread.sleep(3000);
		select_Region.click();	
		Thread.sleep(3000);
		driver.findElement(By.xpath("//mat-option/span[contains(text(),'"+dp.get("Region")+"')]")).click();
		Report.log("Region is selected as "+dp.get("Region"), Status.DONE);
		Thread.sleep(3000);
		select_IndusType.click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//mat-option/span[contains(text(),'"+dp.get("IndustryType")+"')]")).click();
		Report.log("Industry Type is selected as "+dp.get("IndustryType"), Status.DONE);
		Thread.sleep(3000);
		select_Indus.click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//mat-option/span[contains(text(),'"+dp.get("Industry")+"')]")).click();
		Report.log("Industry is selected as "+dp.get("IndustryType"), Status.DONE);
		Thread.sleep(3000);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(5000);
		if(dp.get("Environment").trim().equalsIgnoreCase("Production")) {
			env_Prod.click();
			Report.log("Environment is selected as "+dp.get("Environment"), Status.DONE);
		}else if(dp.get("Environment").trim().equalsIgnoreCase("Pre-Production")) {
			env_PreProd.click();
			Report.log("Environment is selected as "+dp.get("Environment"), Status.DONE);
		}
		AccId.sendKeys(dp.get("AccId"));	
		Report.log("Account ID is entered as "+dp.get("AccId"), Status.DONE);
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
