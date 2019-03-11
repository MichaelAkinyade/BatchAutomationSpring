package com.globalcapital.pack.schedule.report.process;

import java.io.IOException;
import java.util.TimerTask;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.globalcapital.database.datasource.H2DatabaseLuncher;
import com.globalcapital.pack.bean.ReportExecutionBean;
import com.globalcapital.pack.engine.batchSchedule.BatchOperationCli;
import com.globalcapital.pack.engine.batchSchedule.ReportOperationCli;
import com.globalcapital.pack.schedule.utility.ScheduleAutomationUtility;
import com.globalcapital.pack.schedule.utility.ScheduleConstantClass;
import com.globalcapital.utility.DateUtility;
import com.globalcapital.utility.PropertyFileUtils;

@DisallowConcurrentExecution
public class TermActurialExtractDeact  implements Job {
	ReportExecutionBean reportBean = new ReportExecutionBean();
	ScheduleAutomationUtility scheduler = new ScheduleAutomationUtility("report");

	public void execute(JobExecutionContext context) {
		
		ReportExecutionBean reportBean = new ReportExecutionBean();

		try {

			PropertyFileUtils prop1 = new PropertyFileUtils("reportBatch.properties");
			reportBean.setReportName("TERM_ACTUARIAL_EXTRACT_DEACT");
			reportBean.setReportJobId("termActurialExtractDeact");
			reportBean.setPath(prop1.loadProperties().getProperty("solife.report.console"));
			//reportBean.setUsername((prop1.loadProperties().getProperty("solife.batch.username")));
			//reportBean.setJndiServer(prop1.loadProperties().getProperty("solife.jndi.url"));
			//reportBean.setServerUrl(prop1.loadProperties().getProperty("solife.server.url"));
			//reportBean.setPassword(prop1.loadProperties().getProperty("solife.batch.password"));
			String command = " run start reportingJob reportingConfigName=" + reportBean.getReportName();
			ReportOperationCli reportOperationCli = new ReportOperationCli();
			reportOperationCli.startReportCli(command, reportBean.getPath(),"TERM_ACTUARIAL_EXTRACT_DEACT");
			H2DatabaseLuncher.executeStatementInsertAndTruncate(
					"INSERT INTO REPORT_AUDIT (ID, REPORT_ID, LAST_RUN_DATE, NEXT_SCHEDULE_DATE, REPORT_SUCCESSFUL, REPORT_FAILED, REPORT_STARTTIME, REPORT_ENDTIME) VALUES (report_seq.nextval,'"
							+ ScheduleConstantClass.termActurialExtractDeact + "','"
							+ scheduler.getTriggerByJobGroupName(reportBean.getReportJobId()).getPreviousFireTime()
							+ "', '" + scheduler.getTriggerByJobGroupName(reportBean.getReportJobId()).getNextFireTime()
							+ "', '" + reportOperationCli.isReportSuccessful() + "', '"
							+ reportOperationCli.isReportFailed() + "' , '" + ReportOperationCli.getStartTime() + "', '"
							+ DateUtility.DateAndTimeNowToString() + "')");
		} catch (IOException e) {
			System.out.println("Application has hit an error, see error details below");
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}


}
