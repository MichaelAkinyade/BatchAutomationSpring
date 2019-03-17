package com.globalcapital.pack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.globalcapital.pack.schedule.cron.ScheduleCronTaskExecutorBatch;
import com.globalcapital.pack.schedule.cron.ScheduleCronTaskExecutorReport;

@SpringBootApplication
public class LoginApplication {
	private static final Logger logger = LoggerFactory.getLogger(LoginApplication.class);

	@Autowired
	 public ScheduleCronTaskExecutorBatch scheduleCronTaskExecutorBtach;

	@Autowired
	public ScheduleCronTaskExecutorReport scheduleCronTaskExecutorReport;

	@Value("${solife.batch.console}")
	private String props;

	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}
	
}
