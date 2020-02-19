package unav.appModernization.portfolio;

import org.testng.annotations.Test;

import helpers.Description;
import helpers.TestCase;

@Description("PortFolio workload End-to-End flow")
public class TS_PortFolio_09 extends TestCase {

	@Test
	
	public void test() throws Exception{
		
		login.login();		
		dashbrd.createAppWL();			
		defAddModWl.enterPortWLDetails(dp);		
		portWL.checkReviewTabBeforeReview();
		portWL.startReview(dp);
		portWL.checkReviewTabAfterReview();
		portWL.checkRecommendation(dp);
		portWL.goToReview();
		wl.generatePDFReport(dp);
		wl.generateWordReport(dp);
		wl.deleteWorkload();
		dashbrd.logout();
		
	}
}
