package com.globalcapital.pack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.globalcapital.pack.schedule.cron.ScheduleCronTaskExecutorBatch;

@SpringBootApplication
public class LoginApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(LoginApplication.class);

	@Autowired
	ScheduleCronTaskExecutorBatch scheduleCronTaskExecutorBtach;

	@Value("${solife.batch.console}")
	private String props;

	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		scheduleCronTaskExecutorBtach.execute();

	}
}
