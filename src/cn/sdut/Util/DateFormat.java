package cn.sdut.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	public static String YMDDate(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd::HH:mm:ss");
		return sdf.format(date);
	}
}
