package helpers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
//import org.ini4j.Profile.Section;
//import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

//import org.testng.annotations.AfterMethod;
/*import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;*/

//import com.opera.core.systems.OperaDriver;
//import cts.qea.automation.annotations.*;
import reports.*;
import reports.ReportTemplate.Caller;
import unav.pages.*;
import helpers.DataProvider;

public abstract class TestCase {

	protected WebDriver driver;
	protected DataProvider dp = null;
	static volatile LinkedHashMap<String, Queue<String>> tcIterator = new LinkedHashMap<String, Queue<String>>();
	public volatile String iteration = "1", tcStatus = "fail", resultPath = "";
	public volatile int day = 1;
	
	 protected Login login; 
	 protected Dashbboard dashbrd;
	 protected DefineAppModWL defAddModWl; protected CloudNative cldNative;
	 protected ApplicationWorkLoad appWL;
	 protected WorkLoad wl;
	 protected PortFolioWorkload portWL ;
	 protected DefineCloudNativeWL defCldNatWL;
	 protected AzureWorkload azureWL;
	 protected AWSWorkload awsWL;
	 
	 
	 
	// public boolean isStandAloneTest = false;

	@BeforeSuite
	public void beforeSuite() {
		Report.getReportForThread().startSuite(Caller.SUITE);
		
		  
		 
	}

	@BeforeMethod
	public void setup() throws Exception {

		String browser = Util.getValue("browser");

		/*
		 * if (browser.equalsIgnoreCase("ie")) {
		 * System.setProperty("webdriver.ie.driver",
		 * "C:\\Users\\Ramachas\\IEDriverServer.exe"); driver = new
		 * InternetExplorerDriver();
		 * 
		 * }
		 */

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ramachas\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		Report.startTest(this, driver);
		Report.addDetailsToFooter("Environment", Util.getValue("region"));

//		Initializing Data provider
		dp = new DataProvider();
		
		login=new Login(driver); 
		dashbrd=new Dashbboard(driver);
		defAddModWl=new DefineAppModWL(driver); 
		cldNative=new CloudNative(driver);
		appWL=new ApplicationWorkLoad(driver);
		wl=new WorkLoad(driver);
		portWL=new PortFolioWorkload(driver);
		defCldNatWL=new DefineCloudNativeWL(driver);
		azureWL=new AzureWorkload(driver);
		awsWL=new AWSWorkload(driver);
		
//		If last day sec is empty or null load the excel data
		  String fileName=null;
		  System.out.println("testcase name:"+this.getAttribute("Name"));
		  if(this.getAttribute("Name").contains("Appli")) {
			  fileName = Util.getValue("AppDataSheet");
		  }else if(this.getAttribute("Name").contains("Port")) {
			  fileName = Util.getValue("PortFolioDataSheet");
		  }else if(this.getAttribute("Name").contains("Azure")) {
			  fileName = Util.getValue("AzureDataSheet");
		  }else if(this.getAttribute("Name").contains("AWS")) {
			  fileName = Util.getValue("AWSDataSheet");
		  }
		  
		System.out.println("Datasheet is:"+fileName);
		String sheetName = Util.getValue("dataSheetName");
		dp.loadFromExcel(fileName, sheetName, this.getAttribute("Name"));
		// dp.get(group, key);

	}

	@AfterMethod
	public void tearDownMethod() {

		try {
//			Quit the driver
			driver.manage().deleteAllCookies();
			driver.close();
			driver.quit();
	
		} catch (Exception e) {
			Report.log("Exception Occured while ending the test case due to :" + e, Status.FAIL);
		}
//		End the report
		Report.endTest(this);

	}

	@AfterSuite
	public void afterSuite() {
		Report.getReportForThread().endSuite(Caller.SUITE);
	}

	@Test
	public abstract void test() throws Exception;

	public String getAttribute(String name) {

		if (name.equalsIgnoreCase("Name")) {
			return this.getClass().getSimpleName();

		} else if (name.equalsIgnoreCase("Description")) {
			try {
				String desc=this.getClass().getAnnotation(Description.class).value();
				System.out.println("Description of Testcase:"+desc);
				return desc;
			} catch (Exception e) {
				System.out.println("error for desc:"+e.getMessage());
				return "No description available";
			}

		} else if (name.equalsIgnoreCase("Iteration")) {
			return this.iteration;

		} else if (name.equalsIgnoreCase("day")) {
			return Integer.toString(this.day);

		} else if (name.equalsIgnoreCase("resultPath")) {
			return this.resultPath;

		}

		return null;
	}

	public void setAttribute(String attributeName, String attributeValue) {

		if (attributeName.equalsIgnoreCase("tcStatus")) {
			tcStatus = attributeValue;
		} else if (attributeName.equalsIgnoreCase("resultPath")) {
			resultPath = attributeValue;
		} else if (attributeName.equalsIgnoreCase("day")) {
			if (attributeValue == null || attributeValue.equals("")) {
				day = 1;
			} else {
				day = Integer.parseInt(attributeValue);
			}
		}
	}

}