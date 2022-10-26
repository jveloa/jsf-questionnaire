package cu.edu.mes.sigenu.training.web.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static Date stringToDate(String date, boolean withTime)
			throws ParseException {
			SimpleDateFormat sdf = null;
			if (withTime)
				sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			else
				sdf = new SimpleDateFormat("dd/MM/yyyy");

			return sdf.parse(date);
		}
	
	public static String dateToString(Date date, boolean withTime) {
		if (date == null)
			return "";
		String sDate = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int month = calendar.get(Calendar.MONTH) + 1;
		String sMonth = (new Integer(month)).toString();
		if (month < 10)
			sMonth = "0" + sMonth;

		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		String sDayOfMonth = (new Integer(dayOfMonth)).toString();
		if (dayOfMonth < 10)
			sDayOfMonth = "0" + sDayOfMonth;

		String sYear = (new Integer(calendar.get(Calendar.YEAR))).toString();

		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		String sHour = (new Integer(hour)).toString();
		if (hour < 10)
			sHour = "0" + sHour;

		int minute = calendar.get(Calendar.MINUTE);
		String sMinute = (new Integer(minute)).toString();
		if (minute < 10)
			sMinute = "0" + sMinute;

		sDate = sDayOfMonth + "/" + sMonth + "/" + sYear;
		if (withTime)
			sDate += " " + sHour + ":" + sMinute;

		return sDate;
	}
	
	public static String timestampToString(Timestamp date, boolean withTime) {
		return dateToString(new Date(date.getTime()), withTime);
	}
}
