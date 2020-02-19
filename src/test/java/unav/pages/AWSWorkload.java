package unav.pages;

import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helpers.DataProvider;
import reports.Report;
import reports.Status;

public class AWSWorkload {
	WebDriver driver;
	protected HashMap<String,String> UAF=new HashMap<String,String>();
	//Pillars and Questions in Review tab before starting the review
	@FindBy(xpath="//div/div/div/div/h6") WebElement cloudServProvider;
	@FindBy(xpath="//div/div/div/div/div/span[1]") WebElement pillar_Sec;
	@FindBy(xpath="//div/div/div/div/div/span[2]") WebElement pillar_Cost;
	@FindBy(xpath="//div/div/div/div/div/span[4]") WebElement pillar_Rel;
	@FindBy(xpath="//div/div/div/div/div/span[5]") WebElement pillar_Perf;
	@FindBy(xpath="//div/div/div/div/div/span[3]") WebElement pillar_Oper;
	@FindBy(xpath="//button/span[contains(text(),'Start Review')]") WebElement btn_SrtRev; 
	
	//Workload tabs
	@FindBy(xpath="//div[contains(text(),'Improvement Plan')]") WebElement tab_FindNRecommend;
	@FindBy(xpath="//div[text()='Review']") WebElement tab_Review;
	@FindBy(xpath="//div[contains(text(),'Milestones')]") WebElement tab_Milestones;
	@FindBy(xpath="//div[text()='Properties']") WebElement tab_Properties;
	
	////Review tab elements after completion of review
	@FindBy(xpath="//div/p[contains(text(),'Overall Status')]//following-sibling::span") WebElement overallStatus; 
	@FindBy(xpath="//a[contains(text(),'Security')]") WebElement pillar_Sec_link; 
	@FindBy(xpath="//a[contains(text(),'Security')]//parent::div[1]//following-sibling::div/span") WebElement pillar_Sec_QuesAns;
	@FindBy(xpath="//a[contains(text(),'Cost')]") WebElement pillar_Cost_link; 
	@FindBy(xpath="//a[contains(text(),'Cost')]//parent::div[1]//following-sibling::div/span") WebElement pillar_Cost_QuesAns;
	@FindBy(xpath="//a[contains(text(),'Performance')]") WebElement pillar_Perf_link; 
	@FindBy(xpath="//a[contains(text(),'Performance')]//parent::div[1]//following-sibling::div/span") WebElement pillar_Perf_QuesAns; 
	@FindBy(xpath="//a[contains(text(),'Reliability')]") WebElement pillar_Rel_link; 
	@FindBy(xpath="//a[contains(text(),'Reliability')]//parent::div[1]//following-sibling::div/span") WebElement pillar_Rel_QuesAns; 
	@FindBy(xpath="//a[contains(text(),'Operations')]") WebElement pillar_Oper_link; 
	@FindBy(xpath="//a[contains(text(),'Operations')]//parent::div[1]//following-sibling::div/span") WebElement pillar_Oper_QuesAns; 
	
	@FindBy(xpath="//div[contains(text(),'Well Architected')]//following-sibling::div[1]/a") WebElement  count_wa;
	@FindBy(xpath="//div[contains(text(),'High Risk')]//following-sibling::div[1]/a") WebElement  count_hr;
	@FindBy(xpath="//div[contains(text(),'Medium Risk')]//following-sibling::div[1]/a") WebElement  count_lr;
	@FindBy(xpath="//div[contains(text(),'Unanswered')]//following-sibling::div[1]/a") WebElement  count_ua;
	@FindBy(xpath="//div/div/a[2]") WebElement  link_wl;

	
	public AWSWorkload(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }	
	
	
	public void checkReviewTabBeforeReview(DataProvider dp) {
		Report.log("Checking the Review Tab before starting the review", Status.BUSINESSSTEP);
		if(dp.get("CloudProvider").equalsIgnoreCase(cloudServProvider.getText().trim())) {
			Report.log("Cloud Service Provider of the Workload is "+dp.get("CloudProvider"), Status.PASS);
		}
		if(!"Security - 11".equals(pillar_Sec.getText().trim())) {
			Report.log("Default pillar and number of questions is not correct", Status.FAIL);
		}
		else if(!"Cost - 9".equals(pillar_Cost.getText().trim())) {
			Report.log("Default pillar and number of questions is not correct", Status.FAIL);
		}
		else if(!"Reliability - 9".equals(pillar_Rel.getText().trim())) {
			Report.log("Default pillar and number of questions is not correct", Status.FAIL);
		}
		else if(!"Performance - 8".equals(pillar_Perf.getText().trim())) {
			Report.log("Default pillar and number of questions is not correct", Status.FAIL);
		}
		else if(!"Operations - 9".equals(pillar_Oper.getText().trim())) {
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
		new AWSQues(driver).answerQues(dp);
		
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}
	}
	
