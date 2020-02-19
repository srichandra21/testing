package unav.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helpers.Util;
import reports.Report;
import reports.Status;

public class Login {
	
	WebDriver driver;	
	
	@FindBy(id="mat-input-0") WebElement loginid;
	@FindBy(id="secretpassword") WebElement pwd;  
	@FindBy(xpath="//div/form/div/button") WebElement loginbtn;
	
	public Login(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);

    }
	
	public void login() {
		try {
		Report.log("Login to CloudForte Unav Application", Status.BUSINESSSTEP);	
		driver.get(Util.getValue("envurl"));	
		driver.manage().window().maximize();
		loginid.sendKeys(Util.getValue("Username"));
		Report.log("Username is entered", Status.DONE);
		pwd.sendKeys(Util.getValue("Password"));
		Report.log("Password is entered", Status.DONE);
		loginbtn.click();
		Report.log("Login button is entered", Status.DONE);
		Thread.sleep(5000);
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}
	}


}
