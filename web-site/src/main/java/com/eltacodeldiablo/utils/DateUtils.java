package com.eltacodeldiablo.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date midnight(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
}
