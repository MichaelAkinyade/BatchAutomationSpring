package com.globalcapital.pack.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.quartz.SchedulerException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.globalcapital.database.datasource.H2DatabaseLuncher;
import com.globalcapital.database.datasource.MyDataSourceFactory;
import com.globalcapital.pack.bean.BatchScheduleTime;
import com.globalcapital.pack.bean.BatchTypeBean;
import com.globalcapital.pack.bean.SessionBean;
import com.globalcapital.pack.database.entity.Users;
import com.globalcapital.pack.database.serviceInterface.MyDataSourceFactoryService;
import com.globalcapital.pack.model.User;
import com.globalcapital.pack.schedule.utility.ScheduleAutomationUtility;
import com.globalcapital.pack.schedule.utility.ScheduleConstantClass;
import com.globalcapital.utility.DateUtility;

@Controller
//@SessionAttributes("bean")
@SessionAttributes(value = { "bean", "batchTypeList" })
public class LoginController {

	MyDataSourceFactoryService myDataSourceFactoryService;
	ScheduleAutomationUtility scheduleAutomationUtility = new ScheduleAutomationUtility();

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
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		// User userExists = userService.findUserByEmail(user.getUsername());
		/*
		 * if (userExists != null) { bindingResult .rejectValue("username",
		 * "error.user",
		 * "There is already a user registered with the username provided"); } if
		 * (bindingResult.hasErrors()) { modelAndView.setViewName("registration"); }
		 * else { //userService.saveUser(user); modelAndView.addObject("successMessage",
		 * "User has been registered successfully"); modelAndView.addObject("user", new
		 * User()); modelAndView.setViewName("registration");
		 * 
		 * }
		 */
		return modelAndView;
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, Model model) throws SchedulerException {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user = H2DatabaseLuncher.getUserByUsername(auth.getName());
		ScheduleAutomationUtility scheduler = new ScheduleAutomationUtility();
		SessionBean sessionBean = new SessionBean();
		sessionBean.setFirstName(user.getFirstName());
		sessionBean.setLastName(user.getLastName());
		sessionBean.setFullName(user.getFirstName() + " " + user.getLastName());
		sessionBean.setLoggedOnName(user.getUserName());
		sessionBean.setNumberOfRunningBatchs(scheduler.getNumberOfScheduledBatchJobs());
		sessionBean.setUserName(auth.getName());
		// Save the bean to the session
		// request.setAttribute("bean", sessionBean);
		model.addAttribute("bean", sessionBean);
		modelAndView.addObject("numberJobs", sessionBean.getNumberOfRunningBatchs());
		scheduler.getListOfTriggersAndJob();
		modelAndView.addObject("user", user);

		modelAndView.addObject("adminMessage", MyDataSourceFactory.getRunningQuery());
		modelAndView.setViewName("/login/index3.html");
		return modelAndView;
	}

	@RequestMapping(value = "/batchConfig", method = RequestMethod.GET)
	public ModelAndView batchConfig(HttpServletRequest request, BatchTypeBean batchType, Model model)
			throws SchedulerException {
		ModelAndView modelAndView = new ModelAndView();
		List<BatchTypeBean> batchTypeList = H2DatabaseLuncher.getBatchType();

		modelAndView.addObject("bean");
		model.addAttribute("batchTypeList");
		modelAndView.addObject("batchTypeList", batchTypeList);
		modelAndView.setViewName("/login/batchConfig.html");
		return modelAndView;
	}

	// @RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST)
	@RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST, params = "action=pause")
	public ModelAndView pause(HttpServletRequest request, Model model) throws SchedulerException {

		scheduleAutomationUtility.pauseAlltriggers();
		System.out.println("Pause Pressed");

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("/login/batchConfig.html");

		return modelAndView;
	}

	@RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST, params = "action=resume")
	public ModelAndView resume(HttpServletRequest request, Model model) throws SchedulerException {
		scheduleAutomationUtility.resumeAllTriggers();
		System.out.println("Resume Pressed");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/login/batchConfig.html");

		return modelAndView;
	}

	@RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST, params = "action=submitCalender")
	public ModelAndView submitCalender(HttpServletRequest request, SessionStatus status)
			throws SchedulerException, ParseException {
		ModelAndView modelAndView = new ModelAndView();
		String calendarDate = request.getParameter("calenderName");
		String batchTypeSelector = request.getParameter("batchTypeSelector");
		String[] dayMonth = DateUtility.convertFormDateAndTimeParamToMonthAndDayStringArray(calendarDate);

		System.out.println("calender parameter is:" + calendarDate);
		// modelAndView.addObject("batchTypeList", batchBean);

		handleReplaceCronDateTime(dayMonth, Integer.valueOf(batchTypeSelector), calendarDate);

		modelAndView.addObject("batchTypeList");

		modelAndView.setViewName("/login/batchConfig.html");
		status.isComplete();
		return modelAndView;
	}

	public boolean isChecked() {

		return false;
	}

	@RequestMapping(value = "/batchConfigButt", method = RequestMethod.POST, params = "action=submitSinglePauseResume")
	public ModelAndView submitSinglePauseResume(HttpServletRequest request, SessionStatus status, Model model)
			throws SchedulerException, ParseException {
		ModelAndView modelAndView = new ModelAndView();
		String calendarDate = request.getParameter("calenderName");
		int batchTypeSelector = Integer.valueOf(request.getParameter("batchTypeSelector"));
		boolean myBooleanVariable = request.getParameter("checkboxVal") != null;

		// if myBooleanVariable == true, then the single batch has been pause and if
		// false the single batch is resume.
		if (myBooleanVariable == true) {
			// pause single batch
			
			
			scheduleAutomationUtility.pauseSingleBatch(ScheduleConstantClass.resolveBatchCodeToBatchName(batchTypeSelector));
		} else if (myBooleanVariable == false) {
			// resume single batch
			scheduleAutomationUtility.resumeSingleBatch(ScheduleConstantClass.resolveBatchCodeToBatchName(batchTypeSelector));

		}
		modelAndView.setViewName("/login/batchConfig.html");
		return modelAndView;
	}

	public String handleReplaceCronDateTime(String[] paramDayMonth, int batchTypeId, String calendarDate) {

		String sql = "";
		BatchScheduleTime batchScheduleTime = H2DatabaseLuncher.getScheduleTimeByCondition(batchTypeId);
		String[] splitCronTime = batchScheduleTime.getScheduleTimeCron().split(" ");
		String[] splitCronTimeTwo = splitCronTime;
		// index 0 is minute while index 1 is hour
		String[] minuteAndHour = DateUtility.convertFormDateAndTimeParamToTime(calendarDate);
		String hour = minuteAndHour[0];
		String minute = minuteAndHour[1];

		String[] dayYearMonth = DateUtility.convertFormDateAndTimeParamToMonthAndDayStringArray(calendarDate);
		String day = dayYearMonth[0];

		StringBuilder b = new StringBuilder();
		StringBuilder c = new StringBuilder();
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

		// controls the mechanism for changing the time. for bill run one only the
		// time, day and month will be changed
		// while the bill run two only the time will be effected, becuase the bill run
		// two runs once last friday of the month, so only the time to run the batches
		// shld be effected.

		if (ScheduleConstantClass.dummyBatch == batchTypeId || ScheduleConstantClass.autoFinanceBatchOne == batchTypeId
				|| ScheduleConstantClass.genericFeesBatchOne == batchTypeId
				|| ScheduleConstantClass.coverageBatchBatchOne == batchTypeId
				|| ScheduleConstantClass.autoFinanceBatchOne == batchTypeId
				|| ScheduleConstantClass.reschedulingBatchOne == batchTypeId
				|| ScheduleConstantClass.issuingBatchOne == batchTypeId
				|| ScheduleConstantClass.regularBatchOne == batchTypeId) {
			sql = "update BATCH_SCHEDULE_TIME set schedule_date =" + "'" + b.toString().trim() + "'"
					+ " where batch_type =" + batchTypeId;
		} else if (ScheduleConstantClass.autoFinanceBatchTwo == batchTypeId
				|| ScheduleConstantClass.genericFeesBatchTwo == batchTypeId
				|| ScheduleConstantClass.coverageBatchBatchTwo == batchTypeId
				|| ScheduleConstantClass.autoFinanceBatchTwo == batchTypeId
				|| ScheduleConstantClass.reschedulingBatchTwo == batchTypeId
				|| ScheduleConstantClass.issuingBatchTwo == batchTypeId
				|| ScheduleConstantClass.regularBatchTwo == batchTypeId) {
			sql = "update BATCH_SCHEDULE_TIME set schedule_date =" + "'" + c.toString().trim() + "'"
					+ " where batch_type =" + batchTypeId;
		}

		H2DatabaseLuncher.executeStatementInsertAndTruncate(sql);

		return b.toString().trim();
	}

}
