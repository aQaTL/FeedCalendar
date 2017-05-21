package com.aqatl.feedcalendar.calendar;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Maciej on 2017-05-07.
 */
public class Calendar {

	public static void main(String[] args) throws IOException, FeedException {
		if (args.length != 1) {
			System.err.println("Usage: \n" +
					"java -jar FeedCalendar.jar <feed url>");
			System.exit(1);
		}
		printFeed(createFeed(args[0]), System.out);
	}

	public static SyndFeed createFeed(String url) throws IOException, FeedException {
		URL feedURL = new URL(url);
		SyndFeed feed = new SyndFeedInput().build(new XmlReader(feedURL));
		return feed;
	}

	public static void printFeed(SyndFeed feed, PrintStream out)	{
		feed.getEntries().
				parallelStream().
				map(SyndEntry::getPublishedDate).
				collect(Collectors.groupingBy(Date::getMonth)).
				forEach((month, dates) -> printMonth(dates, out));
	}

	private static void printMonth(List<Date> dates, PrintStream out) {
		List<Integer> days = dates.stream().map(Date::getDate).collect(Collectors.toList());
		// construct d as current date
		GregorianCalendar d = new GregorianCalendar();
		d.setTime(dates.get(0));

		int today = d.get(java.util.Calendar.DAY_OF_MONTH);
		int month = d.get(java.util.Calendar.MONTH);

		// set d to start date of the month
		d.set(java.util.Calendar.DAY_OF_MONTH, 1);

		int weekday = d.get(java.util.Calendar.DAY_OF_WEEK);

		// get first day of week (Sunday in the U.S.)
		int firstDayOfWeek = d.getFirstDayOfWeek();

		// determine the required indentation for the first line
		int indent = 0;
		while (weekday != firstDayOfWeek) {
			indent++;
			d.add(java.util.Calendar.DAY_OF_MONTH, -1);
			weekday = d.get(java.util.Calendar.DAY_OF_WEEK);
		}

		String monthName = Month.of(month + 1).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault(Locale.Category.FORMAT));
		int spacing = 14 + (monthName.length() / 2);
		out.printf("%" + spacing + "s\n", monthName);

		// printMonth weekday names
		String[] weekdayNames = new DateFormatSymbols().getShortWeekdays();
		do {
			out.printf("%4s", weekdayNames[weekday]);
			d.add(java.util.Calendar.DAY_OF_MONTH, 1);
			weekday = d.get(java.util.Calendar.DAY_OF_WEEK);
		}
		while (weekday != firstDayOfWeek);
		out.println();

		for (int i = 1; i <= indent; i++)
			out.print("    ");

		d.set(java.util.Calendar.DAY_OF_MONTH, 1);
		do {
			// printMonth day
			int day = d.get(java.util.Calendar.DAY_OF_MONTH);
			out.printf("%3d", day);

			// mark current day with *
			if (days.contains(day)) out.print("*");
			else out.print(" ");

			// advance d to the next day
			d.add(java.util.Calendar.DAY_OF_MONTH, 1);
			weekday = d.get(java.util.Calendar.DAY_OF_WEEK);

			// start a new line at the start of the week
			if (weekday == firstDayOfWeek) out.println();
		}
		while (d.get(java.util.Calendar.MONTH) == month);
		// the loop exits when d is day 1 of the next month

		// printMonth final end of line if necessary
		if (weekday != firstDayOfWeek) out.println();
	}
}

