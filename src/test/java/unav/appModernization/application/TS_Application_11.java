package unav.appModernization.application;


import org.testng.annotations.Test;


import helpers.Description;
import helpers.TestCase;


@Description ("Application workload End-to-End flow")
public class TS_Application_11 extends TestCase{
	
	@Test
	public void test() throws Exception{
		
		login.login();		
		dashbrd.createAppWL();			
		defAddModWl.enterAppWLDetails(dp);		
		appWL.checkReviewTabBeforeReview();
		appWL.startReview(dp);
		appWL.checkReviewTabAfterReview();
		appWL.checkRecommendation(dp);
		appWL.goToReview();
		wl.generatePDFReport(dp);
		wl.generateWordReport(dp);
		wl.deleteWorkload();
		dashbrd.logout();
		
	}
}
