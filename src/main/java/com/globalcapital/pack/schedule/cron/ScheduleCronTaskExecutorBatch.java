package com.globalcapital.pack.schedule.cron;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import com.globalcapital.pack.schedule.process.AutoFinancedRiderOne;
import com.globalcapital.pack.schedule.process.AutoFinancedRiderTwo;
import com.globalcapital.pack.schedule.process.CoverageChargeBatchOne;
import com.globalcapital.pack.schedule.process.CoverageChargeBatchTwo;
import com.globalcapital.pack.schedule.process.DummyBatchProcess;
import com.globalcapital.pack.schedule.process.FinancialOperationBatchRunOne;
import com.globalcapital.pack.schedule.process.GenericFeesOne;
import com.globalcapital.pack.schedule.process.GenericFeesTwo;
import com.globalcapital.pack.schedule.process.IssuingBatchOne;
import com.globalcapital.pack.schedule.process.IssuingBatchTwo;
import com.globalcapital.pack.schedule.process.RegularServiceBatchOne;
import com.globalcapital.pack.schedule.process.RegularServiceBatchRunTwo;
import com.globalcapital.pack.schedule.process.ReschedulingBatchRunOne;
import com.globalcapital.pack.schedule.process.ReschedulingBatchRunTwo;

@Service
public class ScheduleCronTaskExecutorBatch {

