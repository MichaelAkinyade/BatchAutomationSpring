package com.globalcapital.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

public class DateToCronExpression {
	private final Date mDate;
	private final Calendar mCal;
	private final String mSeconds = "0";
	private final String mDaysOfWeek = "?";

	private String mMins;
	private String mHours;
	private String mDaysOfMonth;
	private String mMonths;
	private String mYears;

	public DateToCronExpression(Date pDate) {
		this.mDate = pDate;
		mCal = Calendar.getInstance();
		this.generateCronExpression();
	}

	public void generateCronExpression() {
		mCal.setTime(mDate);

		String hours = String.valueOf(mCal.get(Calendar.HOUR_OF_DAY));
		this.mHours = hours;

		String mins = String.valueOf(mCal.get(Calendar.MINUTE));
		this.mMins = mins;

		String days = String.valueOf(mCal.get(Calendar.DAY_OF_MONTH));
		this.mDaysOfMonth = days;

		String months = new java.text.SimpleDateFormat("MM").format(mCal.getTime());
		this.mMonths = months;

		String years = String.valueOf(mCal.get(Calendar.YEAR));
		this.mYears = years;

	}

	public Date getDate() {
		return mDate;
	}

	public String getSeconds() {
		return mSeconds;
	}

	public String getMins() {
		return mMins;
	}

	public String getDaysOfWeek() {
		return mDaysOfWeek;
	}

	public String getHours() {
		return mHours;
	}

	public String getDaysOfMonth() {
		return mDaysOfMonth;
	}

	public String getMonths() {
		return mMonths;
	}

	public String getYears() {
		return mYears;
	}

	public String getCronExpression(Date date) {
	 String cron = "";
		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dt = dateFormat.format(date);

			Date cronDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt);

			DateToCronExpression calHelper = new DateToCronExpression(cronDate);
			 cron = calHelper.getSeconds() + " " + calHelper.getMins() + " " + calHelper.getHours() + " "
					+ calHelper.getDaysOfMonth() + " " + calHelper.getMonths() + " " + calHelper.getDaysOfWeek() + " "
					+ calHelper.getYears();
			Logger.getLogger("Cron Expression " + cron);
		} catch (Exception e) {

		}
		return cron;
	}



}