	public void goToReview() {
		tab_Review.click();	
	}
	
	
	public void checkReviewTabAfterReview(DataProvider dp) {
		try {
		Report.log("Checking the Review Tab after the review is complete", Status.BUSINESSSTEP);
		String UA=dp.get("UA value");
		String uaArray[]=UA.split(",");
		for(int i=0;i<uaArray.length;i++) {
			String uaArray1[]=uaArray[i].split(":");
			UAF.put(uaArray1[0], uaArray1[1]);
		}
		int k=0;
		Set<String> pil = UAF.keySet();
		for(String pilIte:pil) {
			k=k+Integer.parseInt(UAF.get(pilIte));
		}		
		
		
		if(k>0) {
			if(overallStatus.getText().equals("Partially Answered")) {
				Report.log("OverAllStatus should be Partially Answered", Status.PASS);	
			}else{
				Report.log("OverAllStatus should be Partially Answered", Status.FAIL);		
			}
		}else {
			if(overallStatus.getText().equals("Answered")) {
				Report.log("OverAllStatus should be Answered", Status.PASS);	
			}else{
				Report.log("OverAllStatus should be Answered", Status.FAIL);		
			}
		}
		int j=Integer.parseInt(UAF.get("Cos"));
		String cos=Integer.toString(9-j);
		if(pillar_Cost_QuesAns.getText().equals(cos)) {
			Report.log("All questions of Cost are answered", Status.PASS);	
		}else{
			Report.log("All questions of Cost are answered", Status.FAIL);		
		}
		j=Integer.parseInt(UAF.get("Perf"));
		String perf=Integer.toString(8-j);
		if(pillar_Perf_QuesAns.getText().equals(perf)) {
			Report.log("All questions of Performance are answered", Status.PASS);	
		}else{
			Report.log("All questions of Performance are answered", Status.FAIL);		
		}
		j=Integer.parseInt(UAF.get("Sec"));
		String sec=Integer.toString(11-j);
		if(pillar_Sec_QuesAns.getText().equals(sec)) {
			Report.log("All questions of Security are answered", Status.PASS);	
		}else{
			Report.log("All questions of Security are answered", Status.FAIL);		
		}
		j=Integer.parseInt(UAF.get("Rel"));
		String rel=Integer.toString(9-j);
		if(pillar_Rel_QuesAns.getText().equals(rel)) {
			Report.log("All questions of Reliability are answered", Status.PASS);	
		}else{
			Report.log("All questions of Reliability are answered", Status.FAIL);		
		}
		j=Integer.parseInt(UAF.get("Ops"));
		String ops=Integer.toString(9-j);
		if(pillar_Oper_QuesAns.getText().equals(ops)) {
			Report.log("All questions of Operations are answered", Status.PASS);	
		}else{
			Report.log("All questions of Operations are answered", Status.FAIL);		
		}
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}
	}

