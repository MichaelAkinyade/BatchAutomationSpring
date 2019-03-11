package com.globalcapital.pack.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.quartz.CronScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.globalcapital.database.datasource.H2DatabaseLuncher;
import com.globalcapital.database.datasource.MyDataSourceFactory;
import com.globalcapital.pack.bean.BatchScheduleTime;
import com.globalcapital.pack.bean.BatchTypeBean;
import com.globalcapital.pack.bean.EmailBean;
import com.globalcapital.pack.bean.ReportScheduleTime;
import com.globalcapital.pack.bean.ReportTypeBean;
import com.globalcapital.pack.bean.SessionBean;
import com.globalcapital.pack.configuration.security.PasswordEncoding;
import com.globalcapital.pack.database.entity.Users;
import com.globalcapital.pack.database.serviceInterface.MyDataSourceFactoryService;
import com.globalcapital.pack.model.User;
import com.globalcapital.pack.schedule.utility.ScheduleAutomationUtility;
import com.globalcapital.pack.schedule.utility.ScheduleConstantClass;
import com.globalcapital.utility.DateUtility;

@Controller
//@SessionAttributes("bean")
@SessionAttributes(value = { "bean", "batchTypeList", "reportTypeList" })
public class LoginController {

	PasswordEncoding passwordEncoding = new PasswordEncoding();

	MyDataSourceFactoryService myDataSourceFactoryService;
	// Load the report Scheduler instance
	ScheduleAutomationUtility scheduleAutomationUtilityReport = new ScheduleAutomationUtility("report");
	// Load the batch Scheduler instance
	ScheduleAutomationUtility scheduleAutomationUtilityBatch = new ScheduleAutomationUtility("batch");

