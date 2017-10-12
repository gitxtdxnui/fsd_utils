package com.feishidai.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String datetimeFormat = "yyyy-MM-dd HH:mm:ss";
	public static String dateFormat = "yyyy-MM-dd";

	public static String nowDateTimeToString() {
		return nowDateFormatToString(datetimeFormat);
	}

	public static String nowDateToString() {
		return nowDateFormatToString(dateFormat);
	}

	public static String nowDateFormatToString(String format) {
		return dateFormatToString(format, new Date());
	}

	public static Date dateTimeStringToDate(String date) {
		return dateFormatToDate(datetimeFormat, date);
	}

	public static Date dateStringToDate(String date) {
		return dateFormatToDate(dateFormat, date);
	}
	
	public static String dateTimeDateToString(Date date) {
		return dateFormatToString(datetimeFormat, date);
	}
	
	public static String dateDateToString(Date date) {
		return dateFormatToString(dateFormat, date);
	}

	public static String dateFormatToString(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date dateFormatToDate(String format, String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
