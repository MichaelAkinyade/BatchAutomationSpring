package com.globalcapital.pack.schedule.process;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.globalcapital.pack.bean.BatchExecutionBean;
import com.globalcapital.pack.engine.batchSchedule.BatchOperationCli;
import com.globalcapital.utility.PropertyFileUtils;

public class DummyBatchProcess implements Job {

	public void execute(JobExecutionContext context) {
		BatchExecutionBean batchBean = new BatchExecutionBean();

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
			batchBean.setBatchType("DummyBatch");
			// System.out.println("@@@@@@@@@@@@@@@@@@@@@@ Number of running processes are "+
			// genericService.findAllRuningQueries().size());
			batchBean.setJndiServer(prop1.loadProperties().getProperty("solife.jndi.url"));
			batchBean.setServerUrl(prop1.loadProperties().getProperty("solife.server.url"));

			String command = batchBean.getPath() + " batch:start -batchType " + batchBean.getBatchType() + " -u "
					+ batchBean.getUsername() + " -p " + batchBean.getPassword() + " -jndi.url\r\n "
					+ batchBean.getJndiServer() + " -server.url " + batchBean.getServerUrl() + " -s ";
			BatchOperationCli batchOperationCli = new BatchOperationCli();
			batchOperationCli.startBatchCli(command);
		} catch (IOException e) {
			System.out.println("#############  properties file is" + prop);
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
