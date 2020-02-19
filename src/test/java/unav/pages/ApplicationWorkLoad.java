package unav.pages;

import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helpers.DataProvider;
import helpers.Util;
import reports.Report;
import reports.Status;

public class ApplicationWorkLoad {
	WebDriver driver;
	
	//Pillars and Questions in Review tab before starting the review
	@FindBy(xpath="//div/div/div/div/h6") WebElement reviewType;
	@FindBy(xpath="//div/div/div/div/div/span[1]") WebElement pillar_appMod;
	@FindBy(xpath="//div/div/div/div/div/span[1]/span") WebElement pillar_appMod_value;
	@FindBy(xpath="//div/div/div/div/div/span[2]") WebElement pillar_Cost;
	@FindBy(xpath="//div/div/div/div/div/span[2]/span") WebElement pillar_Cost_value;
	@FindBy(xpath="//div/div/div/div/div/span[3]") WebElement pillar_Perf;
	@FindBy(xpath="//div/div/div/div/div/span[3]/span") WebElement pillar_Perf_value;
	@FindBy(xpath="//div/div/div/div/div/span[4]") WebElement pillar_Sec;
	@FindBy(xpath="//div/div/div/div/div/span[4]/span") WebElement pillar_Sec_value;
	@FindBy(xpath="//div/div/div/div/div/span[5]") WebElement pillar_Rel;
	@FindBy(xpath="//div/div/div/div/div/span[5]/span") WebElement pillar_Rel_value;
	@FindBy(xpath="//div/div/div/div/div/span[6]") WebElement pillar_OpEx;
	@FindBy(xpath="//div/div/div/div/div/span[6]/span") WebElement pillar_OpEx_value; 
	@FindBy(xpath="//button/span[contains(text(),'Start Review')]") WebElement btn_SrtRev; 
	
	
	//Review tab elements after completion of review
	@FindBy(xpath="//div/p[contains(text(),'Overall Status')]//following-sibling::span") WebElement overallStatus; 
	@FindBy(xpath="//a[contains(text(),'Application Modernization')]") WebElement pillar_AppMod_link; 
	@FindBy(xpath="//a[contains(text(),'Application Modernization')]//parent::div[1]//following-sibling::div/span") WebElement pillar_AppMod_QuesAns; 
	@FindBy(xpath="//a[contains(text(),'Cost')]") WebElement pillar_Cost_link; 
	@FindBy(xpath="//a[contains(text(),'Cost')]//parent::div[1]//following-sibling::div/span") WebElement pillar_Cost_QuesAns;
	@FindBy(xpath="//a[contains(text(),'Performance')]") WebElement pillar_Perf_link; 
	@FindBy(xpath="//a[contains(text(),'Performance')]//parent::div[1]//following-sibling::div/span") WebElement pillar_Perf_QuesAns; 
	@FindBy(xpath="//a[contains(text(),'Security')]") WebElement pillar_Sec_link; 
	@FindBy(xpath="//a[contains(text(),'Security')]//parent::div[1]//following-sibling::div/span") WebElement pillar_Sec_QuesAns; 
	@FindBy(xpath="//a[contains(text(),'Reliability')]") WebElement pillar_Rel_link; 
	@FindBy(xpath="//a[contains(text(),'Reliability')]//parent::div[1]//following-sibling::div/span") WebElement pillar_Rel_QuesAns; 
	@FindBy(xpath="//a[contains(text(),'Operational Excellence')]") WebElement pillar_OpEx_link; 
	@FindBy(xpath="//a[contains(text(),'Operational Excellence')]//parent::div[1]//following-sibling::div/span") WebElement pillar_OpEx_QuesAns; 
	
	
	//Workload tabs
	@FindBy(xpath="//div[contains(text(),'Findings and Recommendations')]") WebElement tab_FindNRecommend;
	@FindBy(xpath="//div[text()='Review']") WebElement tab_Review;
	@FindBy(xpath="//div[contains(text(),'Milestones')]") WebElement tab_Milestones;
	@FindBy(xpath="//div[text()='Properties']") WebElement tab_Properties;
	
	
	//Recommendation links in Findings and Recommendations tab
	@FindBy(xpath="//mat-panel-title/span[contains(text(),'6Rs')]") WebElement sec_6R;
	@FindBy(xpath="//span[contains(text(),'6Rs')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_6R;
	@FindBy(xpath="//mat-panel-title/span[contains(text(),'Cloud Hosting')]") WebElement sec_CloudHosting;
	@FindBy(xpath="//span[contains(text(),'Cloud Hosting')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_CloudHosting;
	@FindBy(xpath="//mat-panel-title/span[contains(text(),'Cloud Migration Complexity')]") WebElement sec_CloudMigratComplex;
	@FindBy(xpath="//span[contains(text(),'Cloud Migration Complexity')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_CloudMigratComplex;
	@FindBy(xpath="//mat-panel-title/span[contains(text(),'Cloud Model')]") WebElement sec_CloudModel;
	@FindBy(xpath="//span[contains(text(),'Cloud Model')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_CloudModel;
	@FindBy(xpath="//mat-panel-title/span[contains(text(),'Containerization Fitment')]") WebElement sec_ContFitment;
	@FindBy(xpath="//span[contains(text(),'Containerization Fitment')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_ContFitment;
	@FindBy(xpath="//mat-panel-title/span[contains(text(),'Landing Zone Definition')]") WebElement sec_LandZoneDef;
	@FindBy(xpath="//span[contains(text(),'Landing Zone Definition')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul//child::li") List<WebElement> rec_LandZoneDef;
	@FindBy(xpath="//mat-panel-title/span[contains(text(),'SecDevOps Maturity')]") WebElement sec_SecDevOps;
	@FindBy(xpath="//span[contains(text(),'SecDevOps Maturity')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_SecDevOps;
	@FindBy(xpath="//mat-panel-title/span[contains(text(),'Serverless Fitment')]") WebElement sec_SerLessFitment;
	@FindBy(xpath="//span[contains(text(),'Serverless Fitment')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_SerLessFitment;
	@FindBy(xpath="//mat-panel-title/span[contains(text(),'TCO Assessment')]") WebElement sec_TCOAssess;
	@FindBy(xpath="//span[contains(text(),'TCO Assessment')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_TCOAssess;
	@FindBy(xpath="//div/ul/li[text()='Cost']") WebElement sec_Cost;
	@FindBy(xpath="//span[contains(text(),'Cost')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_Cost;
	@FindBy(xpath="//div/ul/li[text()='Operational Excellence']") WebElement sec_OpeExc;
	@FindBy(xpath="//span[contains(text(),'Operational Excellence')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_OpeExc;
	@FindBy(xpath="//div/ul/li[text()='Performance']") WebElement sec_Perf;
	@FindBy(xpath="//span[contains(text(),'Performance')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_Perf;
	@FindBy(xpath="//div/ul/li[text()='Reliability']") WebElement sec_Rel;
	@FindBy(xpath="//span[contains(text(),'Reliability')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_Rel;
	@FindBy(xpath="//div/ul/li[text()='Security']") WebElement sec_Secu;
	@FindBy(xpath="//span[contains(text(),'Security')]//parent::mat-panel-title//parent::span[1]//parent::mat-expansion-panel-header[1]//following-sibling::div/div/div/ul/li") WebElement rec_Secu;
	
	//Miscellaneous
	
	
	
	public ApplicationWorkLoad(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);

    }	
	
	

	public void checkReviewTabBeforeReview() {
		Report.log("Checking the Review Tab before starting the review", Status.BUSINESSSTEP);
		if("Application".equals(reviewType.getText().trim())) {
			Report.log("Review type of the Workload is Application", Status.PASS);
		}
		if(!"Application Modernization - 62".equals(pillar_appMod.getText().trim())) {
			Report.log("Default pillar and number of questions is not correct", Status.FAIL);
		}
		else if(!"Cost - 9".equals(pillar_Cost.getText().trim())) {
			Report.log("Default pillar and number of questions is not correct", Status.FAIL);
		}
		else if(!"Performance - 6".equals(pillar_Perf.getText().trim())) {
			Report.log("Default pillar and number of questions is not correct", Status.FAIL);
		}
		else if(!"Security - 6".equals(pillar_Sec.getText().trim())) {
			Report.log("Default pillar and number of questions is not correct", Status.FAIL);	
		}
		else if(!"Reliability - 6".equals(pillar_Rel.getText().trim())) {
			Report.log("Default pillar and number of questions is not correct", Status.FAIL);
		}
		else if(!"Operational Excellence - 15".equals(pillar_OpEx.getText().trim())) {
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
		new ApplicationQues(driver).answerQues(dp);
		
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
			try {
			if(sec_6R!=null) {
			String Rs=rec_6R.getText().trim();
			System.out.println(Rs);
			if(Recommendations.contains(Rs)) {
				Report.log("Recommendations for 6R's is as expected", Status.PASS);				
			}else{
				Report.log("Recommendations for 6R's is as expected", Status.FAIL);		
			}
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(sec_CloudHosting!=null) {
			sec_CloudHosting.click();
			Thread.sleep(3000);
			
			String clodHost=rec_CloudHosting.getText().trim();
			System.out.println(clodHost);
			if(Recommendations.contains(clodHost)) {
				Report.log("Recommendations for Cloud Hosting is as expected", Status.PASS);				
			}else{
				Report.log("Recommendations for Cloud Hosting is as expected", Status.FAIL);		
			}
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(sec_CloudMigratComplex!=null) {
			sec_CloudMigratComplex.click();		
			Thread.sleep(3000);
			String clMigratComplex=rec_CloudMigratComplex.getText().trim();
			System.out.println(clMigratComplex);
			if(Recommendations.contains(clMigratComplex)) {
				Report.log("Recommendations for Cloud Migration Complexity is as expected", Status.PASS);				
			}else{
				Report.log("Recommendations for Cloud Migration Complexity is as expected", Status.FAIL);		
			}
			}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			try {
			if(sec_CloudModel!=null) {
			sec_CloudModel.click();		
			Thread.sleep(3000);
			String clMdl=rec_CloudModel.getText().trim();
			System.out.println(clMdl);
			if(Recommendations.contains(clMdl)) {
				Report.log("Recommendations for Cloud Model is as expected", Status.PASS);								
			}else{
				Report.log("Recommendations for Cloud Model is as expected", Status.FAIL);		
			}
			}}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(sec_ContFitment!=null) {
			sec_ContFitment.click();		
			Thread.sleep(3000);
			String conFit=rec_ContFitment.getText().trim();
			System.out.println(conFit);
			if(Recommendations.contains(conFit)) {
				Report.log("Recommendations for Containerization Fitment is as expected", Status.PASS);								
			}else{
				Report.log("Recommendations for Containerization Fitment is as expected", Status.FAIL);		
			}
			}}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(sec_LandZoneDef!=null) {
			sec_LandZoneDef.click();		
			Thread.sleep(3000);
			boolean isLandZoneRec;
			List<WebElement> items=rec_LandZoneDef;
			for (WebElement recom : items) {
				isLandZoneRec=false;
				String landZone=recom.getText().trim();					
				if(Recommendations.contains(landZone) && Recommendations!=null) {
					isLandZoneRec=true;								
				}
				if(isLandZoneRec=false) break;
			}
				if(isLandZoneRec=true) {
					Report.log("Recommendations for Landing Zone Definition is as expected", Status.PASS);						
				}else{
					Report.log("Recommendations for Landing Zone Definition is as expected", Status.FAIL);		
				}
			
			}}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(sec_SecDevOps!=null) {
			sec_SecDevOps.click();		
			Thread.sleep(3000);
			String devOops=rec_SecDevOps.getText().trim();
			System.out.println(devOops);
			if(Recommendations.contains(devOops)) {
				Report.log("Recommendations for SecDevOps Maturity is as expected", Status.PASS);	
			}else{
				Report.log("Recommendations for SecDevOps Maturity is as expected", Status.FAIL);		
			}
			}}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			try {
			if(sec_SerLessFitment!=null) {
			sec_SerLessFitment.click();		
			Thread.sleep(3000);
			String serFit=rec_SerLessFitment.getText().trim();
			System.out.println(serFit);
			if(Recommendations.contains(serFit)) {
				Report.log("Recommendations for Serverless Fitment is as expected", Status.PASS);	
			}else{
				Report.log("Recommendations for Serverless Fitment is as expected", Status.FAIL);		
			}
			}}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			try {
			if(sec_TCOAssess!=null) {
			sec_TCOAssess.click();		
			Thread.sleep(3000);
			String tco=rec_TCOAssess.getText().trim();
			System.out.println(tco);
			if(Recommendations.contains(tco)) {
				Report.log("Recommendations for TCO Assessment is as expected", Status.PASS);	
			}else{
				Report.log("Recommendations for TCO Assessment is as expected", Status.FAIL);		
			}
			}}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(sec_Cost!=null) {
			sec_Cost.click();		
			Thread.sleep(3000);
			String cost=rec_Cost.getText().trim();
			System.out.println(cost);
			if(Recommendations.contains(cost)) {
				Report.log("Recommendations for Cost Optimization is as expected", Status.PASS);	
			}else{
				Report.log("Recommendations for Cost Optimization is as expected", Status.FAIL);		
			}
			}}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(sec_OpeExc!=null) {
			sec_OpeExc.click();		
			Thread.sleep(3000);
			String opeExc=rec_OpeExc.getText().trim();
			System.out.println(opeExc);
			if(Recommendations.contains(opeExc)) {
				Report.log("Recommendations for Operational Efficiency is as expected", Status.PASS);	
			}else{
				Report.log("Recommendations for Operational Efficiency is as expected", Status.FAIL);		
			}
			}	}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(sec_Perf!=null) {
			sec_Perf.click();		
			Thread.sleep(3000);
			String perf=rec_Perf.getText().trim();
			System.out.println(perf);
			if(Recommendations.contains(perf)) {
				Report.log("Recommendations for Performance is as expected", Status.PASS);	
			}else{
				Report.log("Recommendations for Performance is as expected", Status.FAIL);		
			}
			}}catch(Exception e){
				e.printStackTrace();
			}
			
			
			try {
			if(sec_Rel!=null) {
			sec_Rel.click();		
			Thread.sleep(3000);
			String rel=rec_Rel.getText().trim();
			System.out.println(rel);
			if(Recommendations.contains(rel)) {
				Report.log("Recommendations for Reliability is as expected", Status.PASS);	
			}else{
				Report.log("Recommendations for Reliability is as expected", Status.FAIL);		
			}
			}}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(sec_Secu!=null) {
			sec_Secu.click();		
			Thread.sleep(3000);
			String sec=rec_Secu.getText().trim();
			System.out.println(sec);
			if(Recommendations.contains(sec)) {
				Report.log("Recommendations for Security is as expected", Status.PASS);	
			}else{
				Report.log("Recommendations for Security is as expected", Status.FAIL);		
			}
			}}catch(Exception e) {
				e.printStackTrace();
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
			
			if(pillar_AppMod_QuesAns.getText().equals("62")) {
				Report.log("All questions of Application Modernization are answered", Status.PASS);	
			}else{
				Report.log("All questions of Application Modernization are answered", Status.FAIL);		
			}
			
			if(pillar_Cost_QuesAns.getText().equals("9")) {
				Report.log("All questions of Cost are answered", Status.PASS);	
			}else{
				Report.log("All questions of Cost are answered", Status.FAIL);		
			}
			
			if(pillar_Perf_QuesAns.getText().equals("6")) {
				Report.log("All questions of Performance are answered", Status.PASS);	
			}else{
				Report.log("All questions of Performance are answered", Status.FAIL);		
			}
			
			if(pillar_Sec_QuesAns.getText().equals("6")) {
				Report.log("All questions of Security are answered", Status.PASS);	
			}else{
				Report.log("All questions of Security are answered", Status.FAIL);		
			}
			
			if(pillar_Rel_QuesAns.getText().equals("6")) {
				Report.log("All questions of Reliability are answered", Status.PASS);	
			}else{
				Report.log("All questions of Reliability are answered", Status.FAIL);		
			}
			
			if(pillar_OpEx_QuesAns.getText().equals("15")) {
				Report.log("All questions of Operational Excellence are answered", Status.PASS);	
			}else{
				Report.log("All questions of Operational Excellence are answered", Status.FAIL);		
			}
			}catch(Exception e) {
				Report.log(e.getMessage(), Status.FAIL);
			}
		}
	
		
	
	
}