	public void checkRiskAndWA(DataProvider dp) {
		try {
			Report.log("Checking the WA,HR and MR after the review is complete", Status.BUSINESSSTEP);
			HashMap<String,String> WAF=new HashMap<String,String>();
			HashMap<String,String> HRF=new HashMap<String,String>();
			HashMap<String,String> LRF=new HashMap<String,String>();
			
			String WA=dp.get("WA value");
			String HR=dp.get("HR value");
			String LR=dp.get("LR value");
			String waArray[]=WA.split(",");
			String hrArray[]=HR.split(",");
			String lrArray[]=LR.split(",");
			System.out.println("WAF done");
			for(int i=0;i<waArray.length;i++) {
				String waArray1[]=waArray[i].split(":");
				WAF.put(waArray1[0], waArray1[1]);
			}System.out.println("WAF done");
			for(int i=0;i<hrArray.length;i++) {
				String hrArray1[]=hrArray[i].split(":");
				HRF.put(hrArray1[0], hrArray1[1]);
			}System.out.println("HRF done");
			for(int i=0;i<lrArray.length;i++) {
				String lrArray1[]=lrArray[i].split(":");
				LRF.put(lrArray1[0], lrArray1[1]);
			}System.out.println("MRF done");
			
			
			pillar_Sec_link.click();
			Thread.sleep(3000);
			if(count_wa.getText().trim().equals(WAF.get("Sec"))) {
				Report.log("Well Architected count of Security is calculated correctly", Status.PASS);	
			}else{
				Report.log("Well Architected count of Security is calculated correctly", Status.FAIL);		
			}
			
			if(count_hr.getText().trim().equals(HRF.get("Sec"))) {
				Report.log("High Risk count of Security is calculated correctly", Status.PASS);	
			}else{
				Report.log("High Risk count of Security is calculated correctly", Status.FAIL);		
			}
			
			if(count_lr.getText().trim().equals(LRF.get("Sec"))) {
				Report.log("Medium Risk count of Security is calculated correctly", Status.PASS);	
			}else{
				Report.log("Medium Risk count of Security is calculated correctly", Status.FAIL);		
			}			
			if(count_ua.getText().trim().equals(UAF.get("Sec"))) {
				Report.log("Unanswered count of Security is calculated correctly", Status.PASS);	
			}else{
				Report.log("Unanswered count of Security is calculated correctly", Status.FAIL);		
			}
			link_wl.click();
			Thread.sleep(3000);
			
			pillar_Cost_link.click();
			Thread.sleep(3000);
			if(count_wa.getText().trim().equals(WAF.get("Cos"))) {
				Report.log("Well Architected count of Cost is calculated correctly", Status.PASS);	
			}else{
				Report.log("Well Architected count of Cost is calculated correctly", Status.FAIL);		
			}
			
			if(count_hr.getText().trim().equals(HRF.get("Cos"))) {
				Report.log("High Risk count of Cost is calculated correctly", Status.PASS);	
			}else{
				Report.log("High Risk count of Cost is calculated correctly", Status.FAIL);		
			}
			
			if(count_lr.getText().trim().equals(LRF.get("Cos"))) {
				Report.log("Medium Risk count of Cost is calculated correctly", Status.PASS);	
			}else{
				Report.log("Medium Risk count of Cost is calculated correctly", Status.FAIL);		
			}
			if(count_ua.getText().trim().equals(UAF.get("Cos"))) {
				Report.log("Unanswered count of Cost is calculated correctly", Status.PASS);	
			}else{
				Report.log("Unanswered count of Cost is calculated correctly", Status.FAIL);		
			}
			link_wl.click();
			Thread.sleep(3000);
			
			pillar_Perf_link.click();
			Thread.sleep(3000);
			if(count_wa.getText().trim().equals(WAF.get("Perf"))) {
				Report.log("Well Architected count of Performance is calculated correctly", Status.PASS);	
			}else{
				Report.log("Well Architected count of Performance is calculated correctly", Status.FAIL);		
			}
			
			if(count_hr.getText().trim().equals(HRF.get("Perf"))) {
				Report.log("High Risk count of Performance is calculated correctly", Status.PASS);	
			}else{
				Report.log("High Risk count of Performance is calculated correctly", Status.FAIL);		
			}
			
			if(count_lr.getText().trim().equals(LRF.get("Perf"))) {
				Report.log("Medium Risk count of Performance is calculated correctly", Status.PASS);	
			}else{
				Report.log("Medium Risk count of Performance is calculated correctly", Status.FAIL);		
			}
			if(count_ua.getText().trim().equals(UAF.get("Perf"))) {
				Report.log("Unanswered count of Performance is calculated correctly", Status.PASS);	
			}else{
				Report.log("Unanswered count of Performance is calculated correctly", Status.FAIL);		
			}
			link_wl.click();
			Thread.sleep(3000);
			
			pillar_Rel_link.click();
			Thread.sleep(3000);
			if(count_wa.getText().trim().equals(WAF.get("Rel"))) {
				Report.log("Well Architected count of Reliability is calculated correctly", Status.PASS);	
			}else{
				Report.log("Well Architected count of Reliability is calculated correctly", Status.FAIL);		
			}
			
			if(count_hr.getText().trim().equals(HRF.get("Rel"))) {
				Report.log("High Risk count of Reliability is calculated correctly", Status.PASS);	
			}else{
				Report.log("High Risk count of Reliability is calculated correctly", Status.FAIL);		
			}
			
			if(count_lr.getText().trim().equals(LRF.get("Rel"))) {
				Report.log("Medium Risk count of Reliability is calculated correctly", Status.PASS);	
			}else{
				Report.log("Medium Risk count of Reliability is calculated correctly", Status.FAIL);		
			}
			if(count_ua.getText().trim().equals(UAF.get("Rel"))) {
				Report.log("Unanswered count of Reliability is calculated correctly", Status.PASS);	
			}else{
				Report.log("Unanswered count of Reliability is calculated correctly", Status.FAIL);		
			}
			link_wl.click();
			Thread.sleep(3000);
			
			pillar_Oper_link.click();
			Thread.sleep(3000);
			if(count_wa.getText().trim().equals(WAF.get("Ops"))) {
				Report.log("Well Architected count of Operations is calculated correctly", Status.PASS);	
			}else{
				Report.log("Well Architected count of Operations is calculated correctly", Status.FAIL);		
			}
			
			if(count_hr.getText().trim().equals(HRF.get("Ops"))) {
				Report.log("High Risk count of Operations is calculated correctly", Status.PASS);	
			}else{
				Report.log("High Risk count of Operations is calculated correctly", Status.FAIL);		
			}
			
			if(count_lr.getText().trim().equals(LRF.get("Ops"))) {
				Report.log("Medium Risk count of Operations is calculated correctly", Status.PASS);	
			}else{
				Report.log("Medium Risk count of Operations is calculated correctly", Status.FAIL);		
			}
			if(count_ua.getText().trim().equals(UAF.get("Ops"))) {
				Report.log("Unanswered count of Operations is calculated correctly", Status.PASS);	
			}else{
				Report.log("Unanswered count of Operations is calculated correctly", Status.FAIL);		
			}
			link_wl.click();
			Thread.sleep(3000);
			
		}catch(Exception e) {
			Report.log(e.getMessage(), Status.FAIL);
		}
		
	}
	
	
	
	
}
