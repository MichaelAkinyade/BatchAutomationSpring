package com.globalcapital.pack.schedule.batch.process;

import java.io.IOException;
import java.util.Properties;

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
public class GenericFeesTwo implements Job {

	public void execute(JobExecutionContext context) {
		BatchExecutionBean batchBean = new BatchExecutionBean();
		ScheduleAutomationUtility scheduler = new ScheduleAutomationUtility("batch");

		Properties prop = null;
		// Spring issues cannot instantiate bean factory to load
		// genericService.findAllRuningQueries().size(), hence using Datasource to fetch
		// database object
		// ApplicationContext factory = new
		// AnnotationConfigApplicationContext(GenericServiceImpl.class);
		// GenericServiceImpl genericService = (GenericServiceImpl)
		// SpringApplicationContext.getBean("genericServiceImp");
		// GenericServiceImpl genericService=factory.getBean(GenericServiceImpl.class);
		try {

			PropertyFileUtils prop1 = new PropertyFileUtils("reportBatch.properties");
			prop = prop1.loadProperties();
			batchBean.setPath(prop1.loadProperties().getProperty("solife.batch.console"));
			batchBean.setUsername((prop1.loadProperties().getProperty("solife.batch.username")));
			batchBean.setBatchType("GEN_FEES");
			batchBean.setBatchJoId("genericFeesBatchTwo");
			batchBean.setJndiServer(prop1.loadProperties().getProperty("solife.jndi.url"));
			batchBean.setServerUrl(prop1.loadProperties().getProperty("solife.server.url"));
			String command = batchBean.getPath() + " batch:start -batchType " + batchBean.getBatchType()
					+ " -parameters String:Calculation_Date=" + DateUtility.fifteenthDayOfMonthReduceMonthByOneString()
					+ " -batchConfig Integer:maxBusinessError=25  -u" + batchBean.getUsername() + " -p "
					+ batchBean.getPassword() + " -jndi.url\r\n " + batchBean.getJndiServer() + " -server.url "
					+ batchBean.getServerUrl() + " -s ";
			BatchOperationCli batchOperationCli = new BatchOperationCli();
			batchOperationCli.startBatchCli(command,"genericFeesBatchTwo");
			H2DatabaseLuncher.executeStatementInsertAndTruncate(
					"INSERT INTO BATCH_AUDIT (ID, BATCHTYPE_ID, LAST_RUN_DATE, NEXT_SCHDULE_DATE, BATCH_CSUCCESSFUL, BATCH_FAILED, BATCH_STARTTIME, BATCH_ENDTIME) VALUES (s.nextval,'"
							+ ScheduleConstantClass.genericFeesBatchTwo + "','"
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
