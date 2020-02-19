package unav.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helpers.DataProvider;
import reports.Report;
import reports.Status;

public class PortFolioWorkload {
	WebDriver driver;
	
	//Pillars and Questions in Review tab before starting the review
	@FindBy(xpath="//div/div/div/div/h6") WebElement reviewType;
	@FindBy(xpath="//div/div/div/div/div/span") WebElement pillar_appMod;
	@FindBy(xpath="//div/div/div/div/div/span/span") WebElement pillar_appMod_value;
	@FindBy(xpath="//button/span[contains(text(),'Start Review')]") WebElement btn_SrtRev; 
	
	//Workload tabs
		@FindBy(xpath="//div[contains(text(),'Findings and Recommendations')]") WebElement tab_FindNRecommend;
		@FindBy(xpath="//div[text()='Review']") WebElement tab_Review;
		@FindBy(xpath="//div[contains(text(),'Milestones')]") WebElement tab_Milestones;
		@FindBy(xpath="//div[text()='Properties']") WebElement tab_Properties;
		@FindBy(xpath="//div[text()='Applications']") WebElement tab_Applications;
		
		//Review tab elements after completion of review
		@FindBy(xpath="//div/p[contains(text(),'Overall Status')]//following-sibling::span") WebElement overallStatus; 
		@FindBy(xpath="//a[contains(text(),'Application Modernization')]") WebElement pillar_AppMod_link; 
		@FindBy(xpath="//a[contains(text(),'Application Modernization')]//parent::div[1]//following-sibling::div/span") WebElement pillar_AppMod_QuesAns; 
		
		
		//Recommendation links in Findings and Recommendations tab
		@FindBy(xpath="//mat-accordion/div/mat-expansion-panel[1]/div/div/div/ul/child::li") List<WebElement> rec_AppDisc;
		@FindBy(xpath="//mat-panel-title/span[contains(text(),'Business Goals and Alignment')]") WebElement sec_BusGoalsAlign;
		@FindBy(xpath="//mat-accordion/div/mat-expansion-panel[2]/div/div/div/ul/child::li") List<WebElement> rec_BusGoalsAlign;
		@FindBy(xpath="//mat-panel-title/span[contains(text(),'Cloud Migration Complexity')]") WebElement sec_CloudMigratComplex;
		@FindBy(xpath="//mat-accordion/div/mat-expansion-panel[3]/div/div/div/ul/li") WebElement rec_CloudMigratComplex;
		@FindBy(xpath="//mat-panel-title/span[contains(text(),'Cost Benefit Assessment')]") WebElement sec_CostBeneAssess;
		@FindBy(xpath="//mat-accordion/div/mat-expansion-panel[4]/div/div/div/ul/li") WebElement rec_CostBeneAssess;
		@FindBy(xpath="//mat-panel-title/span[contains(text(),'Operational Efficiency')]") WebElement sec_OpeEff;
		@FindBy(xpath="//mat-accordion/div/mat-expansion-panel[5]/div/div/div/ul/li") WebElement rec_OpeEff;
		@FindBy(xpath="//mat-panel-title/span[contains(text(),'Regulatory Compliance Requirements')]") WebElement sec_RegCompReq;
		@FindBy(xpath="//mat-accordion/div/mat-expansion-panel[6]/div/div/div/ul/li") WebElement rec_RegCompReq;
		@FindBy(xpath="//mat-panel-title/span[contains(text(),'Third-Party Licenses')]") WebElement sec_3rdPartyLicen;
		@FindBy(xpath="//mat-accordion/div/mat-expansion-panel[7]/div/div/div/ul/li") WebElement rec_3rdPartyLicen;
	
		
		
	public PortFolioWorkload(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }	
	

	public void checkReviewTabBeforeReview() {
		Report.log("Checking the Review Tab before starting the review", Status.BUSINESSSTEP);
		if("PortFolio".equals(reviewType.getText().trim())) {
			Report.log("Review type of the Workload is Application", Status.PASS);
		}
		String pillar=pillar_appMod.getText().trim();
		System.out.println("pillar data:"+pillar);
		if(!"Application Modernization - 42".equals(pillar)) {
			Report.log("Default pillar and number of questions is not correct", Status.FAIL);
		}
		else {
			Report.log("Default pillar and number of questions is correct", Status.PASS);
		}
			
	}
	
	public void startReview(DataProvider dp) {
		try {
		btn_SrtRev.click();		
		Report.log("Start Review button clicked", Status.DONE);	
		Thread.sleep(3000);
		Report.log("Start the Review by answering the Questionaire", Status.BUSINESSSTEP);
		new PortFolioQues(driver).answerQues(dp);
		
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}
	}
	
	public void goToReview() {
		tab_Review.click();	
	}
	
