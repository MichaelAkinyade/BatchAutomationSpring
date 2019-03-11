package com.globalcapital.pack.bean;

public class ReportScheduleTime {
	public int id;
	public int reportTypeId;
	public String ScheduleTimeCron;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReportTypeId() {
		return reportTypeId;
	}
	public void setReportTypeId(int reportTypeId) {
		this.reportTypeId = reportTypeId;
	}
	public String getScheduleTimeCron() {
		return ScheduleTimeCron;
	}
	public void setScheduleTimeCron(String scheduleTimeCron) {
		ScheduleTimeCron = scheduleTimeCron;
	}
	
	
	

}
