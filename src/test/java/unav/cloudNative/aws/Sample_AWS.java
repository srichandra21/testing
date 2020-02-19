package unav.cloudNative.aws;

import org.testng.annotations.Test;

import helpers.Description;
import helpers.TestCase;

@Description("AWS workload End-to-End flow")
public class Sample_AWS extends TestCase{

@Test
	
	public void test() throws Exception{
		
		login.login();		
		dashbrd.createCloudNativeWL();			
		defCldNatWL.enterCloudNativeWLDetails(dp);			
		awsWL.checkReviewTabBeforeReview(dp);
		awsWL.startReview(dp);
		awsWL.checkReviewTabAfterReview(dp);
		awsWL.checkRiskAndWA(dp);
		wl.generatePDFReport(dp);
		wl.deleteWorkload();
		dashbrd.logout();
		
	}
}
