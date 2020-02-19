package reports;

import java.io.*;
import java.util.HashMap;
//import org.apache.commons.lang3.StringEscapeUtils;
import helpers.*;

public class HTMLReport implements ReportTemplate {
	String reportTittle="Automation Regression Report",reportHeaderTittle=null;
	String resultsPath=null,screenShotPath=null,testCasePath=null;
	String suiteStartTime,suiteEndTime;
	int totalPassCounter=0,totalFailCounter=0,totalWarningsCounter=0;
	String testCaseName=null,testCaseDesc=null,day=null;
	int startTestCase;
	int screenShotNum,sNum,businesSNum,testCasePassCntr,testCaseFailCntr,testCaseWarningsCounter;
	long testCaseStartTime,testCaseEndTime;
	PrintStream p=null;
	HashMap<String,String> footerAttributes=new HashMap<String, String>();

	public void startSuite(Caller caller, String root) {
		resultsPath=root+"/HTML";
		suiteStartTime=Util.getCurrentDatenTime("MM-dd-yyyy")+"\t"+Util.getCurrentDatenTime("hh-mm-ss_a");

		if(!new File(resultsPath).isDirectory())
			new File(resultsPath).mkdirs();
		String stamp=Util.getCurrentDatenTime("HHmmss");
		String fileName="SummaryReport";

		if(caller.equals(Caller.TEST)){
			fileName=Thread.currentThread().getName(); 
			System.out.println(fileName);
		}

		try {
			p= new PrintStream(new FileOutputStream(resultsPath+"/"+fileName+"_"+stamp+".html"));
		} catch (FileNotFoundException e) {
		}
		try {
		if(reportHeaderTittle==null){ 
			reportHeaderTittle=Util.getValue("tittle");
			reportTittle=reportHeaderTittle;
		}
		}catch(Exception e) {}
		String header= "";
		header="<html>\n<head>\n\t" +
		"<script>\n\tfunction hideshow(testID) {\n\t\t" +
		"var tcName=document.getElementById('tsname'+testID);\n\t\tvar stepsTable=document.getElementById('tst'+testID);\n\t\tvar tcDesc=document.getElementById('tsdesc'+testID);\n\t\tvar eleIndicator = document.getElementById('exp'+testID);\n\t\tvar testcasestatus=document.getElementById('tststatus'+testID);\n\t\tvar testcaseday=document.getElementById('tstday'+testID);\n\n\t\t" +
		"if(stepsTable==null || eleIndicator==null){\n\t\t\talert('Script Error');\n\t\t\treturn;\n\t\t}\n\n\t\t" +
		"if(stepsTable.className=='stepstbl_vis') {\n\t\t\ttcName.className = 'tcname';\n\t\t\tstepsTable.className = 'stepstbl_notvis';\n\t\t\ttcDesc.className = 'tcdesc';\n\t\t\teleIndicator.innerHTML='[+]';\n\t\t}else{\n\t\t\ttcName.className = 'tcname_selected';\n\t\t\tstepsTable.className = 'stepstbl_vis';\n\t\t\ttcDesc.className = 'tcdesc_selected';\n\t\t\teleIndicator.innerHTML='[-]';\n\t\t}\n\t}\n\t" +
		"function setInitial() {\n\t\tvar testID = 1;\n\t\twhile(true){\n\t\t\tvar testcaseday=document.getElementById('tstday'+testID);\n\t\t\tvar day=document.getElementById('day'+testID);\n\t\t\tvar eleStatus=document.getElementById('st'+testID);\n\t\t\tvar eleIndicator = document.getElementById('exp'+testID);\n\t\t\tvar testcasestatus=document.getElementById('tststatus'+testID);\n\t\t\tif(eleStatus==null || eleIndicator==null) break;\n\t\t\t//set values to pass/fail/warning\n\t\t\tif(day.innerText==null){\n\t\t\t\ttestcaseday.innerHTML= day.textContent;\n\t\t\t}else if(day.textContent==null) {\n\t\t\t\ttestcaseday.innerHTML=day.innerText;\n\t\t\t}\n\t\t\tif(eleStatus.innerText=='Pass' || eleStatus.textContent=='Pass'){\n\t\t\t\teleIndicator.style.color = 'green';\n\t\t\t\ttestcasestatus.innerHTML='Pass';\n\t\t\t\ttestcasestatus.style.color = 'green';\n\t\t\t}else if(eleStatus.innerText=='Warning' || eleStatus.textContent=='Warning'){\n\t\t\t\teleIndicator.style.color = '#FF9900';\n\t\t\t\ttestcasestatus.innerHTML='Warning';\n\t\t\t\ttestcasestatus.style.color = '#FF9900';\n\t\t\t}else{\n\t\t\t\teleIndicator.style.color = 'red';\n\t\t\t\ttestcasestatus.innerHTML='Fail';\n\t\t\t\ttestcasestatus.style.color = 'red';\n\t\t\t}\n\t\t\t//alert(testID + eleStatus.innerText);\n\t\t\ttestID++;\n\t\t}\n\t}\n\t" +
		"</script>\n\t"+
		"<style>\n\t\t" +
		"body,p,h1,h2,h3,h4,table,td,th,ul,ol,textarea,input{background-color:#FFFFFF;font-family:verdana,helvetica,arial,sans-serif;font-size:12px;}\n\t\t"+
		"table{border:1px solid sandybrown;border-collapse:collapse;background-color:#B3D9FF;}\n\t\t" +
		"td.headerrow1{text-align:center;background-color:sienna;color:white;font-weight:bold;font-size:14px;}\n\t\t" +
		"td{border:0px;padding:3px;}\n\t\t" +
		"td.tcname_selected{background-color:peru;color:black;font-size:14px;}\n\t\t" +
		"td.tcdesc_selected{background-color:peru;color:black;font-size:12px;}\n\t\t" +
		"td.tcname{background-color:moccasin;font-size:14px;}\n\t\t" +
		"td.tcdesc{background-color:moccasin;font-size:12px;}\n\t\t" +
		"td.collapsebutton{text-align:center;}\n\t\t" +
		"td.tcstatus{text-align:center;color:#8A4117;}\n\t\t" +
		"tr td.tccontainer{padding:0px;border:1px solid sandybrown;}\n\t\t" +
		"tr.stepstbl_vis{}\n\t\t" +
		"tr.stepstbl_notvis{display:none;}\n\t\t" +
		"table.stepstbl {border:none;}\n\t\t" +
		"tr.step_head td{text-align:center;color:black;background-color:tan;font-weight:bold;}\n\t\t" +
		"td.step_reg{text-align:left;background-color:White;}\n\t\t" +
		"tr.business_step td{background-color:AntiqueWhite;color:black;font-weight:bold;}\n\t\t" +
		"tr.statuspass td,td.statuspass{color:green;border 1px solid wheat;}\n\t\t" +
		"a.passlink,font.statuspass{color:green;}\n\t\t" +
		"tr.statusfail td,td.statusfail{color:red;}\n\t\t" +
		"a.faillink,font.statusfail{color:red;}\n\t\t" +
		"tr.statuswarn td,td.statuswarn{color:orange;}\n\t\t" +
		"a.warnlink,font.statuswarn{color:orange;}\n\t\t" +
		"tr.statusdone td,td.statusdone{color:grey;}\n\t\t" +
		"tr.tcfooter td{background-color:white;color:black;font-weight:bold;border:1px solid sandybrown;}\n\t\t" +
		"td.stepno{text-align:left;background-color:White;padding-left:15px;}\n\t\t" +
		"td.summarycolumnvalue{text-align:center;color:black;font-weight:bold;}\n\t\t" +
		"td.summarypasscolumn{text-align:center;color:green;font-weight:bold;}\n\t\t" +
		"td.summaryfailcolumn{text-align:center;color:red;font-weight:bold;}\n\t\t" +
		"td.summarywarningcolumn{text-align:center;color:orange;font-weight:bold;}\n\t\t" +
		"span.hidden{display:none;}\n\t" +
		"</style>\n\t" +
		"<title>" +
		reportTittle +
		"</title>\n" +
		"</head>\n"+
		"<body onload=\"setInitial()\">\n" +
		"<table width=\"900px\" align=\"center\">\n\t" +
		"<tr>\n\t\t" +
		"<td class=\"headerrow1\" colspan=\"3\">"+reportHeaderTittle+"</td>\n\t" +
		"</tr>\n\t";
		startTestCase=0;

		p.println (header);
		p.flush();
	}


