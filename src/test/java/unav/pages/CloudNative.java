package unav.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class CloudNative {
 
	WebDriver driver;		

	@FindBy(xpath="//div[contains(text(),'Milestones')]") WebElement milestones;
	@FindBy(xpath="//div[text()='Review']") WebElement review;
	@FindBy(xpath="//button/span/div[text()=' Generate Report ']") WebElement generaterpt;
	
	
	public CloudNative(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);

    }
	
	public void selectMilestones() {
		milestones.click();
	}
	
	public void selectReview() {
		review.click();
	}
	
	public void generateReport() {
		try {
		generaterpt.click();
		  Robot robot = new Robot(); robot.keyPress(KeyEvent.VK_CONTROL);
		  robot.keyPress(KeyEvent.VK_T); robot.keyRelease(KeyEvent.VK_CONTROL);
		  robot.keyRelease(KeyEvent.VK_T);
		  
		  ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		  driver.switchTo().window(tabs.get(1)); Thread.sleep(10000);
		  driver.get("file:///C:/Users/Ramachas/Downloads/AZWL1.pdf");
		  
		  Thread.sleep(3000);
		}catch(Exception e) {}
	}
	
	public void closeReports() {
		 driver.close(); //Switch back to your original tab
		 ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		  driver.switchTo().window(tabs.get(0));
		  
		  File file = new File("C:\\Users\\Ramachas\\Downloads\\AZWL1.pdf");
		  System.out.println(file.getPath()); System.out.println(file.delete());
		  if(file.delete()) {		  
			  System.out.println("file deleted"); 
		  }
	}
}
