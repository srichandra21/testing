package unav.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helpers.DataProvider;
import reports.Report;
import reports.Status;

public class AzureQues {
	WebDriver driver;
	
	@FindBy(xpath="//button/span[contains(text(),'Next')]") WebElement btn_Next;
	@FindBy(xpath="//button/span[contains(text(),'Save and Exit')]") WebElement btn_SaveNExit;
	@FindBy(xpath="//button/span[contains(text(),'Previous')]") WebElement btn_Prvious;
	@FindBy(xpath="//mat-checkbox/label/div") WebElement chk_Flg4Review;
	
	public AzureQues(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }	
	
	public void answerQues(DataProvider dp) {
		try {
			for (int i=1;i<=10;i++) {
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
			for (int i=1;i<=14;i++) {
				for(int k=1;k<=6;k++) {
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
			
			for (int i=1;i<=5;i++) {
				for(int k=1;k<=7;k++) {
					if(dp.get("Rel_0"+i+"_opt0"+k).equalsIgnoreCase("true")) {
						driver.findElement(By.xpath("//div["+k+"]/mat-checkbox/label/div")).click();
						Report.log("Rel_0"+i+"_opt0"+k+" is selected", Status.DONE);	
					}
				}
				btn_Next.click();
				Thread.sleep(3000);
			}
			
			for (int i=1;i<=4;i++) {
				for(int k=1;k<=5;k++) {
					if(dp.get("Perf_0"+i+"_opt0"+k).equalsIgnoreCase("true")) {
						driver.findElement(By.xpath("//div["+k+"]/mat-checkbox/label/div")).click();
						Report.log("Perf_0"+i+"_opt0"+k+" is selected", Status.DONE);
					}					
				}
				btn_Next.click();
				Thread.sleep(3000);
			}
			
			for (int i=1;i<=6;i++) {
				for(int k=1;k<=6;k++) {
					//Ops_01_opt01
					String str="Ops_0"+i+"_opt0"+k;
					System.out.println(str+":"+dp.get(str));
					if(dp.get(str).equalsIgnoreCase("true")) {	
						System.out.println(k);
						driver.findElement(By.xpath("//div["+k+"]/mat-checkbox/label/div")).click();
						Report.log("Ops_0"+i+"_opt0"+k+" is selected", Status.DONE);
					}					
				}
				if(i!=6) {
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