	public void endSuite(Caller caller) {
		suiteEndTime=Util.getCurrentDatenTime("MM-dd-yyyy")+"\t"+Util.getCurrentDatenTime("hh-mm-ss_a");
		String updateSummary="";
		updateSummary="</table>\n" +
		"</br></br>\n" +
		"<table width=\"500px\" align=\"center\">\n\t" +
		"<tr>\n\t\t" +
		"<td class=\"headerrow1\" colspan=\"2\"> Test Execution Summary</td>\n\t" +
		"</tr>\n\t" +
		"<tr>\n\t\t" +
		"<td class=\"summarycolumn\">Environment</td>\n\t\t" +
		"<td class=\"summarycolumnvalue\">"+footerAttributes.get("Environment")+"</td>\n\t" +
		"</tr>\n\t" +
		"<tr>\n\t\t" +
		"<td class=\"summarycolumn\">Test Cases Executed</td>\n\t\t" +
		"<td class=\"summarycolumnvalue\">"+(totalPassCounter+totalFailCounter+totalWarningsCounter)+"</td>\n\t" +
		"</tr>\n\t" +
		"<tr>\n\t\t" +
		"<td class=\"summarycolumn\">Total Pass Count</td>\n\t\t" +
		"<td class=\"summarypasscolumn\">"+totalPassCounter+"</td>\n\t" +
		"</tr>\n\t" +
		"<tr>\n\t\t" +
		"<td class=\"summarycolumn\">Total Fail Count</td>\n\t\t" +
		"<td class=\"summaryfailcolumn\">"+totalFailCounter+"</td>\n\t" +
		"</tr>\n\t" +
		"<tr>\n\t\t" +
		"<td class=\"summarycolumn\">Total Warning Count</td>\n\t\t" +
		"<td class=\"summarywarningcolumn\">"+totalWarningsCounter+"</td>\n\t" +
		"</tr>\n\t" +
		"<tr>\n\t\t" +
		"<td class=\"summarycolumn\">Start Time</td>\n\t\t" +
		"<td class=\"summarycolumnvalue\">"+suiteStartTime+"</td>\n\t" +
		"</tr>\n\t" +
		"<tr>\n\t\t" +
		"<td class=\"summarycolumn\">End Time</td>\n\t\t" +
		"<td class=\"summarycolumnvalue\">"+suiteEndTime+"</td>\n\t" +
		"</tr>\n" +
		"</table>\n" +
		"</body></html>";
		p.println (updateSummary);
		p.close();
	}

	
	public void startTest(TestCase tc) {
		testCaseStartTime=Util.getLastsetTimeinmili();
		++startTestCase;
		testCaseName=tc.getAttribute("name");
		testCaseDesc=tc.getAttribute("description");
		//tcIteration=tc.getAttribute("Iteration");
		//day="[Day: <span id=\"tstday"+startTestCase+"\" > 1 </span>]";
		tc.setAttribute("resultPath",resultsPath);
		screenShotNum= 1;
		sNum= 0;
		businesSNum= 1;
		testCasePassCntr=0;
		testCaseFailCntr=0;
		testCaseWarningsCounter=0;
		screenShotPath= testCaseName+"_"+Util.getCurrentDatenTime("HHmmss");

		if(!new File(resultsPath+"/"+screenShotPath).isDirectory())
			new File(resultsPath+"/"+screenShotPath).mkdirs();

		String testCaseheader="";
		testCaseheader=		"\t<tr>\n\t\t" +
		"<td width=\"45px\" class=\"collapsebutton\" rowspan=\"2\"><span id=\"exp"+startTestCase+"\" onClick=\"hideshow('"+startTestCase+"')\">[+]</span></td>\n\t\t" +
		"<td width=\"775px\" class=\"tcname\" id=\"tsname"+startTestCase+"\"><TestName>"+startTestCase+". "+testCaseName+"</TestName></td>\n\t\t" +
		"<td width=\"80px\" class=\"tcstatus\" rowspan=\"2\"><span id=\"tststatus"+startTestCase+"\" >TBD</span></td>\n\t" +
		"</tr>\n\t" +
		"<tr>\n\t\t" +
		"<td class=\"tcdesc\" id=\"tsdesc"+startTestCase+"\"><TestDescription>"+testCaseDesc+"</TestDescription></td>\n\t" +
		"</tr>\n\t" +
		"<tr class=\"stepstbl_notvis\" id=\"tst"+startTestCase+"\"><td class=\"tccontainer\" colspan=\"3\">\n\t\t" +
		"<table class=\"stepstbl\" width=\"100%\">\n\t\t\t" +
		"<tr class=\"step_head\">\n\t\t\t\t" +
		"<td width=\"45px\">S No</td>\n\t\t\t\t" +
		"<td width=\"700px\">Step Description</td>\n\t\t\t\t" +
		"<td width=\"75px\">Status</td>\n\t\t\t\t" +
		"<td width=\"80px\">Time</td>\n\t\t\t" +
		"</tr>\n";
		p.println (testCaseheader);
		p.flush();
	}

	
	public void endTest(TestCase tc) {
		testCaseEndTime=Util.getLastsetTimeinmili();
		String tcStatus="Done";
		if(testCaseFailCntr>0){
			tcStatus="Fail";
			++totalFailCounter;
		}else if(testCaseWarningsCounter>0){
			tcStatus="Warning";
			++totalWarningsCounter;
		}else if(testCasePassCntr>0){
			tcStatus="Pass";
			++totalPassCounter;	
		}
		String tcfooter="\t\t\t<tr class=\"tcfooter\">\n\t\t\t" +
		"<td colspan=\"4\" align=\"Center\">\n\t\t\t\t" +
		"PASS:<font class=\"statuspass\">"+testCasePassCntr+"</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n\t\t\t\t" +
		"FAIL:<font class=\"statusfail\">"+testCaseFailCntr+"</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n\t\t\t\t" +
		"Total Duration:"+Util.getFormattedTime(testCaseEndTime-testCaseStartTime)+"\n\t\t\t" +
		"</td>" +
		"</tr>" +
		"</table>" +
		"<span id=\"st"+startTestCase+"\" class=\"hidden\"><TCStatus>"+tcStatus+"</TCStaus></span>" +
		"<span id=\"day"+startTestCase+"\" class=\"hidden\">"+tc.getAttribute("day")+"</span>" +
		"</td></tr>	";
		p.println (tcfooter);
		p.flush();
	}


