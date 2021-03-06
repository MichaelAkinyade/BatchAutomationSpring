package com.globalcapital.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.lang.Nullable;

public class CheckLogFileForMessage {

	public boolean batchFileBatchCompleted() {

		return false;
	}

	public static boolean hasCode99(String source) {
		boolean retVal = false;

		if (source != null) {
			//String pattern = "\\b console returns 99 code \\b"; ".*console returns 99 code.*"
			String pattern = ".*console returns 99 code.*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(source);
			if (m.find() == true)
				return retVal = true;
		}

		return retVal;
	}
	
	public static boolean hasReportErrorCode(String source) {
		boolean retVal = false;

		if (source != null) {
			//String pattern = "\\b console returns 99 code \\b"; ".*console returns 99 code.*"
			String pattern = ".*Done 11.*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(source);
			if (m.find() == true)
				return retVal = true;
		}

		return retVal;
	}
	
	
	
	public static boolean hasReportErrorMessage(String source) {
		boolean retVal = false;

		if (source != null) {
			String pattern = ".*Could not send Message.*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(source);
			if (m.find() == true)
				return retVal = true;
		}

		return retVal;
	}
	
	
	public static boolean hasReportErrorCode17(String source) {
		boolean retVal = false;

		if (source != null) {
			String pattern = ".*Done 17.*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(source);
			if (m.find() == true)
				return retVal = true;
		}

		return retVal;
	} 
	
	public static boolean hasReportErrorConnectRefused(String source) {
		boolean retVal = false;

		if (source != null) {
			String pattern = ".*Connection refused: connect*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(source);
			if (m.find() == true)
				return retVal = true;
		}

		return retVal;
	} 
	
	public static boolean hasCode3(String source) {
		boolean retVal = false;

		if (source != null) {
			String pattern = ".*console returns 3 code.*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(source);
			if (m.find() == true)
				return retVal = true;
		}

		return retVal;
	}
	
	public static boolean hasCode4(String source) {
		boolean retVal = false;

		if (source != null) {
			String pattern = ".*console returns 4 code.*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(source);
			if (m.find() == true)
				return retVal = true;
		}

		return retVal;
	}
	
	public static boolean hasCode2(String source) {
		boolean retVal = false;

		if (source != null) {
			String pattern = ".*console returns 2 code.*";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(source);
			if (m.find() == true)
				return retVal = true;
		}

		return retVal;
	}
	
}
