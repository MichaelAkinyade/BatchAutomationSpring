package com.globalcapital.pack.schedule.process;

import java.io.IOException;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.globalcapital.database.datasource.H2DatabaseLuncher;
import com.globalcapital.pack.bean.BatchExecutionBean;
import com.globalcapital.pack.engine.batchSchedule.BatchOperationCli;
import com.globalcapital.pack.schedule.utility.ScheduleAutomationUtility;
import com.globalcapital.pack.schedule.utility.ScheduleConstantClass;
import com.globalcapital.utility.DateUtility;
import com.globalcapital.utility.PropertyFileUtils;

@DisallowConcurrentExecution
public class RegularServiceBatchOne implements Job {

	public void execute(JobExecutionContext context) {
		BatchExecutionBean batchBean = new BatchExecutionBean();
		ScheduleAutomationUtility scheduler = new ScheduleAutomationUtility();

		try {

			PropertyFileUtils prop1 = new PropertyFileUtils("reportBatch.properties");
			batchBean.setPath(prop1.loadProperties().getProperty("solife.batch.console"));
			batchBean.setUsername((prop1.loadProperties().getProperty("solife.batch.username")));
			batchBean.setBatchType("REG_SERV");
			// System.out.println("@@@@@@@@@@@@@@@@@@@@@@ Number of running processes are "+
			// genericService.findAllRuningQueries().size());
			batchBean.setJndiServer(prop1.loadProperties().getProperty("solife.jndi.url"));
			batchBean.setServerUrl(prop1.loadProperties().getProperty("solife.server.url"));
			batchBean.setPassword(prop1.loadProperties().getProperty("solife.batch.password"));
			String command = batchBean.getPath() + " batch:start -batchType " + batchBean.getBatchType()
			+ " -parameters String:Process_Date="+DateUtility.firstOftheMonthString()+ " -u"+ batchBean.getUsername() +" -p " + batchBean.getPassword() + " -jndi.url\r\n "
			+ batchBean.getJndiServer() + " -server.url " + batchBean.getServerUrl() + " -s ";
			BatchOperationCli batchOperationCli = new BatchOperationCli();
			batchOperationCli.startBatchCli(command);
			H2DatabaseLuncher.executeStatementInsertAndTruncate(
					"INSERT INTO BATCH_AUDIT (ID, BATCHTYPE_ID, LAST_RUN_DATE, NEXT_SCHDULE_DATE, BATCH_CSUCCESSFUL, BATCH_FAILED, BATCH_STARTTIME, BATCH_ENDTIME) VALUES (s.nextval,'"
							+ ScheduleConstantClass.regularBatchOne + "','"
							+ scheduler.getTriggerByJobGroupName(batchBean.getBatchJobId()).getPreviousFireTime()
							+ "', '" + scheduler.getTriggerByJobGroupName(batchBean.getBatchJobId()).getNextFireTime()
							+ "', '" + batchOperationCli.isBatchSuccessful() + "', '"
							+ batchOperationCli.isBatchFailed() + "' , '" + BatchOperationCli.getStartTime() + "', '"
							+ DateUtility.DateAndTimeNowToString() + "')");

		} catch (IOException e) {
			System.out.println("Application has hit an error, see error details below");
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
