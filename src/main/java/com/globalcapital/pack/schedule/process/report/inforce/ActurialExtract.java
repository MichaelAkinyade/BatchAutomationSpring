package com.globalcapital.pack.schedule.process.report.inforce;

import java.io.IOException;
import java.util.TimerTask;

import org.quartz.JobExecutionContext;

import com.globalcapital.pack.bean.ReportExecutionBean;
import com.globalcapital.pack.engine.batchSchedule.BatchOperationCli;
import com.globalcapital.pack.engine.batchSchedule.ReportOperationCli;
import com.globalcapital.utility.DateUtility;
import com.globalcapital.utility.PropertyFileUtils;

public class ActurialExtract extends TimerTask {

	public void execute(JobExecutionContext context) {
		
		ReportExecutionBean batchBean = new ReportExecutionBean();

		try {

			PropertyFileUtils prop1 = new PropertyFileUtils("reportBatch.properties");
			batchBean.setPath(prop1.loadProperties().getProperty("solife.batch.console"));
			batchBean.setUsername((prop1.loadProperties().getProperty("solife.batch.username")));
			batchBean.setReportName("ActurialExtract");
			// System.out.println("@@@@@@@@@@@@@@@@@@@@@@ Number of running processes are "+
			// genericService.findAllRuningQueries().size());
			batchBean.setJndiServer(prop1.loadProperties().getProperty("solife.jndi.url"));
			batchBean.setServerUrl(prop1.loadProperties().getProperty("solife.server.url"));
			batchBean.setPassword(prop1.loadProperties().getProperty("solife.batch.password"));
			String command = batchBean.getPath() + " reporting:start -reportName " + batchBean.getReportName()
			+ " -parameters String:PaymentDate="+DateUtility.firstOfMonthReduceMonthByOneString() + " -u"+ batchBean.getUsername() +" -p " + batchBean.getPassword() + " -jndi.url\r\n "
			+ batchBean.getJndiServer() + " -server.url " + batchBean.getServerUrl() + " -s ";
			ReportOperationCli reportOperationCli = new ReportOperationCli();
			System.out.println("~~~~~~~~Date Utility Class~~~~~~~~~~~~~"+DateUtility.DateToStringReduceMonthByone());
			reportOperationCli.startBatchCli(command);
		} catch (IOException e) {
			System.out.println("Application has hit an error, see error details below");
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
