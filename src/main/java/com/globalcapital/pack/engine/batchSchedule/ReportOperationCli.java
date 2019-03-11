package com.globalcapital.pack.engine.batchSchedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.quartz.SchedulerException;

import com.globalcapital.database.datasource.MyDataSourceFactory;
import com.globalcapital.pack.schedule.utility.ScheduleAutomationUtility;
import com.globalcapital.pack.service.SendEmail;
import com.globalcapital.utility.CheckLogFileForMessage;
import com.globalcapital.utility.DateUtility;

public class ReportOperationCli {

	public static boolean isCompletedSuccessfully;
	public static boolean isFailed;
	public static String messageStatus = "";
	private static final Logger LOGGER = Logger.getLogger(BatchOperationCli.class.getName());
	ScheduleAutomationUtility scheduleAutomationUtility = new ScheduleAutomationUtility("report");
	public static String startTime;

	public static String getMessageStatus() {
		return messageStatus;
	}

	public static void setMessageStatus(String messageStatus) {
		ReportOperationCli.messageStatus = messageStatus;
	}

	public ReportOperationCli() {
	}

	public static String getStartTime() {

		return startTime;
	}

	public void setStartTime() {
		ReportOperationCli.startTime = DateUtility.DateAndTimeNowToString();
	}

	public boolean checkDBBackgroundActivity() {
		MyDataSourceFactory ms = new MyDataSourceFactory();
		int thresold = 20;
		int dbRuningProcessCount = ms.getRunningQuery();

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%checking background activity, number of running queries are "
				+ dbRuningProcessCount + "%%%%%%%%%%%%%%%%%");

		return dbRuningProcessCount < thresold;

	}

	public void setCompletedSuccessfully(boolean isCompletedSuccessfully) {
		ReportOperationCli.isCompletedSuccessfully = isCompletedSuccessfully;
	}

	public static boolean isFailed() {

		return isFailed;
	}

	public void setFailed(boolean isFailed) {
		ReportOperationCli.isFailed = isFailed;
	}

	public static boolean isCompletedSuccessfully() {
		return isCompletedSuccessfully;
	}

	public String isReportSuccessful() {

		String retVal = "";

		if (ReportOperationCli.isFailed != true) {

			retVal = "Report is running Successfully";

		}

		return retVal;
	}

	public String isReportFailed() {

		String retVal = "";

		if (isFailed == true) {

			retVal = "Batch Failed";
		}

		return retVal;
	}

	public int startReportCli(String command, String path, String jobName) throws IOException, SchedulerException {
		int counter = 0;
		BufferedReader r = null;

		while (counter <= 0) {
			if ((checkDBBackgroundActivity() == true)) {
				// gets the time this Report starts
				setStartTime();
				// run Cli command
				Runtime rt = Runtime.getRuntime();
				ProcessBuilder processBuilder = new ProcessBuilder();
				processBuilder.directory(new File("C:\\Solife-runtime\\com.bsb.solife.tools.ws.cli-6.3.20\\bin"));
				processBuilder.command("cmd.exe", "/C", command);

				// Process pr = rt.exec(command);
				Process pr = processBuilder.start();
				pr.getInputStream();
				// comment out reading log file to console
				r = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line;
				while (true) {
					line = r.readLine();
					if (CheckLogFileForMessage.hasReportErrorCode17(line) == true
							|| CheckLogFileForMessage.hasReportErrorMessage(line)
							|| CheckLogFileForMessage.hasReportErrorConnectRefused(line)) {
						this.isFailed = true;
						
						setMessageStatus("Report failed witht the following error: /n" + line);
						LOGGER.info("Report failed with the following error: " + LOGGER.getName() + "\n");
						SendEmail.sendMail(jobName);
						scheduleAutomationUtility.pauseAlltriggers();
						LOGGER.info(
								"Report failed all job has been paused, Admin needs to fix the problem and resume: \n"
										+ LOGGER.getName());
						break;

					} else if (line == null) {
						break;
					}

					System.out.println(line);
				}
				// checkDBBackgroundActivity()= false;
				counter++;
				r.close();
				break;
			}
			try {
				System.out.println("Another Report is in Progress, it will retry to run the batch in 300 seconds");
				Thread.sleep(300000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;

	}

}
