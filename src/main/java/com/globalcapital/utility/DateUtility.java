package com.globalcapital.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

	public static String DateNowToString() {
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = dateFormat.format(date);
		return strDate;
	}

	public static String DateAndTimeNowToString() {
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
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

	public static String DateFormatToString(String dateStr) {
		String strDate ="";
		Long dateConvert;
		if (Long.valueOf(dateStr) >=1) {
			dateConvert  = Long.valueOf(dateStr);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(dateConvert);
		    java.util.Date utilDate = calendar.getTime();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		strDate = dateFormat.format(utilDate);}
		else {
			
			return "0";
		}
		return strDate;

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

	// index 0 == day value; while index 1 == Month value; index 2== Year Value
	public static String[] convertFormDateAndTimeParamToMonthAndDayStringArray(String formDate) {
		String[] retVal = new String[10];

		if (formDate != null) {
			String tx = formDate.substring(0, 10);
			String month = tx.substring(0, 2);
			String day = tx.substring(3, 5);
			String year = tx.substring(6, 10);

			if ((day != null) && (month != null)) {
				retVal[0] = day;
				retVal[1] = month;
				retVal[2] = year;
			}

		} else {
			System.out.println("Month and Day cannot be Null");

		}

		return retVal;
	}

	// index 0's value is the minute while index 1's value is the hour
	public static String[] convertFormDateAndTimeParamToTime(String formDate) {

		String[] retVal = new String[19];
		int counter = 0;
		int counter2 = 0;
		boolean counterBoolen = false;

		String[] val = formDate.split(" ");
		String[] manipulateTime = val[1].split("");
		int length = manipulateTime.length - 1;

		for (int i = 0; i < manipulateTime.length; i++) {

			//Handles Hours
			if ((manipulateTime[i].equals(":"))) {

				StringBuilder b = new StringBuilder();

				// left side to the middle ":"
				for (int j = 0; j < i; j++) {

					b.append(manipulateTime[j]);
					counter++;
				}

				retVal[0] = b.toString().trim();

				// convert to 24 hours value
				if (val[2].equals("PM")&& Integer.valueOf(retVal[0])!=12) {

					String twentyFourHoursValue = String.valueOf(Integer.valueOf(retVal[0].trim()) + 12);
					retVal[0] = twentyFourHoursValue;
				}

			}
			// minute
			if (!(counter == 0) && counter >= 1 && !(manipulateTime[i].equals(":")) && counterBoolen == false) {
				StringBuilder b = new StringBuilder();
				for (counter2 = i; counter2 < manipulateTime.length; counter2++) {
					b.append(manipulateTime[counter2]);
				}

				retVal[1] = b.toString().trim();
				counterBoolen = true;
			}

		}

		return retVal;
	}

}
