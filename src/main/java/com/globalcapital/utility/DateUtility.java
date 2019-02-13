package com.globalcapital.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtility {

	public static String DateNowToString() {
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = dateFormat.format(date);
		return strDate;
	}

	public static String DataParamToString(Date date) {
		// date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = dateFormat.format(date);
		return strDate;

	}

	public static String DateToStringReduceMonthByone() {
		String[] arryDate = DateNowToString().split("");
		StringBuilder result = new StringBuilder(DateNowToString());
		// int monthFirstCha = Integer.valueOf(arryDate[03]);
		int monthSecCha = Integer.valueOf(arryDate[04]);
		int reduceMonthByOne = monthSecCha - 1;
		result.setCharAt(4, String.valueOf(reduceMonthByOne).charAt(0));

		return result.toString();
	}

	public static String DateToStringReduceMonthByoneDateParam(Date date) {
		String[] arryDate = DataParamToString(date).split("");
		StringBuilder result = new StringBuilder(DataParamToString(date));
		// int monthFirstCha = Integer.valueOf(arryDate[03]);
		int monthSecCha = Integer.valueOf(arryDate[04]);
		int reduceMonthByOne = monthSecCha - 1;
		result.setCharAt(4, String.valueOf(reduceMonthByOne).charAt(0));

		return result.toString();
	}

	public static String fourteenthOfMonth() {

		LocalDate todaydate = LocalDate.now();

		String value = DataParamToString(java.sql.Date.valueOf(todaydate.withDayOfMonth(14)));

		return value;
	}

	public static String firstOftheMonthString() {

		LocalDate todaydate = LocalDate.now();

		String value = DataParamToString(java.sql.Date.valueOf(todaydate.withDayOfMonth(1)));

		return value;
	}

	

	public static String fifttheenthOftheMonthString() {

		LocalDate todaydate = LocalDate.now();

		String value = DataParamToString(java.sql.Date.valueOf(todaydate.withDayOfMonth(15)));

		return value;
	}

	public static String firstOfMonthReduceMonthByOneString() {
		LocalDate todaydate = LocalDate.now();
		// Date date = java.sql.Date.valueOf(todaydate.withDayOfMonth(1));
		String value = DateToStringReduceMonthByoneDateParam(java.sql.Date.valueOf(todaydate.withDayOfMonth(1)));

		return value;
	}

	public static String lastDayOfTheMonth() {
		LocalDate lastDayofCurrentMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
		return DataParamToString(java.sql.Date.valueOf(lastDayofCurrentMonth));
	}

	public static String fifteenthDayOfMonthReduceMonthByOneString() {
		LocalDate todaydate = LocalDate.now();
		// Date date = java.sql.Date.valueOf(todaydate.withDayOfMonth(1));
		String value = DateToStringReduceMonthByoneDateParam(java.sql.Date.valueOf(todaydate.withDayOfMonth(15)));

		return value;
	}

}
