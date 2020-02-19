package unav.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helpers.DataProvider;
import reports.Report;
import reports.Status;

public class ApplicationQues {
	WebDriver driver;
	@FindBy(id="mat-slide-toggle-1-input") WebElement tgl_QuesNotAppli;
	@FindBy(xpath="//div/mat-radio-group/div[1]/mat-radio-button/label/div[1]/div[1]") WebElement opt_First;
	@FindBy(xpath="//div/mat-radio-group/div[2]/mat-radio-button/label/div[1]/div[1]") WebElement opt_Second;
	@FindBy(xpath="//div/mat-radio-group/div[3]/mat-radio-button/label/div[1]/div[1]") WebElement opt_Third;
	@FindBy(xpath="//button/span[contains(text(),'Next')]") WebElement btn_Next;
	@FindBy(xpath="//button/span[contains(text(),'Save and Exit')]") WebElement btn_SaveNExit;
	@FindBy(xpath="//button/span[contains(text(),'Previous')]") WebElement btn_Prvious;
	@FindBy(xpath="//mat-checkbox/label/div") WebElement chk_Flg4Review;
	
	
	
	public ApplicationQues(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);

    }	
	
	public void answerQues(DataProvider dp) {
		try {
		String quesOpt;
		for (int i = 1; i <=63; i++) {
			if(i==43) {
				continue;
			}
			if(i>=1 && i<=9) {
				quesOpt=dp.get("App_Mod_".concat("0"+i)).trim();
			}else {
				quesOpt=dp.get("App_Mod_"+i).trim();
			}
			
			if(quesOpt.equalsIgnoreCase("Yes")||quesOpt.equalsIgnoreCase("Lesser than 1000")||quesOpt.equalsIgnoreCase("Manual")
					||quesOpt.equalsIgnoreCase("Yes updated 100%")||quesOpt.equalsIgnoreCase("Mostly internal users")||quesOpt.equalsIgnoreCase("Upto 1000")
					||quesOpt.equalsIgnoreCase("> 90% Java / J2EE or Microsoft .NET")||quesOpt.equalsIgnoreCase("Relational single vendor")||quesOpt.equalsIgnoreCase("Mostly Database storage")
					||quesOpt.equalsIgnoreCase("Upto 100 GB")||quesOpt.equalsIgnoreCase("No external interfaces outside the application portfolio")||quesOpt.equalsIgnoreCase("Mostly through services/APIs")||quesOpt.equalsIgnoreCase("No / Not more than 1")
					||quesOpt.equalsIgnoreCase("Data archival or retention requirements is for a period of 3 years or less")||quesOpt.equalsIgnoreCase("No stored procedures or database triggers")) {
				opt_First.click();
				Report.log(quesOpt+" is selected for ques App_Mod_"+i, Status.DONE);
			}
			if(quesOpt.equalsIgnoreCase("No")||quesOpt.equalsIgnoreCase("Multiple 1000s")||quesOpt.equalsIgnoreCase("Partially automated")
					||quesOpt.equalsIgnoreCase(">50% accurate")||quesOpt.equalsIgnoreCase("Equal mix of internal & external users")||quesOpt.equalsIgnoreCase("Between 1001 - 10000")
					||quesOpt.equalsIgnoreCase(">90% of Java / J2EE and Microsoft .NET combined")||quesOpt.equalsIgnoreCase("Relational multiple vendors")||quesOpt.equalsIgnoreCase("Mix of Database storage & block storage")
					||quesOpt.equalsIgnoreCase("Upto 1 TB")||quesOpt.equalsIgnoreCase("Upto 2")||quesOpt.equalsIgnoreCase("Mostly point to point")||quesOpt.equalsIgnoreCase("Not more than 2")
					||quesOpt.equalsIgnoreCase("Data archival or retention requirements is for a period of 5 years or less")||quesOpt.equalsIgnoreCase("Limited (upto 5) stored procedures and database triggers combined")) {
				opt_Second.click();
				Report.log(quesOpt+" is selected for ques App_Mod_"+i, Status.DONE);
			}
			if(quesOpt.equalsIgnoreCase("May be / Don�t know")||quesOpt.equalsIgnoreCase("Don't Know")||quesOpt.equalsIgnoreCase("Fully automated")
					||quesOpt.equalsIgnoreCase("Not Updated / Does not exist")||quesOpt.equalsIgnoreCase("Mostly external users")||quesOpt.equalsIgnoreCase("> 10000 / Dynamic")
					||quesOpt.equalsIgnoreCase("Significantly legacy applications including mainframes")||quesOpt.equalsIgnoreCase("Relational NoSQL & Mainframe Multiple vendors")||quesOpt.equalsIgnoreCase("Mix of Database storage Block storage & File storage")
					||quesOpt.equalsIgnoreCase("> 1 TB")||quesOpt.equalsIgnoreCase(">2")||quesOpt.equalsIgnoreCase("More than 2")
					||quesOpt.equalsIgnoreCase("Data archival or retention requirements is for a period of more than 5 years")||quesOpt.equalsIgnoreCase("Significant (more than 5) stored procedures and database triggers combined")
					||quesOpt.equalsIgnoreCase("Both")) {
				opt_Third.click();
				Report.log(quesOpt+" is selected for ques App_Mod_"+i, Status.DONE);
			}			
			btn_Next.click();
			
			Thread.sleep(3000);
			
		}
		
		for (int i = 1; i <=9; i++) {			
		quesOpt=dp.get("Cst_0"+i).trim();
			
		if(quesOpt.equalsIgnoreCase("Yes")) {
			opt_First.click();
			Report.log(quesOpt+" is selected for ques Cst_0"+i, Status.DONE);
		}
		if(quesOpt.equalsIgnoreCase("No")) {
			opt_Second.click();
			Report.log(quesOpt+" is selected for ques Cst_0"+i, Status.DONE);
		}
		if(quesOpt.equalsIgnoreCase("Don't Know")) {
			opt_Third.click();
			Report.log(quesOpt+" is selected for ques Cst_0"+i, Status.DONE);
		}
		
		btn_Next.click();
		Thread.sleep(3000);
		}
		
		for (int i = 1; i <=6; i++) {			
		quesOpt=dp.get("Per_0"+i).trim();
			
		if(quesOpt.equalsIgnoreCase("Yes")) {
			opt_First.click();
			Report.log(quesOpt+" is selected for ques Per_0"+i, Status.DONE);
		}
		if(quesOpt.equalsIgnoreCase("No")) {
			opt_Second.click();
			Report.log(quesOpt+" is selected for ques Per_0"+i, Status.DONE);
		}
		if(quesOpt.equalsIgnoreCase("Don't Know")) {
			opt_Third.click();
			Report.log(quesOpt+" is selected for ques Per_0"+i, Status.DONE);
		}
		btn_Next.click();
		Thread.sleep(3000);
		}
		
		for (int i = 1; i <=6; i++) {			
			quesOpt=dp.get("Sec_0"+i).trim();
				
			if(quesOpt.equalsIgnoreCase("Yes")) {
				opt_First.click();
				Report.log(quesOpt+" is selected for ques Sec_0"+i, Status.DONE);
			}
			if(quesOpt.equalsIgnoreCase("No")) {
				opt_Second.click();
				Report.log(quesOpt+" is selected for ques Sec_0"+i, Status.DONE);
			}
			if(quesOpt.equalsIgnoreCase("Don't Know")) {
				opt_Third.click();
				Report.log(quesOpt+" is selected for ques Sec_0"+i, Status.DONE);
			}
			btn_Next.click();
			Thread.sleep(3000);
			}
		
		for (int i = 1; i <=6; i++) {			
			quesOpt=dp.get("Rel_0"+i).trim();
				
			if(quesOpt.equalsIgnoreCase("Yes")) {
				opt_First.click();
				Report.log(quesOpt+" is selected for ques Rel_0"+i, Status.DONE);
			}
			if(quesOpt.equalsIgnoreCase("No")) {
				opt_Second.click();
				Report.log(quesOpt+" is selected for ques Rel_0"+i, Status.DONE);
			}
			if(quesOpt.equalsIgnoreCase("Don't Know")) {
				opt_Third.click();
				Report.log(quesOpt+" is selected for ques Rel_0"+i, Status.DONE);
			}
			btn_Next.click();
			Thread.sleep(3000);
			}
		
		for (int i = 1; i <=15; i++) {			
			if(i>=1 && i<=9) {
				quesOpt=dp.get("Ope_Exc_".concat("0"+i)).trim();
			}else {
				quesOpt=dp.get("Ope_Exc_"+i).trim();
			}
				
			if(quesOpt.equalsIgnoreCase("Yes")) {
				opt_First.click();
				Report.log(quesOpt+" is selected for ques Ope_Exc_"+i, Status.DONE);
			}
			if(quesOpt.equalsIgnoreCase("No")) {
				opt_Second.click();
				Report.log(quesOpt+" is selected for ques Ope_Exc_"+i, Status.DONE);
			}
			if(quesOpt.equalsIgnoreCase("Don't Know")) {
				opt_Third.click();
				Report.log(quesOpt+" is selected for ques Ope_Exc_"+i, Status.DONE);
			}
			if(i!=15) {
				btn_Next.click();
			}else {
				btn_SaveNExit.click();
				Report.log("Save and Exit is clicked", Status.DONE);
			}
			
			Thread.sleep(3000);
			}
		
			/*
			 * if(dp.get("Ope_Exc_15").equalsIgnoreCase("yes")) { opt_First.click();
			 * Report.log(dp.get("Ope_Exc_15")+" is selected for ques Ope_Exc_15",
			 * Status.DONE); }else if(dp.get("Ope_Exc_15").equalsIgnoreCase("No")) {
			 * opt_Second.click();
			 * Report.log(dp.get("Ope_Exc_15")+" is selected for ques Ope_Exc_15",
			 * Status.DONE); }else if(dp.get("Ope_Exc_15").equalsIgnoreCase("Don't Know")) {
			 * opt_Third.click();
			 * Report.log(dp.get("Ope_Exc_15")+" is selected for ques Ope_Exc_15",
			 * Status.DONE); }
			 * 
			 * btn_SaveNExit.click(); Report.log("Save and Exit is clicked", Status.DONE);
			 * Thread.sleep(3000);
			 */
		
			Report.log("Questions answered Successfully", Status.PASS);
		
		
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
			Report.log("Questions answered Successfully", Status.FAIL);
		}
	
	}
		

}
