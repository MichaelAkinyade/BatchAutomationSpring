package com.globalcapital.pack.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.quartz.SchedulerException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.globalcapital.database.datasource.H2DatabaseLuncher;
import com.globalcapital.database.datasource.MyDataSourceFactory;
import com.globalcapital.pack.bean.SessionBean;
import com.globalcapital.pack.database.entity.Users;
import com.globalcapital.pack.database.serviceInterface.MyDataSourceFactoryService;
import com.globalcapital.pack.model.User;
import com.globalcapital.pack.schedule.utility.ScheduleAutomationUtility;

@Controller
@SessionAttributes("bean")
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
	public ModelAndView batchConfig(HttpServletRequest request) throws SchedulerException {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("bean");

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

}
