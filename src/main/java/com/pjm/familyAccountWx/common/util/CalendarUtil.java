package com.pjm.familyAccountWx.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.*;

/**
 * 日期工具类
 *
 * @author Brain
 */
public class CalendarUtil {

	// 获取当前年
	public static String getYear() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return year + "";
	}

	// 获取当前月
	public static String getMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		month = month + 1;
		if (month > 12) {
			return "12";
		}
		return month + "";
	}

	// 获取当前日
	public static String getDay() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.DAY_OF_MONTH);
		return month + "";
	}
}
