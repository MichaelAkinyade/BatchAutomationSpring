package com.globalcapital.pack.bean;

public class SessionBean {

	public String userName;

	public String fullName;
	public String loggedOnName;
	public String firstName;
	public String lastName;
	public int numberOfRunningBatchs;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLoggedOnName() {
		return loggedOnName;
	}

	public void setLoggedOnName(String loggedOnName) {
		this.loggedOnName = loggedOnName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getNumberOfRunningBatchs() {
		return numberOfRunningBatchs;
	}

	public void setNumberOfRunningBatchs(int numberOfRunningBatchs) {
		this.numberOfRunningBatchs = numberOfRunningBatchs;
	}

	public int getNumberOfRunningReports() {
		return numberOfRunningReports;
	}

	public void setNumberOfRunningReports(int numberOfRunningReports) {
		this.numberOfRunningReports = numberOfRunningReports;
	}

	public int numberOfRunningReports;

}
