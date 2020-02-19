package unav.cloudNative.azure;

import org.testng.annotations.Test;

import helpers.Description;
import helpers.TestCase;


@Description("Azure workload End-to-End flow")
public class TS_Azure_07 extends TestCase{

@Test
	
	public void test() throws Exception{
		
		login.login();		
		dashbrd.createCloudNativeWL();			
		defCldNatWL.enterCloudNativeWLDetails(dp);			
		azureWL.checkReviewTabBeforeReview(dp);
		azureWL.startReview(dp);
		azureWL.checkReviewTabAfterReview(dp);
		azureWL.checkRiskAndWA(dp);
		wl.generatePDFReport(dp);
		wl.deleteWorkload();
		dashbrd.logout();
		
	}
}
