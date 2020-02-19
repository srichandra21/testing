package reports;

import helpers.TestCase;

public interface ReportTemplate {
	
	enum Caller {
		SUITE,
		TEST
	}
	
	public void startSuite(Caller caller, String rootFolder);
	public void endSuite(Caller caller);
	public void startTest(TestCase tc) ;
	public void endTest(TestCase tc) ;
	public void addAttachement(String message, String filepath);
	public void step(String message, Status status);
	public void addToFooter(String name, String value);
	public void setTittle(String tittle);
}
