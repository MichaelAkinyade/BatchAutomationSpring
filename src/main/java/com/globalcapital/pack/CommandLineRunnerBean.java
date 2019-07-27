package com.globalcapital.pack;

import java.io.IOException;
import java.net.URISyntaxException;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.globalcapital.pack.schedule.cron.ScheduleCronTaskExecutorBatch;
import com.globalcapital.pack.schedule.cron.ScheduleCronTaskExecutorReport;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineRunnerBean.class);
	@Autowired
	private ScheduleCronTaskExecutorBatch scheduleCronTaskExecutorBtach;

	@Autowired
	private ScheduleCronTaskExecutorReport scheduleCronTaskExecutorReport;

	public void run(String... args) throws SchedulerException, IOException, URISyntaxException {
		scheduleCronTaskExecutorBtach.execute();
		scheduleCronTaskExecutorReport.execute();
		logger.info("Application started with arguments and Loaded cron scheduler");
	}
}