	public void execute() {

		try {
			System.out.println("**#################- Loaded Dummy Batch Into Cron -#################");
			// DummyBatch cron job
			JobDetail dummyBatchJob = JobBuilder.newJob(DummyBatchProcess.class).withIdentity("dummyBatch", "group1")
					.build();

			Trigger dummyTrigger = TriggerBuilder.newTrigger().withIdentity("dummyTrigger1", "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 */5 * ? * *")).build();
			Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
			scheduler1.start();
			scheduler1.scheduleJob(dummyBatchJob, dummyTrigger);

		} catch (Exception e) {
			System.out
					.println("**#################- error in loading dummy batch process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################-Loaded Financial Batch Run One Into Cron#################");

			JobDetail financialBatchOneJob = JobBuilder.newJob(FinancialOperationBatchRunOne.class)
					.withIdentity("financialBatchOne", "group1").build();

			Trigger financialOneTrigger = TriggerBuilder.newTrigger().withIdentity("financialOneTrigger", "group1")

					// fires at 4th every month at 6:30 PM //
					.withSchedule(CronScheduleBuilder.cronSchedule("0 30 18 4 * ? *")) //
					.build();
			Scheduler schedulerFinancialOne = new StdSchedulerFactory().getScheduler();
			schedulerFinancialOne.start();
			schedulerFinancialOne.scheduleJob(financialBatchOneJob, financialOneTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading financial batch run one process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################-Loaded Financial Batch Run two Into Cron#################");

			JobDetail financialBatchTwoJob = JobBuilder.newJob(FinancialOperationBatchRunOne.class)
					.withIdentity("financialBatchTwo", "group1").build();

			Trigger financialTwoTrigger = TriggerBuilder.newTrigger().withIdentity("financialTwoTrigger", "group1")

					// fires at last day of every month at 6:30 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 30 18 ? * 6L *")).build();

			Scheduler schedulerFinancialTwo = new StdSchedulerFactory().getScheduler();
			schedulerFinancialTwo.start();
			schedulerFinancialTwo.scheduleJob(financialBatchTwoJob, financialTwoTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading financial batch run two process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################-Loaded Generic fees Batch Run one Into Cron#################");

			JobDetail genericFeesBatchOneJob = JobBuilder.newJob(GenericFeesOne.class)
					.withIdentity("genericBatchOne", "group1").build();

			Trigger genericOneTrigger = TriggerBuilder.newTrigger().withIdentity("genericOneTrigger", "group1")

					// fires at 04th every month at 7:00 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 00 19 04 * ?")).build();

			Scheduler schedulerGenericOne = new StdSchedulerFactory().getScheduler();
			schedulerGenericOne.start();
			schedulerGenericOne.scheduleJob(genericFeesBatchOneJob, genericOneTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading generic batch run one process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################-Loaded Generic fees Batch Run two Into Cron#################");

			JobDetail genericFeesBatchTwoJob = JobBuilder.newJob(GenericFeesTwo.class)
					.withIdentity("genericBatchTwo", "group1").build();

			Trigger genericTwoTrigger = TriggerBuilder.newTrigger().withIdentity("genericTwoTrigger", "group1")
					// fires at last day of every month at 7:00 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 00 19 ? * 6L")).build();

			Scheduler schedulerGenericTwo = new StdSchedulerFactory().getScheduler();
			schedulerGenericTwo.start();
			schedulerGenericTwo.scheduleJob(genericFeesBatchTwoJob, genericTwoTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading generic batch run two process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################-Loaded Coverage Charge Batch Run One Into Cron#################");

			JobDetail coverageChargeBatchOneJob = JobBuilder.newJob(CoverageChargeBatchOne.class)
					.withIdentity("coverageChargeBatchOneJob", "group1").build();

			Trigger coverageChargeOneTrigger = TriggerBuilder.newTrigger()
					.withIdentity("coverageChargeOneTrigger", "group1")
					// fires at 04th every month at 8:00 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 00 20 04 * ?")).build();

			Scheduler schedulerCoverageChrage = new StdSchedulerFactory().getScheduler();
			schedulerCoverageChrage.start();
			schedulerCoverageChrage.scheduleJob(coverageChargeBatchOneJob, coverageChargeOneTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading Coverage batch run One process into cron#################");

			e.printStackTrace();
		}
		try {
			System.out.println("**#################-Loaded Coverage Charge Batch Run Two Into Cron#################");

			JobDetail coverageChargeBatchTwoJob = JobBuilder.newJob(CoverageChargeBatchTwo.class)
					.withIdentity("coverageChargeBatchTwoJob", "group1").build();

			Trigger coverageChargeTwoTrigger = TriggerBuilder.newTrigger()
					.withIdentity("coverageChargeTwoTrigger", "group1")
					// fires at 04th every month at 8:00 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 00 20 ? * 6L")).build();

			Scheduler schedulerCoverageChrage = new StdSchedulerFactory().getScheduler();
			schedulerCoverageChrage.start();
			schedulerCoverageChrage.scheduleJob(coverageChargeBatchTwoJob, coverageChargeTwoTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading Coverage batch run two process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out
					.println("**#################-Loaded Auto Finance Rider Batch Run One Into Cron#################");

			JobDetail autoFinancedBatchOneJob = JobBuilder.newJob(AutoFinancedRiderOne.class)
					.withIdentity("autoFinancedBatchOneJob", "group1").build();

			Trigger autoFinancedOneTrigger = TriggerBuilder.newTrigger()
					.withIdentity("autoFinancedOneTrigger", "group1")
					// fires at 04th every month at 8:00 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 45 20 04 * ?")).build();

			Scheduler schedulerAutoFin = new StdSchedulerFactory().getScheduler();
			schedulerAutoFin.start();
			schedulerAutoFin.scheduleJob(autoFinancedBatchOneJob, autoFinancedOneTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading Autofinanced batch run One process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################-Loaded Autofinanced  Batch Run Two Into Cron#################");

			JobDetail autofinacedBatchTwoJob = JobBuilder.newJob(AutoFinancedRiderTwo.class)
					.withIdentity("autofinacedBatchTwoJob", "group1").build();

			Trigger autofinancedTwoTrigger = TriggerBuilder.newTrigger()
					.withIdentity("autofinancedTwoTrigger", "group1")
					// fires at 04th every month at 8:00 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 45 20 ? * 6L")).build();

			Scheduler schedulerAutofinanced = new StdSchedulerFactory().getScheduler();
			schedulerAutofinanced.start();
			schedulerAutofinanced.scheduleJob(autofinacedBatchTwoJob, autofinancedTwoTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading AutoFinanced batch run two process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out
					.println("**#################-Loaded ReschedulingBatch  Batch Run One Into Cron#################");

			JobDetail reschedulingBatchOneJob = JobBuilder.newJob(ReschedulingBatchRunOne.class)
					.withIdentity("reschedulingBatchOneJob", "group1").build();

			Trigger reschedulingOneTrigger = TriggerBuilder.newTrigger()
					.withIdentity("reschedulingOneTrigger", "group1")
					// fires at 04th every month at 10:45 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 45 22 04 * ?")).build();

			Scheduler schedulerReschedulingOne = new StdSchedulerFactory().getScheduler();
			schedulerReschedulingOne.start();
			schedulerReschedulingOne.scheduleJob(reschedulingBatchOneJob, reschedulingOneTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading Rescheduling batch run One process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################-Loaded Rescheduling  Batch Run Two Into Cron#################");

			JobDetail reschdulingBatchTwoJob = JobBuilder.newJob(ReschedulingBatchRunTwo.class)
					.withIdentity("reschdulingBatchTwoJob", "group1").build();

			Trigger reschedulingTwoTrigger = TriggerBuilder.newTrigger()
					.withIdentity("reschedulingTwoTrigger", "group1")

					// fires at 04th every month at 20:45 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 45 22 ? * 6L")).build();

			Scheduler schedulerReschedulingTwo = new StdSchedulerFactory().getScheduler();
			schedulerReschedulingTwo.start();
			schedulerReschedulingTwo.scheduleJob(reschdulingBatchTwoJob, reschedulingTwoTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading Rescheduling batch run two process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################-Loaded Issuing  Batch Run One Into Cron#################");

			JobDetail issuingBatchOneJob = JobBuilder.newJob(IssuingBatchOne.class)
					.withIdentity("issuingBatchOneJob", "group1").build();

			Trigger issuingOneTrigger = TriggerBuilder.newTrigger().withIdentity("issuingOneTrigger", "group1")

					// fires at 04th every month at 11:10 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 10 23 04 * ?")).build();

			Scheduler schedulerissuingOne = new StdSchedulerFactory().getScheduler();
			schedulerissuingOne.start();
			schedulerissuingOne.scheduleJob(issuingBatchOneJob, issuingOneTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading Issuing batch run One process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################-Loaded Issuing  Batch Run Two Into Cron#################");

			JobDetail issuingBatchTwoJob = JobBuilder.newJob(IssuingBatchTwo.class)
					.withIdentity("issuingBatchTwoJob", "group1").build();

			Trigger issuingTwoTrigger = TriggerBuilder.newTrigger().withIdentity("issuingTwoTrigger", "group1")

					// fires at 04th every month at 23:10 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 10 23 ? * 6L")).build();

			Scheduler schedulerissuingTwo = new StdSchedulerFactory().getScheduler();
			schedulerissuingTwo.start();
			schedulerissuingTwo.scheduleJob(issuingBatchTwoJob, issuingTwoTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading Issuing batch run two process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################-Loaded Regular Service  Batch Run One Into Cron#################");

			JobDetail regularBatchOneJob = JobBuilder.newJob(RegularServiceBatchOne.class)
					.withIdentity("regularBatchOneJob", "group1").build();

			Trigger regularSerOneTrigger = TriggerBuilder.newTrigger().withIdentity("regularSerOneTrigger", "group1")

					// fires at 04th every month at 11:10 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 20 00 04 * ?")).build();

			Scheduler schedulerRegular = new StdSchedulerFactory().getScheduler();
			schedulerRegular.start();
			schedulerRegular.scheduleJob(regularBatchOneJob, regularSerOneTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading Regular batch run One process into cron#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################-Loaded Regular Service  Batch Run Two Into Cron#################");

			JobDetail regularBatchTwoJob = JobBuilder.newJob(RegularServiceBatchRunTwo.class)
					.withIdentity("regularBatchTwoJob", "group1").build();

			Trigger regularTwoTrigger = TriggerBuilder.newTrigger().withIdentity("regularTwoTrigger", "group1")

					// fires at 04th every month at 12:00 PM
					.withSchedule(CronScheduleBuilder.cronSchedule("0 20 00 ? * 6L")).build();

			Scheduler schedulerRegular = new StdSchedulerFactory().getScheduler();
			schedulerRegular.start();
			schedulerRegular.scheduleJob(regularBatchTwoJob, regularTwoTrigger);

		} catch (Exception e) {
			System.out.println(
					"**#################-error in loading Regular Service batch run two process into cron#################");

			e.printStackTrace();
		}

		/*
		 * //Test run try { System.out.
		 * println("**#################-Loaded test Run Batch Run One Into Cron#################"
		 * );
		 * 
		 * JobDetail financialBatchOneJob =
		 * JobBuilder.newJob(FinancialOperationBatchRunOne.class)
		 * .withIdentity("financialBatchOne", "group1").build();
		 * 
		 * Trigger financialOneTrigger =
		 * TriggerBuilder.newTrigger().withIdentity("financialOneTrigger", "group1") //
		 * fires at 4th every month at 6:30 PM //
		 * .withSchedule(CronScheduleBuilder.cronSchedule("0 30 06 04 * ?")) //
		 * .build();
		 * .withSchedule(CronScheduleBuilder.cronSchedule("0 0/15 * * * ?")).build();
		 * 
		 * Scheduler schedulerFinancialOne = new StdSchedulerFactory().getScheduler();
		 * schedulerFinancialOne.start();
		 * schedulerFinancialOne.scheduleJob(financialBatchOneJob, financialOneTrigger);
		 * 
		 * }catch(
		 * 
		 * Exception e) { System.out.println(
		 * "**#################-error in loading financial batch run one process into cron#################"
		 * );
		 * 
		 * e.printStackTrace(); }
		 */
	}

}