	public LoginController() {

	}

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/login/signup.html");
		return modelAndView;
	}

	@ModelAttribute("registrationBean")
	public Users registrationBean() {
		return new Users();
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@ModelAttribute("registrationBean") @Valid Users registrationBean) {
		ModelAndView modelAndView = new ModelAndView();

		for (Users user : H2DatabaseLuncher.getUsersList()) {
			if (user.getUserName().equals(registrationBean.getUserName())) {
				modelAndView.setViewName("/login/signup.html");
				return modelAndView;
			} 
		}
		
		if (registrationBean.getPassword().equals(registrationBean.getConfirmPassword())) {
			
			String sql = "insert into user values (user_seq.nextval, '" + registrationBean.getFirstName() + "', '"
					+ registrationBean.getLastName() + "', '" + registrationBean.getUserName() + "','"
					+ passwordEncoding.encoder(registrationBean.getConfirmPassword()) + "', 1)";
			H2DatabaseLuncher.executeStatementInsertAndTruncate(sql);
			modelAndView.setViewName("/login");
			return modelAndView;
		}
		
		modelAndView.setViewName("/login/signup.html");

		return modelAndView;
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, Model model)
			throws SchedulerException, FileNotFoundException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user = H2DatabaseLuncher.getUserByUsername(auth.getName());
		SessionBean sessionBean = new SessionBean();
		sessionBean.setFirstName(user.getFirstName());
		sessionBean.setLastName(user.getLastName());
		sessionBean.setFullName(user.getFirstName() + " " + user.getLastName());
		sessionBean.setLoggedOnName(user.getUserName());
		sessionBean.setNumberOfRunningBatchs(scheduleAutomationUtilityBatch.getNumberOfScheduledBatchJobs());
		sessionBean.setNumberOfRunningReports(scheduleAutomationUtilityReport.getNumberOfScheduledReportsJobs());
		sessionBean.setUserName(auth.getName());
		// Save the bean to the session
		// request.setAttribute("bean", sessionBean);
		model.addAttribute("bean", sessionBean);
		modelAndView.addObject("numberJobs", sessionBean.getNumberOfRunningBatchs());
		modelAndView.addObject("numberReportJob", sessionBean.getNumberOfRunningReports());
		// scheduleAutomationUtilityBatch.getListOfTriggersAndJob();
		modelAndView.addObject("user", user);

		modelAndView.addObject("adminMessage", MyDataSourceFactory.getRunningQuery());
		modelAndView.setViewName("/login/index3.html");
		return modelAndView;
	}

	@RequestMapping(value = "/batchConfig", method = RequestMethod.GET)
	public ModelAndView emailSConfig(HttpServletRequest request, Model model) throws SchedulerException {
		ModelAndView modelAndView = new ModelAndView();
		List<BatchTypeBean> batchTypeList = H2DatabaseLuncher.getBatchType();
		modelAndView.addObject("bean");
		model.addAttribute("batchTypeList");
		modelAndView.addObject("batchTypeList", batchTypeList);
		modelAndView.addObject("tableDataList", H2DatabaseLuncher.tableDataList("batchReport"));
		modelAndView.setViewName("/login/batchConfig.html");
		return modelAndView;
	}

	@RequestMapping(value = "/emailConfig", method = RequestMethod.GET)
	public ModelAndView emailConfig(HttpServletRequest request, Model model) throws SchedulerException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/login/emailConfig.html");
		return modelAndView;
	}

	@ModelAttribute("emailBean")
	public EmailBean emailRegistration() {
		return new EmailBean();
	}

	// @RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST)
	@RequestMapping(value = "/emailConfigButt", method = RequestMethod.POST, params = "action=addEmail")
	public ModelAndView emailConfigPost(HttpServletRequest request, Model model,
			@ModelAttribute("emailBean") @Valid EmailBean emailBean)
			throws SchedulerException, FileNotFoundException, IOException {

		ModelAndView modelAndView = new ModelAndView();
		String sql = "insert into EMAIL_RECIPIENT values (email_seq.nextVal, '" + emailBean.getEmaillAddress() + "','"
				+ emailBean.getFirstName() + " " + emailBean.getLastName() + "')";
		;
		H2DatabaseLuncher.executeStatementInsertAndTruncate(sql);
		modelAndView.setViewName("/login/emailConfig.html");

		return modelAndView;
	}

	@RequestMapping(value = "/reportConfig", method = RequestMethod.GET)
	public ModelAndView reportConfig(HttpServletRequest request, ReportTypeBean reportType, Model model)
			throws SchedulerException {
		ModelAndView modelAndView = new ModelAndView();
		List<ReportTypeBean> reportTypeList = H2DatabaseLuncher.getReportType();

		modelAndView.addObject("bean");
		// model.addAttribute("reportTypeList");
		modelAndView.addObject("tableDataList", H2DatabaseLuncher.tableDataList("reportReport"));
		modelAndView.addObject("reportTypeList", reportTypeList);
		modelAndView.setViewName("/login/reportConfig.html");
		return modelAndView;
	}

	// @RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST)
	@RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST, params = "action=pause")
	public ModelAndView pause(HttpServletRequest request, Model model)
			throws SchedulerException, FileNotFoundException, IOException {

		scheduleAutomationUtilityBatch.pauseAlltriggers();
		System.out.println("Pause Pressed");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("tableDataList", H2DatabaseLuncher.tableDataList("batchReport"));
		modelAndView.setViewName("/login/batchConfig.html");

		return modelAndView;
	}

	// @RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST)
	@RequestMapping(value = "/reportConfigButt", method = RequestMethod.POST, params = "action=pause")
	public ModelAndView pauseReport(HttpServletRequest request, Model model)
			throws SchedulerException, FileNotFoundException, IOException {

		scheduleAutomationUtilityReport.pauseAlltriggers();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("tableDataList", H2DatabaseLuncher.tableDataList("reportReport"));
		modelAndView.setViewName("/login/reportConfig.html");
		return modelAndView;
	}

	@RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST, params = "action=resume")
	public ModelAndView resume(HttpServletRequest request, Model model)
			throws SchedulerException, FileNotFoundException, IOException {
		scheduleAutomationUtilityBatch.resumeAllTriggers();
		System.out.println("Resume Pressed");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("tableDataList", H2DatabaseLuncher.tableDataList("batchReport"));
		modelAndView.setViewName("/login/batchConfig.html");

		return modelAndView;
	}

	@RequestMapping(value = "/reportConfigButt", method = RequestMethod.POST, params = "action=resume")
	public ModelAndView resumeReport(HttpServletRequest request, Model model)
			throws SchedulerException, FileNotFoundException, IOException {
		scheduleAutomationUtilityReport.resumeAllTriggers();
		System.out.println("Resume Pressed");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("tableDataList", H2DatabaseLuncher.tableDataList("reportReport"));
		modelAndView.setViewName("/login/reportConfig.html");
		return modelAndView;
	}

	@RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST, params = "action=submitCalender")
	public ModelAndView submitCalender(HttpServletRequest request, SessionStatus status)
			throws SchedulerException, ParseException, NumberFormatException, FileNotFoundException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		String calendarDate = request.getParameter("calenderName");
		String batchTypeSelector = request.getParameter("batchTypeSelector");
		String[] dayMonth = DateUtility.convertFormDateAndTimeParamToMonthAndDayStringArray(calendarDate);

		System.out.println(":" + calendarDate);
		// modelAndView.addObject("batchTypeList", batchBean);

		handleReplaceCronDateTime(dayMonth, Integer.valueOf(batchTypeSelector), calendarDate, "batch");

		modelAndView.addObject("batchTypeList");
		modelAndView.addObject("tableDataList", H2DatabaseLuncher.tableDataList("batchReport"));
		modelAndView.setViewName("/login/batchConfig.html");
		status.isComplete();
		return modelAndView;
	}

	@RequestMapping(value = "/reportConfigButt", method = RequestMethod.POST, params = "action=submitCalenderReport")
	public ModelAndView submitCalenderReport(HttpServletRequest request, SessionStatus status)
			throws SchedulerException, ParseException, NumberFormatException, FileNotFoundException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		String calendarDate = request.getParameter("calenderName");
		String batchTypeSelector = request.getParameter("reportTypeSelector");
		String[] dayMonth = DateUtility.convertFormDateAndTimeParamToMonthAndDayStringArray(calendarDate);

		// modelAndView.addObject("batchTypeList", batchBean);

		handleReplaceCronDateTime(dayMonth, Integer.valueOf(batchTypeSelector), calendarDate, "report");

		modelAndView.addObject("reportTypeList");
		modelAndView.addObject("tableDataList", H2DatabaseLuncher.tableDataList("reportReport"));
		modelAndView.setViewName("/login/reportConfig.html");
		status.isComplete();
		return modelAndView;
	}

	public boolean isChecked() {

		return false;
	}

	@RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST, params = "action=submitSinglePauseResume")
	public ModelAndView submitSinglePauseResume(HttpServletRequest request, SessionStatus status, Model model)
			throws SchedulerException, ParseException, FileNotFoundException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		String calendarDate = request.getParameter("calenderName");
		int batchTypeSelector = Integer.valueOf(request.getParameter("batchTypeSelector"));
		boolean myBooleanVariable = request.getParameter("checkboxVal") != null;

		// if myBooleanVariable == true, then the single batch has been pause and if
		// false the single batch is resume.
		if (myBooleanVariable == true) {
			// pause single batch
			scheduleAutomationUtilityBatch
					.pauseSingleBatch(ScheduleConstantClass.resolveBatchCodeToBatchName(batchTypeSelector));
		} else if (myBooleanVariable == false) {
			// resume single batch
			scheduleAutomationUtilityBatch
					.resumeSingleBatch(ScheduleConstantClass.resolveBatchCodeToBatchName(batchTypeSelector));

		}
		modelAndView.addObject("tableDataList", H2DatabaseLuncher.tableDataList("batchReport"));
		modelAndView.setViewName("/login/batchConfig.html");
		return modelAndView;
	}

	@RequestMapping(value = "/reportConfigButt", method = RequestMethod.POST, params = "action=submitSinglePauseResumeReport")
	public ModelAndView submitSinglePauseResumeReport(HttpServletRequest request, SessionStatus status, Model model)
			throws SchedulerException, ParseException, FileNotFoundException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		String calendarDate = request.getParameter("calenderName");
		int reportTypeSelector = Integer.valueOf(request.getParameter("reportTypeSelector"));
		boolean myBooleanVariable = request.getParameter("checkboxVal") != null;

		// if myBooleanVariable == true, then the single batch has been pause and if
		// false the single batch is resume.
		if (myBooleanVariable == true) {
			// pause single batch
			scheduleAutomationUtilityReport
					.pauseSingleBatch(ScheduleConstantClass.resolveBatchCodeToBatchName(reportTypeSelector));
		} else if (myBooleanVariable == false) {
			// resume single batch
			scheduleAutomationUtilityReport
					.resumeSingleBatch(ScheduleConstantClass.resolveBatchCodeToBatchName(reportTypeSelector));

		}
		modelAndView.addObject("tableDataList", H2DatabaseLuncher.tableDataList("reportReport"));
		modelAndView.setViewName("/login/reportConfig.html");
		return modelAndView;
	}

	public String handleReplaceCronDateTime(String[] paramDayMonth, int batchTypeId, String calendarDate,
			String operationType) throws FileNotFoundException, SchedulerException, IOException {

		String sql = "";
		String newTime = "";
		BatchScheduleTime batchScheduleTime = new BatchScheduleTime();
		ReportScheduleTime reportScheduleTime = new ReportScheduleTime();
		String[] splitCronTime = null;
		String[] splitCronTimeReport = null;

		if (operationType.contains("batch")) {
			batchScheduleTime = H2DatabaseLuncher.getScheduleTimeByCondition(batchTypeId);
			splitCronTime = batchScheduleTime.getScheduleTimeCron().split(" ");
		} else if (operationType.contains("report")) {
			reportScheduleTime = H2DatabaseLuncher.getScheduleTimeByConditionReport(batchTypeId);
			splitCronTimeReport = reportScheduleTime.getScheduleTimeCron().split(" ");
		}

		String[] splitCronTimeTwo = splitCronTime;
		// index 0 is minute while index 1 is hour
		String[] minuteAndHour = DateUtility.convertFormDateAndTimeParamToTime(calendarDate);
		String hour = minuteAndHour[0];
		String minute = minuteAndHour[1];

		String[] dayYearMonth = DateUtility.convertFormDateAndTimeParamToMonthAndDayStringArray(calendarDate);
		String day = dayYearMonth[0];

		StringBuilder b = new StringBuilder();
		StringBuilder c = new StringBuilder();
		StringBuilder a = new StringBuilder();

		if (operationType.contains("batch")) {
			// replace minute and hour
			splitCronTime[1] = minute;
			splitCronTime[2] = hour;
			splitCronTime[3] = day;

			splitCronTimeTwo[1] = minute;
			splitCronTimeTwo[2] = hour;

			// Rearrange the cron date into cron expression for Bill Run One
			for (int i = 0; i < splitCronTime.length; i++) {

				b.append(splitCronTime[i] + " ");
			}

			// Rearrange the cron date into cron expression for Bill Run Two

			for (int i = 0; i < splitCronTimeTwo.length; i++) {

				c.append(splitCronTimeTwo[i] + " ");
			}

		} else if (operationType.contains("report")) {

			splitCronTimeReport[5] = new SimpleDateFormat("EE").format(new Date(calendarDate)).toString();
			splitCronTimeReport[1] = minute;
			splitCronTimeReport[2] = hour;

			// Rearrange for report
			for (int i = 0; i < splitCronTimeReport.length; i++) {

				a.append(splitCronTimeReport[i] + " ");
			}

		}

		// controls the mechanism for changing the time. for bill run one only the
		// time, day and month will be changed
		// while the bill run two only the time will be effected, becuase the bill run
		// two runs once last friday of the month, so only the time to run the batches
		// shld be effected.

		if (ScheduleConstantClass.dummyBatch == batchTypeId || ScheduleConstantClass.autoFinanceBatchOne == batchTypeId
				|| ScheduleConstantClass.genericFeesBatchOne == batchTypeId
				|| ScheduleConstantClass.financialBatchOne == batchTypeId
				|| ScheduleConstantClass.coverageBatchBatchOne == batchTypeId
				|| ScheduleConstantClass.autoFinanceBatchOne == batchTypeId
				|| ScheduleConstantClass.reschedulingBatchOne == batchTypeId
				|| ScheduleConstantClass.issuingBatchOne == batchTypeId
				|| ScheduleConstantClass.regularBatchOne == batchTypeId) {
			newTime = b.toString().trim();
			sql = "update BATCH_SCHEDULE_TIME set schedule_date =" + "'" + newTime + "'" + " where batch_type ="
					+ batchTypeId;

		} else if (ScheduleConstantClass.autoFinanceBatchTwo == batchTypeId
				|| ScheduleConstantClass.genericFeesBatchTwo == batchTypeId
				|| ScheduleConstantClass.coverageBatchBatchTwo == batchTypeId
				|| ScheduleConstantClass.autoFinanceBatchTwo == batchTypeId
				|| ScheduleConstantClass.reschedulingBatchTwo == batchTypeId
				|| ScheduleConstantClass.issuingBatchTwo == batchTypeId
				|| ScheduleConstantClass.regularBatchTwo == batchTypeId
				|| ScheduleConstantClass.financialBatchTwo == batchTypeId) {
			newTime = c.toString().trim();
			sql = "update BATCH_SCHEDULE_TIME set schedule_date =" + "'" + newTime + "'" + " where batch_type ="
					+ batchTypeId;
		}
		if (ScheduleConstantClass.acutrialWeekly == batchTypeId || ScheduleConstantClass.lifeCoversWeekly == batchTypeId
				|| ScheduleConstantClass.fundSplitsWeekly == batchTypeId
				|| ScheduleConstantClass.policyBeneficiaries == batchTypeId
				|| ScheduleConstantClass.policyHolders == batchTypeId
				|| ScheduleConstantClass.policyPayers == batchTypeId
				|| ScheduleConstantClass.thirdPartyActiveAddress == batchTypeId
				|| ScheduleConstantClass.termActurialExtractCFI == batchTypeId
				|| ScheduleConstantClass.termActurialExtractDeact == batchTypeId
				|| ScheduleConstantClass.termActurialExtractDeath == batchTypeId
				|| ScheduleConstantClass.termActurialExtractRedemp == batchTypeId
				|| ScheduleConstantClass.termActurialExtractSurren == batchTypeId
				|| ScheduleConstantClass.termActurialExtractTerm == batchTypeId) {
			newTime = a.toString().trim();
			sql = "update REPORT_SCHEDULE_TIME set schedule_date =" + "'" + newTime + "'" + " where report_type ="
					+ batchTypeId;

		}

		H2DatabaseLuncher.executeStatementInsertAndTruncate(sql);

		ScheduleAutomationUtility scheduleAutomation = new ScheduleAutomationUtility(operationType);
		Trigger Oldtrigger = scheduleAutomation
				.getTriggerByJobGroupName(ScheduleConstantClass.resolveBatchCodeToBatchName(batchTypeId));
		Scheduler scheduler = scheduleAutomation.getScheduleByInstanceName();
		Trigger newTrigger = null;
		newTrigger = TriggerBuilder.newTrigger()
				.withIdentity(Oldtrigger.getKey().getName(), Oldtrigger.getKey().getGroup())
				.withSchedule(CronScheduleBuilder.cronSchedule(newTime).withMisfireHandlingInstructionDoNothing())
				.build();

		if (scheduler.checkExists(Oldtrigger.getKey())) {
			scheduler.rescheduleJob(Oldtrigger.getKey(), newTrigger);
		}

		return b.toString().trim();
	}

}
