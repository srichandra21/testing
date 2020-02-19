package unav.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helpers.DataProvider;
import reports.Report;
import reports.Status;

public class PortFolioQues {
	WebDriver driver;
	
	public PortFolioQues(WebDriver driver){
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }	
	
	@FindBy(id="mat-slide-toggle-1") WebElement tgl_QuesNotAppli;
	@FindBy(xpath="//div/mat-radio-group/div[1]/mat-radio-button/label/div[1]/div[1]") WebElement opt_First;
	@FindBy(xpath="//div/mat-radio-group/div[2]/mat-radio-button/label/div[1]/div[1]") WebElement opt_Second;
	@FindBy(xpath="//div/mat-radio-group/div[3]/mat-radio-button/label/div[1]/div[1]") WebElement opt_Third;
	@FindBy(xpath="//button/span[contains(text(),'Next')]") WebElement btn_Next;
	@FindBy(xpath="//button/span[contains(text(),'Save and Exit')]") WebElement btn_SaveNExit;
	@FindBy(xpath="//button/span[contains(text(),'Previous')]") WebElement btn_Prvious;
	@FindBy(xpath="//mat-checkbox/label/div") WebElement chk_Flg4Review;
	@FindBy(xpath="//div/mat-radio-group/div[1]/div") WebElement opt_FirstText;
	@FindBy(xpath="//div/mat-radio-group/div[2]/div") WebElement opt_SecondText;
	@FindBy(xpath="//div/mat-radio-group/div[3]/div") WebElement opt_ThirdText;
	
	
	public void answerQues(DataProvider dp) {
		try {
		String quesOpt;
		for (int i = 1; i <=42; i++) {
			if(i>=1 && i<=9) {
				quesOpt=dp.get("Prt_".concat("0"+i)).trim();
			}else {
				quesOpt=dp.get("Prt_"+i).trim();
			}
			if(quesOpt.equals("2.0")||quesOpt.equals("1.0")) {
				System.out.println(quesOpt);
				String quesOpt1=quesOpt.substring(0,1);
				quesOpt=quesOpt1;
				System.out.println(quesOpt);
			}
			System.out.println("xcel option for "+i+" :"+quesOpt);
			System.out.println("appli option1:"+opt_FirstText.getText().replace(",", ""));
			System.out.println("appli option2:"+opt_SecondText.getText().replace(",", ""));
			System.out.println("appli option3:"+opt_ThirdText.getText().replace(",", ""));
			if(quesOpt.equalsIgnoreCase(opt_FirstText.getText().replace(",", ""))) {
				opt_First.click();
				Report.log(opt_FirstText.getText()+" is selected for ques Prt_"+i, Status.DONE);
			}
			if(quesOpt.equalsIgnoreCase(opt_SecondText.getText().replace(",", ""))) {
				opt_Second.click();
				Report.log(opt_SecondText.getText()+" is selected for ques Prt_"+i, Status.DONE);
			}
			if(quesOpt.equalsIgnoreCase(opt_ThirdText.getText().replace(",", ""))) {
				opt_Third.click();
				Report.log(opt_ThirdText.getText()+" is selected for ques Prt_"+i, Status.DONE);
			}			
			if(i!=42) {
				btn_Next.click();
			}else {
				btn_SaveNExit.click();
				Report.log("Save and Exit is clicked", Status.DONE);
			}	
			
			Thread.sleep(3000);	
	}
		Report.log("Questions answered Successfully", Status.PASS);
		}catch(Exception e) {
		Report.log(e.getMessage(), Status.FAIL);
		Report.log("Questions answered Successfully", Status.FAIL);
	}
	
	}
}
