package com.globalcapital.pack.engine.batchSchedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.quartz.SchedulerException;

import com.globalcapital.database.datasource.MyDataSourceFactory;
import com.globalcapital.pack.schedule.utility.ScheduleAutomationUtility;
import com.globalcapital.pack.service.SendEmail;
import com.globalcapital.utility.CheckLogFileForMessage;
import com.globalcapital.utility.DateUtility;

public class BatchOperationCli {

	public static boolean isCompletedSuccessfully;
	public static boolean isFailed;
	public static String messageStatus = "";
	private static final Logger LOGGER = Logger.getLogger(BatchOperationCli.class.getName());
	ScheduleAutomationUtility scheduleAutomationUtility = new ScheduleAutomationUtility("batch");

	public static String getMessageStatus() {
		return messageStatus;
	}

	public static void setMessageStatus(String messageStatus) {
		BatchOperationCli.messageStatus = messageStatus;
	}

	public BatchOperationCli() {
	}

	public boolean checkDBBackgroundActivity() {
		MyDataSourceFactory ms = new MyDataSourceFactory();
		int thresold = 20;
		int dbRuningProcessCount = ms.getRunningQuery();

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%checking background activity, number of running queries are "
				+ dbRuningProcessCount + "%%%%%%%%%%%%%%%%%");

		return dbRuningProcessCount < thresold;

	}

	public static boolean isCompletedSuccessfully() {
		return isCompletedSuccessfully;
	}

	public void setCompletedSuccessfully(boolean isCompletedSuccessfully) {
		BatchOperationCli.isCompletedSuccessfully = isCompletedSuccessfully;
	}

	public static boolean isFailed() {

		return isFailed;
	}

	public void setFailed(boolean isFailed) {
		BatchOperationCli.isFailed = isFailed;
	}

	public int startBatchCli(String command, String jobName) throws IOException, SchedulerException {
		int counter = 0;
		BufferedReader r = null;

		while (counter <= 0) {
			if ((checkDBBackgroundActivity() == true)) {

				// gets the time this batch starts
				setStartTime();

				// Truncates the LastRUn Details table to prepare it for a unique data
				// H2DatabaseLuncher.executeStatementInsertTruncate("TRUNCATE TABLE
				// LASTRUNDETAILS");
				// run Cli command
				Runtime rt = Runtime.getRuntime();
				System.out.println("Batch process has started");
				Process pr = rt.exec(command);
				pr.getInputStream();
				// comment out reading log file to console
				r = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line;
				while (true) {
					line = r.readLine();

					// read log output to see if batch failed
					if (CheckLogFileForMessage.hasCode99(line) == true || CheckLogFileForMessage.hasCode3(line) == true
							|| CheckLogFileForMessage.hasCode4(line) == true
							|| CheckLogFileForMessage.hasCode2(line) == true) {
						this.isFailed = true;
						SendEmail.sendMail(jobName);
						setMessageStatus("Batch failed witht the following error: /n" + line);
						LOGGER.info("Batch failed with the following error: " + LOGGER.getName() + "\n");
						scheduleAutomationUtility.pauseAlltriggers();
						LOGGER.info(
								"Batch failed all job has been paused, Admin needs to fix the problem and resume: \n"
										+ LOGGER.getName());
					} else if (line == null) {
						break;
					}

					System.out.println(line);
				}
				counter++;
				r.close();
				setCompletedSuccessfully(true);
				break;
			}
			try {
				System.out.println("Another Batch is in Progress, it will retry to run the batch in 300 seconds");
				Thread.sleep(300000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;

	}

	public static String getStartTime() {

		return startTime;
	}

	public void setStartTime() {
		BatchOperationCli.startTime = DateUtility.DateAndTimeNowToString();
	}

	public static String startTime;

	public String isBatchSuccessful() {

		String retVal = "";

		if (BatchOperationCli.isFailed != true) {

			retVal = "Batch Completed Successfully";

		}

		return retVal;
	}

	public String isBatchFailed() {

		String retVal = "";

		if (isFailed == true) {

			retVal = "Batch Failed";
		}

		return retVal;
	}

}
