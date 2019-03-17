package com.globalcapital.pack.schedule.cron;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.globalcapital.database.datasource.H2DatabaseLuncher;
import com.globalcapital.pack.bean.ReportTypeCronTimeBean;
import com.globalcapital.pack.schedule.report.process.ActurialExtractWeekly;
import com.globalcapital.pack.schedule.report.process.FundSplits;
import com.globalcapital.pack.schedule.report.process.LifeCovers;
import com.globalcapital.pack.schedule.report.process.PolicyHolders;
import com.globalcapital.pack.schedule.report.process.PolicyPayers;
import com.globalcapital.pack.schedule.report.process.Policybeneficiaries;
import com.globalcapital.pack.schedule.report.process.TermActurialExtractCFI;
import com.globalcapital.pack.schedule.report.process.TermActurialExtractDeact;
import com.globalcapital.pack.schedule.report.process.TermActurialExtractDeath;
import com.globalcapital.pack.schedule.report.process.TermActurialExtractRedemp;
import com.globalcapital.pack.schedule.report.process.TermActurialExtractSurren;
import com.globalcapital.pack.schedule.report.process.TermActurialExtractTerm;
import com.globalcapital.pack.schedule.report.process.ThirdPartyActiveAddress;

@Service
public class ScheduleCronTaskExecutorReport {

	public ScheduleCronTaskExecutorReport() {

	}

