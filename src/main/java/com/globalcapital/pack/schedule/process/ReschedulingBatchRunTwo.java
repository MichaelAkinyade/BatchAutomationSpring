package com.globalcapital.pack.schedule.process;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.globalcapital.pack.bean.BatchExecutionBean;
import com.globalcapital.pack.engine.batchSchedule.BatchOperationCli;
import com.globalcapital.utility.DateUtility;
import com.globalcapital.utility.PropertyFileUtils;

public class ReschedulingBatchRunTwo implements Job {

	public void execute(JobExecutionContext context) {
		BatchExecutionBean batchBean = new BatchExecutionBean();

		try {

			PropertyFileUtils prop1 = new PropertyFileUtils("reportBatch.properties");
			batchBean.setPath(prop1.loadProperties().getProperty("solife.batch.console"));
			batchBean.setUsername((prop1.loadProperties().getProperty("solife.batch.username")));
			batchBean.setBatchType("BILL_RESCH");
			// System.out.println("@@@@@@@@@@@@@@@@@@@@@@ Number of running processes are "+
			// genericService.findAllRuningQueries().size());
			batchBean.setJndiServer(prop1.loadProperties().getProperty("solife.jndi.url"));
			batchBean.setServerUrl(prop1.loadProperties().getProperty("solife.server.url"));
			String command = batchBean.getPath() + " batch:start -batchType " + batchBean.getBatchType()
			+ " -parameters String:RESCHEDULING_DATE="+DateUtility.lastDayOfTheMonth() + " -u"+ batchBean.getUsername() +" -p " + batchBean.getPassword() + " -jndi.url\r\n "
			+ batchBean.getJndiServer() + " -server.url " + batchBean.getServerUrl() + " -s ";
			BatchOperationCli batchOperationCli = new BatchOperationCli();
			batchOperationCli.startBatchCli(command);
			System.out.println("~~~~~~~~~~~~ ReschedulingBatchRunTwo batch completed~~~~~~~~~~"+DateUtility.DateNowToString());

		} catch (IOException e) {
			System.out.println("Application has hit an error, see error details below");
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
