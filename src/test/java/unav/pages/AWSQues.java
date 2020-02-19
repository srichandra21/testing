package unav.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helpers.DataProvider;
import reports.Report;
import reports.Status;

public class AWSQues {
	WebDriver driver;
	
	@FindBy(xpath="//button/span[contains(text(),'Next')]") WebElement btn_Next;
	@FindBy(xpath="//button/span[contains(text(),'Save and Exit')]") WebElement btn_SaveNExit;
	@FindBy(xpath="//button/span[contains(text(),'Previous')]") WebElement btn_Prvious;
	@FindBy(xpath="//mat-checkbox/label/div") WebElement chk_Flg4Review;
	
	public AWSQues(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }	
	
	public void answerQues(DataProvider dp) {
		try {
			for (int i=1;i<=11;i++) {
				for(int k=1;k<=9;k++) {
					if(i<=9) {
					if(dp.get("Sec_0"+i+"_opt0"+k).equalsIgnoreCase("true")) {
						driver.findElement(By.xpath("//div["+k+"]/mat-checkbox/label/div")).click();
						Report.log("Sec_0"+i+"_opt0"+k+" is selected", Status.DONE);	
					}		
				}else {
					if(dp.get("Sec_"+i+"_opt0"+k).equalsIgnoreCase("true")) {
						driver.findElement(By.xpath("//div["+k+"]/mat-checkbox/label/div")).click();
						Report.log("Sec_"+i+"_opt0"+k+" is selected", Status.DONE);	
					}	
				}
				}
				btn_Next.click();
				Thread.sleep(3000);
			
			}
			for (int i=1;i<=9;i++) {
				for(int k=1;k<=9;k++) {
					if(i<=9) {
					if(dp.get("Cos_0"+i+"_opt0"+k).equalsIgnoreCase("true")) {
						driver.findElement(By.xpath("//div["+k+"]/mat-checkbox/label/div")).click();
						Report.log("Cos_0"+i+"_opt0"+k+" is selected", Status.DONE);	
					}	
					}else {
						if(dp.get("Cos_"+i+"_opt0"+k).equalsIgnoreCase("true")) {
							driver.findElement(By.xpath("//div["+k+"]/mat-checkbox/label/div")).click();
							Report.log("Cos_"+i+"_opt0"+k+" is selected", Status.DONE);	
						}
					}
				}
				btn_Next.click();
				Thread.sleep(3000);
			}
			
			for (int i=1;i<=9;i++) {
				for(int k=1;k<=11;k++) {
					if(k<=9) {					
					if(dp.get("Ops_0"+i+"_opt0"+k).equalsIgnoreCase("true")) {							
						driver.findElement(By.xpath("//div["+k+"]/mat-checkbox/label/div")).click();
						Report.log("Ops_0"+i+"_opt0"+k+" is selected", Status.DONE);
					}
					}else {
						if(dp.get("Ops_0"+i+"_opt"+k).equalsIgnoreCase("true")) {							
							driver.findElement(By.xpath("//div["+k+"]/mat-checkbox/label/div")).click();
							Report.log("Ops_0"+i+"_opt"+k+" is selected", Status.DONE);
						}
					}
				}
				btn_Next.click();
				Thread.sleep(3000);
			}
			
			for (int i=1;i<=9;i++) {
				for(int k=1;k<=8;k++) {
					if(dp.get("Rel_0"+i+"_opt0"+k).equalsIgnoreCase("true")) {
						driver.findElement(By.xpath("//div["+k+"]/mat-checkbox/label/div")).click();
						Report.log("Rel_0"+i+"_opt0"+k+" is selected", Status.DONE);	
					}
				}
				btn_Next.click();
				Thread.sleep(3000);
			}
			
			for (int i=1;i<=8;i++) {
				for(int k=1;k<=9;k++) {
					if(dp.get("Perf_0"+i+"_opt0"+k).equalsIgnoreCase("true")) {
						driver.findElement(By.xpath("//div["+k+"]/mat-checkbox/label/div")).click();
						Report.log("Perf_0"+i+"_opt0"+k+" is selected", Status.DONE);
					}					
				}
				if(i!=8) {
					btn_Next.click();					
				}else {
					btn_SaveNExit.click();
				}
				Thread.sleep(3000);
			}
			
			
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
			Report.log("Questions answered Successfully", Status.FAIL);
		}
	
	}

}