	// For the report console
	public void execute() throws SchedulerException, IOException {
		
		FileInputStream in = new FileInputStream("quartz.properties");
		Properties props = new Properties();
		props.load(in);
		in.close();
		
		StdSchedulerFactory schedFactory = new StdSchedulerFactory();
		FileOutputStream out = new FileOutputStream("quartz.properties");
		props.setProperty("org.quartz.scheduler.instanceName", "reportInstance");
		props.setProperty("org.quartz.scheduler.instanceId", "report419");
		props.setProperty("org.quartz.threadPool.threadCount", "1");
		props.store(out, null);
		out.close();
		schedFactory.initialize(quartzProperties());
		// props.setProperty("org.quartz.jobStore.misfireThreshold", "600000");
		ReportTypeCronTimeBean reportTypeCronTime = H2DatabaseLuncher.getScheduleTimeReport();

		try {
			System.out.println("**#################- Loaded Acutrial Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail acutualWeekly = JobBuilder.newJob(ActurialExtractWeekly.class)
					.withIdentity("acutrialWeekly", "acturialGroup").build();

			Trigger acturialTrigger = TriggerBuilder.newTrigger()
					.withIdentity("acutrialWeeklyTrigger1", "acturialGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getAcutrialWeekly())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler scheduler1 = schedFactory.getScheduler();
			scheduler1.start();
			if (scheduler1.checkExists(acutualWeekly.getKey())) {
				scheduler1.rescheduleJob(acturialTrigger.getKey(), acturialTrigger);
			} else {

				scheduler1.scheduleJob(acutualWeekly, acturialTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading Acutrial Extract process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################- Loaded LifeCovers Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail lifeCovers = JobBuilder.newJob(LifeCovers.class)
					.withIdentity("lifeCoversWeekly", "lifeCoverGroup").build();

			Trigger lifeCoversTrigger = TriggerBuilder.newTrigger().withIdentity("lifeCoversTrigger1", "lifeCoverGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(
							CronScheduleBuilder.cronSchedule(reportTypeCronTime.getLifeCoversWeekly()).withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulerLiveCoveres = schedFactory.getScheduler();
			schedulerLiveCoveres.start();
			if (schedulerLiveCoveres.checkExists(lifeCovers.getKey())) {
				schedulerLiveCoveres.rescheduleJob(lifeCoversTrigger.getKey(), lifeCoversTrigger);
			} else {

				schedulerLiveCoveres.scheduleJob(lifeCovers, lifeCoversTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading lifecovers report process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################- Loaded FundSplits Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail fundSplits = JobBuilder.newJob(FundSplits.class).withIdentity("fundSplitsWeekly", "fundSlipGroup")
					.build();

			Trigger fundSplitsTrigger = TriggerBuilder.newTrigger().withIdentity("fundSplitTrigger1", "fundSlipGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getFundSplitsWeekly())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulerLiveCoveres = schedFactory.getScheduler();
			schedulerLiveCoveres.start();
			if (schedulerLiveCoveres.checkExists(fundSplits.getKey())) {
				schedulerLiveCoveres.rescheduleJob(fundSplitsTrigger.getKey(), fundSplitsTrigger);
			} else {

				schedulerLiveCoveres.scheduleJob(fundSplits, fundSplitsTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading lifecovers report process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################- Loaded policyBeneficiaries Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail policyBeneficiaries = JobBuilder.newJob(Policybeneficiaries.class)
					.withIdentity("policyBeneficiaries", "policyBeneficiariesGroup").build();

			Trigger policyBeneficiariessTrigger = TriggerBuilder.newTrigger()
					.withIdentity("policyBeneficiariesTrigger1", "policyBeneficiariesGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getPolicyBeneficiaries())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulerpolicyBeneficiaries = schedFactory.getScheduler();
			schedulerpolicyBeneficiaries.start();
			if (schedulerpolicyBeneficiaries.checkExists(policyBeneficiaries.getKey())) {
				schedulerpolicyBeneficiaries.rescheduleJob(policyBeneficiariessTrigger.getKey(),
						policyBeneficiariessTrigger);
			} else {

				schedulerpolicyBeneficiaries.scheduleJob(policyBeneficiaries, policyBeneficiariessTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading policyBeneficiaries report process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################- Loaded policyHolders Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail policyHolders = JobBuilder.newJob(PolicyHolders.class)
					.withIdentity("policyHolders", "policyHoldersGroup").build();

			Trigger policyHoldersTrigger = TriggerBuilder.newTrigger()
					.withIdentity("policyHoldersTrigger1", "policyHoldersGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getPolicyHolders())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulerpolicyBeneficiaries = schedFactory.getScheduler();
			schedulerpolicyBeneficiaries.start();
			if (schedulerpolicyBeneficiaries.checkExists(policyHolders.getKey())) {
				schedulerpolicyBeneficiaries.rescheduleJob(policyHoldersTrigger.getKey(), policyHoldersTrigger);
			} else {

				schedulerpolicyBeneficiaries.scheduleJob(policyHolders, policyHoldersTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading policyHolders report process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out.println("**#################- Loaded policyPayers Weekly Into Cron -#################");
			JobDetail policyPayers = JobBuilder.newJob(PolicyPayers.class)
					.withIdentity("policyPayers", "policyPayersGroup").build();

			Trigger policyPayersTrigger = TriggerBuilder.newTrigger()
					.withIdentity("policyPayersTrigger1", "policyPayersGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getPolicyPayers())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulerpolicyPayers = schedFactory.getScheduler();
			schedulerpolicyPayers.start();
			if (schedulerpolicyPayers.checkExists(policyPayers.getKey())) {
				schedulerpolicyPayers.rescheduleJob(policyPayersTrigger.getKey(), policyPayersTrigger);
			} else {

				schedulerpolicyPayers.scheduleJob(policyPayers, policyPayersTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading policyPayers report process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out
					.println("**#################- Loaded thirdPartyActiveAddress Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail thirdPartyActiveAddress = JobBuilder.newJob(ThirdPartyActiveAddress.class)
					.withIdentity("thirdPartyActiveAddress", "thirdPartyActiveAddressGroup").build();

			Trigger thirdPartyActiveAddressTrigger = TriggerBuilder.newTrigger()
					.withIdentity("thirdPartyActiveAddressTrigger1", "thirdPartyActiveAddressGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getThirdPartyActiveAddress())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulerpolicyPayers = schedFactory.getScheduler();
			schedulerpolicyPayers.start();
			if (schedulerpolicyPayers.checkExists(thirdPartyActiveAddress.getKey())) {
				schedulerpolicyPayers.rescheduleJob(thirdPartyActiveAddressTrigger.getKey(),
						thirdPartyActiveAddressTrigger);
			} else {

				schedulerpolicyPayers.scheduleJob(thirdPartyActiveAddress, thirdPartyActiveAddressTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading thirdPartyActiveAddressTrigger report process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out.println(
					"**#################- Loaded termActurialExtractCFITrigger Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail termActurialExtractCFI = JobBuilder.newJob(TermActurialExtractCFI.class)
					.withIdentity("termActurialExtractCFI", "termActurialExtractCFIGroup").build();

			Trigger termActurialExtractCFITrigger = TriggerBuilder.newTrigger()
					.withIdentity("termActurialExtractCFITrigger1", "termActurialExtractCFIGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getTermActurialExtractCFI())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulerpolicyPayers = schedFactory.getScheduler();
			schedulerpolicyPayers.start();
			if (schedulerpolicyPayers.checkExists(termActurialExtractCFI.getKey())) {
				schedulerpolicyPayers.rescheduleJob(termActurialExtractCFITrigger.getKey(),
						termActurialExtractCFITrigger);
			} else {

				schedulerpolicyPayers.scheduleJob(termActurialExtractCFI, termActurialExtractCFITrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading termActurialExtractCFITrigger report process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out.println(
					"**#################- Loaded termActurialExtractDeact Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail termActurialExtractDeact = JobBuilder.newJob(TermActurialExtractDeact.class)
					.withIdentity("termActurialExtractDeact", "termActurialExtractDeactGroup").build();

			Trigger termActurialExtractDeactTrigger = TriggerBuilder.newTrigger()
					.withIdentity("termActurialExtractDeactTrigger1", "termActurialExtractDeactGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getTermActurialExtractDeact())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulerpolicyPayers = schedFactory.getScheduler();
			schedulerpolicyPayers.start();
			if (schedulerpolicyPayers.checkExists(termActurialExtractDeact.getKey())) {
				schedulerpolicyPayers.rescheduleJob(termActurialExtractDeactTrigger.getKey(),
						termActurialExtractDeactTrigger);
			} else {

				schedulerpolicyPayers.scheduleJob(termActurialExtractDeact, termActurialExtractDeactTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading termActurialExtractDeactTrigger report process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out.println(
					"**#################- Loaded termActurialExtractDeath Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail termActurialExtractDeath = JobBuilder.newJob(TermActurialExtractDeath.class)
					.withIdentity("termActurialExtractDeath", "termActurialExtractDeathGroup").build();
			Trigger termActurialExtractDeathTrigger = TriggerBuilder.newTrigger()
					.withIdentity("termActurialExtractDeathTrigger1", "termActurialExtractDeathGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getTermActurialExtractDeath())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulertermActurialExtractDeath = schedFactory.getScheduler();
			schedulertermActurialExtractDeath.start();
			if (schedulertermActurialExtractDeath.checkExists(termActurialExtractDeath.getKey())) {
				schedulertermActurialExtractDeath.rescheduleJob(termActurialExtractDeathTrigger.getKey(),
						termActurialExtractDeathTrigger);
			} else {

				schedulertermActurialExtractDeath.scheduleJob(termActurialExtractDeath,
						termActurialExtractDeathTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading termActurialExtractDeactTrigger report process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out.println(
					"**#################- Loaded termActurialExtractRedemp Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail termActurialExtractRedemp = JobBuilder.newJob(TermActurialExtractRedemp.class)
					.withIdentity("termActurialExtractRedemp", "termActurialExtractRedempGroup").build();
			Trigger termActurialExtractRedempTrigger = TriggerBuilder.newTrigger()
					.withIdentity("termActurialExtractRedempTrigger1", "termActurialExtractRedempGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getTermActurialExtractRedemp())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulertermActurialExtractRedemp = schedFactory.getScheduler();
			schedulertermActurialExtractRedemp.start();
			if (schedulertermActurialExtractRedemp.checkExists(termActurialExtractRedemp.getKey())) {
				schedulertermActurialExtractRedemp.rescheduleJob(termActurialExtractRedempTrigger.getKey(),
						termActurialExtractRedempTrigger);
			} else {

				schedulertermActurialExtractRedemp.scheduleJob(termActurialExtractRedemp,
						termActurialExtractRedempTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading termActurialExtractRedemp report process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out.println(
					"**#################- Loaded termActurialExtractSurren Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail termActurialExtractSurren = JobBuilder.newJob(TermActurialExtractSurren.class)
					.withIdentity("termActurialExtractSurren", "termActurialExtractSurrenGroup").build();
			Trigger termActurialExtractSurrenTrigger = TriggerBuilder.newTrigger()
					.withIdentity("termActurialExtractSurrenTrigger1", "termActurialExtractSurrenGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getTermActurialExtractSurren())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulertermActurialExtractSurren = schedFactory.getScheduler();
			schedulertermActurialExtractSurren.start();
			if (schedulertermActurialExtractSurren.checkExists(termActurialExtractSurren.getKey())) {
				schedulertermActurialExtractSurren.rescheduleJob(termActurialExtractSurrenTrigger.getKey(),
						termActurialExtractSurrenTrigger);
			} else {

				schedulertermActurialExtractSurren.scheduleJob(termActurialExtractSurren,
						termActurialExtractSurrenTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading termActurialExtractSurren report process into cron --#################");

			e.printStackTrace();
		}

		try {
			System.out
					.println("**#################- Loaded termActurialExtractTerm Weekly Into Cron -#################");
			// DummyBatch cron job
			JobDetail termActurialExtractTerm = JobBuilder.newJob(TermActurialExtractTerm.class)
					.withIdentity("termActurialExtractTerm", "termActurialExtractTermGroup").build();
			Trigger termActurialExtractTermTrigger = TriggerBuilder.newTrigger()
					.withIdentity("termActurialExtractTermTrigger1", "termActurialExtractTermGroup")
					// using dynamic cron dates fetched from the Database
					.withSchedule(CronScheduleBuilder.cronSchedule(reportTypeCronTime.getTermActurialExtractTerm())
							.withMisfireHandlingInstructionDoNothing())
					.build();
			Scheduler schedulertertermActurialExtractTerm = schedFactory.getScheduler();
			schedulertertermActurialExtractTerm.start();
			if (schedulertertermActurialExtractTerm.checkExists(termActurialExtractTerm.getKey())) {
				schedulertertermActurialExtractTerm.rescheduleJob(termActurialExtractTermTrigger.getKey(),
						termActurialExtractTermTrigger);
			} else {

				schedulertertermActurialExtractTerm.scheduleJob(termActurialExtractTerm,
						termActurialExtractTermTrigger);
			}

		} catch (Exception e) {
			System.out.println(
					"**#################- error in loading termActurialExtractTerm report process into cron --#################");

			e.printStackTrace();
		}

	}

	// setting the quartz configutaion properties
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

}