	public void step(String message, Status status) {
		String row = "";
		boolean takeScreenShot=false;
		//message=StringEscapeUtils.escapeHtml4(message);

		if (status.equals(Status.BUSINESSSTEP)) {
			businesSNum=1;
			sNum++;
			row="\t\t\t<tr class=\"business_step\">\n\t\t\t\t" +
			"<td>"+sNum+"</td>\n\t\t\t\t" +
			"<td colspan=\"3\">"+message+"</td>\n\t\t\t" +
			"</tr>\n";

		} else {
			String cssClass="statusdone",anchorLinkClass="faillink",htmlStatus="DONE";
			switch(status){
			case Pass: cssClass="statuspass"; takeScreenShot= false; htmlStatus="Pass"; ++testCasePassCntr; break;
			case PASS: cssClass="statuspass"; takeScreenShot= true; anchorLinkClass="passlink"; htmlStatus="Pass"; ++testCasePassCntr; break;
			case FAIL: cssClass="statusfail"; takeScreenShot= true; anchorLinkClass="faillink"; htmlStatus="Fail"; ++testCaseFailCntr; break;
			case Fail: cssClass="statusfail"; takeScreenShot= false; anchorLinkClass="faillink"; htmlStatus="Fail"; ++testCaseFailCntr; break;
			case DONE: cssClass="statusdone"; takeScreenShot= false; htmlStatus="Done"; break;
			case WARN: cssClass="statuswarn"; takeScreenShot= true; anchorLinkClass="warnlink"; htmlStatus="Warning"; ++testCaseWarningsCounter; break;
			}
			if(takeScreenShot){
				String screenShot = screenShotPath+"/Screen"+screenShotNum+".png";
				Report.takeScreenShot(resultsPath+"/"+screenShot);
				row="\t\t\t<tr class=\""+cssClass+"\">\n\t\t\t\t" +
				"<td class=\"stepno\">"+sNum+"."+businesSNum+"</td>\n\t\t\t\t" +
				"<td>"+message+"</td>\n\t\t\t\t" +
				"<td><a class=\""+anchorLinkClass+"\" href='"+screenShot+"' target=\"_blank\">"+htmlStatus+"</a></td>\n\t\t\t\t" +
				"<td>"+Util.getCurrentDatenTime("H:mm:ss") +"</td>\n\t\t\t" +
				"</tr>\n";
				screenShotNum++;
			}else{
				row="\t\t\t<tr class=\""+cssClass+"\">\n\t\t\t\t" +
				"<td class=\"stepno\">"+sNum+"."+businesSNum+"</td>\n\t\t\t\t" +
				"<td>"+message+"</td>\n\t\t\t\t" +
				"<td>"+htmlStatus+"</td>\n\t\t\t\t" +
				"<td>"+Util.getCurrentDatenTime("H:mm:ss") +"</td>\n\t\t\t" +
				"</tr>\n";
			}
			businesSNum++;
		}
		p.println (row);
		p.flush();
	}

	public void addToFooter(String name, String value){
		footerAttributes.put(name, value);
	}

	public void setTittle(String tittle){
		reportHeaderTittle=tittle;
	}

	
	public void addAttachement (String message, String filePath) {
		String row = "";
		businesSNum=1;
		sNum++;
		row="\t\t\t<tr class=\"business_step\">\n\t\t\t\t" +
		"<td>"+sNum+"</td>\n\t\t\t\t" +
		"<td colspan=\"3\">"+message+"</td>\n\t\t\t" +
		"</tr>\n";
		p.println (row);
		p.flush();

		row = "";
		row="\t\t\t<tr class=\"statusdone\">\n\t\t\t\t" +
		"<td class=\"stepno\">"+sNum+"."+businesSNum+"</td>\n\t\t\t\t" +
		"<td>Attachment Link </td>\n\t\t\t\t" +
		"<td colspan=\"2\"><a href='./"+filePath+"' target=\"_blank\">File Link</a></td>\n\t\t\t" +
		"</tr>\n";
		p.println (row);
		p.flush();
		businesSNum++;
	}
}
