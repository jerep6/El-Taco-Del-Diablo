package com.eltacodeldiablo.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date addDay(Date d, int nbDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_YEAR, nbDay);
		return c.getTime();
	}

	public static Boolean equalsYMD(Date d1, Date d2) {
		return midnight(d1).equals(midnight(d2));
	}

	public static Date midnight(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}
}
