package com.lukazakrajsek.timeago;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.res.Resources;

public abstract class TimeAgo {

	public static String timeAgo(Context context, Date date) {
		return TimeAgo.timeAgo(context, date.getTime());
	}

	public static String timeAgo(Context context, String strDate, String dateFormat) {
		//formato = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		//"Thu Jul 11 12:40:18 GMT-03:00 2013"
		//"EE MMM dd HH:mm:ss z YYYY"
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date;
		try {
			date = (Date) format.parse(strDate);
		} catch (ParseException e) {
			date = Calendar.getInstance().getTime();
		}
		return TimeAgo.timeAgo(context, date);
	}
	
	public static String timeAgo(Context context, long millis) {
		long diff = new Date().getTime() - millis;

		Resources r = context.getResources();

		String prefix = r.getString(R.string.time_ago_prefix);
		String suffix = r.getString(R.string.time_ago_suffix);

		double seconds = Math.abs(diff) / 1000;
		double minutes = seconds / 60;
		double hours = minutes / 60;
		double days = hours / 24;
		double years = days / 365;

		String words;

		if (seconds < 45) {
			words = r.getString(R.string.time_ago_seconds, Math.round(seconds));
		} else if (seconds < 90) {
			words = r.getString(R.string.time_ago_minute, 1);
		} else if (minutes < 45) {
			words = r.getString(R.string.time_ago_minutes, Math.round(minutes));
		} else if (minutes < 90) {
			words = r.getString(R.string.time_ago_hour, 1);
		} else if (hours < 24) {
			words = r.getString(R.string.time_ago_hours, Math.round(hours));
		} else if (hours < 42) {
			words = r.getString(R.string.time_ago_day, 1);
		} else if (days < 30) {
			words = r.getString(R.string.time_ago_days, Math.round(days));
		} else if (days < 45) {
			words = r.getString(R.string.time_ago_month, 1);
		} else if (days < 365) {
			words = r.getString(R.string.time_ago_months, Math.round(days / 30));
		} else if (years < 1.5) {
			words = r.getString(R.string.time_ago_year, 1);
		} else {
			words = r.getString(R.string.time_ago_years, Math.round(years));
		}
		StringBuilder sb = new StringBuilder();

		if (prefix != null && prefix.length() > 0) {
			sb.append(prefix).append(" ");
		}

		sb.append(words);

		if (suffix != null && suffix.length() > 0) {
			sb.append(" ").append(suffix);
		}

		return sb.toString().trim();
	}
}