		public void checkRecommendation(DataProvider dp) {
			
			Report.log("Checking the Recommendations after the review is complete", Status.BUSINESSSTEP);
			tab_FindNRecommend.click();
			try {
				
			//String Recommendations=dp.get("Recommendations");
				String Recommendations=dp.get("Recommendations value");
			
			System.out.println("Recommendations:"+Recommendations);
			
			boolean isAppDiscRec;
			List<WebElement> itemsAppDisc=rec_AppDisc;
			for (WebElement recom : itemsAppDisc) {
				isAppDiscRec=false;				
				String AppDisc=recom.getText().trim();	
				System.out.println(AppDisc);
				if(Recommendations.contains(AppDisc)) {
					isAppDiscRec=true;								
				}
				if(isAppDiscRec=false) break;
			}
				if(isAppDiscRec=true) {
					Report.log("Recommendations for Application Discovery is as expected", Status.PASS);						
				}else{
					Report.log("Recommendations for Application Discovery is as expected", Status.FAIL);		
				}
			
			sec_BusGoalsAlign.click();
			Thread.sleep(3000);
			boolean isBusGoalsAlign;
			List<WebElement> itemsBusGoalsAlign=rec_BusGoalsAlign;
			for (WebElement recom : itemsBusGoalsAlign) {
				isBusGoalsAlign=false;
				String BusGoalsAlign=recom.getText().trim();
				System.out.println(BusGoalsAlign);
				if(Recommendations.contains(BusGoalsAlign)) {
					isBusGoalsAlign=true;								
				}
				if(isBusGoalsAlign=false) break;
			}
				if(isBusGoalsAlign=true) {
					Report.log("Recommendations for Business Goals and Alignment is as expected", Status.PASS);						
				}else{
					Report.log("Recommendations for Business Goals and Alignment is as expected", Status.FAIL);		
				}
			
			sec_CloudMigratComplex.click();		
			Thread.sleep(3000);
			String clMigratComplex=rec_CloudMigratComplex.getText().trim();
			System.out.println(clMigratComplex);
			if(Recommendations.contains(clMigratComplex)) {
				Report.log("Recommendations for Cloud Migration Complexity is as expected", Status.PASS);				
			}else{
				Report.log("Recommendations for Cloud Migration Complexity is as expected", Status.FAIL);		
			}
			
			
			sec_CostBeneAssess.click();		
			Thread.sleep(3000);
			String CostBeneAssess=rec_CostBeneAssess.getText().trim();
			System.out.println(CostBeneAssess);
			if(Recommendations.contains(CostBeneAssess)) {
				Report.log("Recommendations for Cost Benefit Assessment is as expected", Status.PASS);								
			}else{
				Report.log("Recommendations for Cost Benefit Assessment is as expected", Status.FAIL);		
			}
			
			
			sec_OpeEff.click();		
			Thread.sleep(3000);
			String OpeEff=rec_OpeEff.getText().trim();
			System.out.println(OpeEff);
			if(Recommendations.contains(OpeEff)) {
				Report.log("Recommendations for Operational Efficiency is as expected", Status.PASS);								
			}else{
				Report.log("Recommendations for Operational Efficiency is as expected", Status.FAIL);		
			}
			
						
			sec_RegCompReq.click();		
			Thread.sleep(3000);
			String RegCompReq=rec_RegCompReq.getText().trim();
			System.out.println(RegCompReq);
			if(Recommendations.contains(RegCompReq)) {
				Report.log("Recommendations for Regulatory Compliance Requirements is as expected", Status.PASS);	
			}else{
				Report.log("Recommendations for Regulatory Compliance Requirements is as expected", Status.FAIL);		
			}
			
			
			sec_3rdPartyLicen.click();		
			Thread.sleep(3000);
			String thirdPartyLicen=rec_3rdPartyLicen.getText().trim();
			System.out.println(thirdPartyLicen);
			if(Recommendations.contains(thirdPartyLicen)) {
				Report.log("Recommendations for Third-Party Licenses is as expected", Status.PASS);	
			}else{
				Report.log("Recommendations for Third-Party Licenses is as expected", Status.FAIL);		
			}								
			
			}catch(Exception e) {
				Report.log(e.getMessage(), Status.FAIL);		
			}					
		
		}
		
		
		public void checkReviewTabAfterReview() {
			try {
			Report.log("Checking the Review Tab after the review is complete", Status.BUSINESSSTEP);
			
			if(overallStatus.getText().equals("Answered")) {
				Report.log("OverAllStatus should be Answered", Status.PASS);	
			}else{
				Report.log("OverAllStatus should be Answered", Status.FAIL);		
			}
			
			if(pillar_AppMod_QuesAns.getText().equals("42")) {
				Report.log("All questions of Application Modernization are answered", Status.PASS);	
			}else{
				Report.log("All questions of Application Modernization are answered", Status.FAIL);		
			}
						
			}catch(Exception e) {
				Report.log(e.getMessage(), Status.FAIL);
			}
		}
	
		
	
	
}
