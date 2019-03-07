package com.globalcapital.pack.schedule.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

public class ScheduleAutomationUtility {

	public Scheduler getDetailsOfSingleScheduleJob(String jobName, String jobGroup) throws SchedulerException {

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();

		JobDetail existingJobDetail = scheduler.getJobDetail(new JobKey(jobName, jobGroup));
		if (existingJobDetail != null) {
			List<JobExecutionContext> currentlyExecutingJobs = (List<JobExecutionContext>) scheduler
					.getCurrentlyExecutingJobs();

			for (JobExecutionContext jec : currentlyExecutingJobs) {
				if (existingJobDetail.equals(jec.getJobDetail())) {
					String message = jobName + " is already running.";
					System.out.println(message);
					scheduler = jec.getScheduler();
					throw new JobExecutionException(message, false);
				}
			}
			// sched.deleteJob(jobName, jobGroup); if you want to delete the scheduled but
			// not-currently-running job
		} else {

			System.out.println("Job hasnt been scheduled");
		}

		return scheduler;
	}

// List of currently running schedule
	public List<String> findCurrentRunningSchedule() throws SchedulerException {
		List<String> retVal = new ArrayList<String>();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		List<JobExecutionContext> currentlyExecutingJobs = (List<JobExecutionContext>) scheduler
				.getCurrentlyExecutingJobs();
		for (JobExecutionContext jec : currentlyExecutingJobs) {
			retVal.add(jec.getScheduler().getSchedulerName());
		}
		return retVal;
	}

	// Get the List of all triggers by Trigger class
	@SuppressWarnings("unchecked")
	public List<Trigger> getListOfTriggersAndJob() throws SchedulerException {

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		List<Trigger> retVal = new ArrayList<>();
		// List all quartz jobs
		for (String groupName : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				String jobName = jobKey.getName();
				String jobGroup = jobKey.getGroup();
				retVal.add(scheduler.getTriggersOfJob(jobKey).get(0));
				Date nextFireTime = retVal.get(0).getNextFireTime();
				System.out.println(
						"Job : " + jobName + ", Group : " + jobGroup + ", Next execution time : " + nextFireTime);
			}

		}

		return retVal;
	}

	// find a specific trigger class by the job group identifier
	public Trigger getTriggerByJobGroupName(String jobNameString) throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		Trigger trig = null;
		// List all quartz jobs
		for (String groupName : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				String jobName = jobKey.getName();
				if (jobName.equals(jobNameString)) {

					trig = scheduler.getTriggersOfJob(jobKey).get(0);

				}

			}

		}

		return trig;
	}

	public void resumeAllTriggersMine() throws SchedulerException {

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		// scheduler.resumeAll();
		Trigger trig;
		try {
			// String jobNameFind= "dummyBatch";
			// String groupName = "group1";

			for (String groupName : scheduler.getJobGroupNames()) {

				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
					String jobName = jobKey.getName();
					JobDetail existingJobDetail = scheduler.getJobDetail(new JobKey(jobName, groupName));

					trig = scheduler.getTriggersOfJob(jobKey).get(0);

					if (!(scheduler.checkExists(jobKey)) == true) {
						scheduler.scheduleJob(existingJobDetail, trig);
					}

				}

			}

		} catch (SchedulerException e) {

			e.printStackTrace();
		}

	}

	public void resumeAllTriggers() throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.resumeAll();
	}

	// Pause Single batch within the Schedular.
	public void resumeSingleBatch(String groupNameEx) throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		try {
			
			for (String groupName : scheduler.getJobGroupNames()) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

					if (jobKey.getName().equals(groupNameEx)) {
						scheduler.resumeJob(jobKey);
					}
				}
			}

		} catch (SchedulerException e) {

			e.printStackTrace();
		}

	}

	public void pauseAlltriggers() throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.pauseAll();
	}

	// Pause Single batch within the schedular.
	// Pause Single batch within the Schedular.
	public void pauseSingleBatch(String groupNameEx) throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		try {
			// String groupName = "group1";
			for (String groupName : scheduler.getJobGroupNames()) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

					if (jobKey.getName().equals(groupNameEx)) {
						scheduler.pauseJob(jobKey);
					}
				}
			}

		} catch (SchedulerException e) {

			e.printStackTrace();
		}

	}

	public int getNumberOfScheduledBatchJobs() throws SchedulerException {
		int retVal = 0;
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		// Subtracting 1 because we have a dummy batch job group(group 1) used for test
		// purpose and shldnt be removed from the code
		retVal = scheduler.getJobGroupNames().size() - 1;
		if (retVal < 0) {
			retVal = 0;
		}

		return retVal;
	}

}
