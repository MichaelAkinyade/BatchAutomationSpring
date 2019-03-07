package com.globalcapital.pack.bean;

public class BatchScheduleTime {
	public int id;
	public int batchTypeId;
	public String ScheduleTimeCron;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBatchTypeId() {
		return batchTypeId;
	}
	public void setBatchTypeId(int batchTypeId) {
		this.batchTypeId = batchTypeId;
	}
	public String getScheduleTimeCron() {
		return ScheduleTimeCron;
	}
	public void setScheduleTimeCron(String scheduleTimeCron) {
		ScheduleTimeCron = scheduleTimeCron;
	}
	
	
	

}
