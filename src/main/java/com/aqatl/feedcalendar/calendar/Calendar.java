package com.aqatl.feedcalendar.calendar;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.time.Month.JANUARY;

/**
 * @author Maciej on 2017-07-04.
 */
public class Calendar {
	private URL feedURL;
	private SyndFeed feed;
	private Map<Year, int[][][]> years;

	public Calendar(URL feedURL) {
		this.feedURL = feedURL;
		years = new TreeMap<>();
	}

	public void parseFeed() {
		try {
			this.feed = new SyndFeedInput().build(new XmlReader(feedURL));
		} catch (FeedException | IOException e) {
			e.printStackTrace();
		}
	}

	public Map<Year, int[][][]> processFeed() {
		if (feed == null) {
			parseFeed();
		}

		Map<Year, List<Date>> datesByYear = feed.getEntries().stream().
				map(SyndEntry::getPublishedDate).
				collect(Collectors.groupingBy(date -> Year.of(date.getYear())));

		datesByYear.forEach((year, instants) -> years.put(year, generateYear(year.getValue() + 1900)));
		return years;
	}

	public int[][][] generateYear(int yearValue) {
		int[][][] year = new int[12][6][7];

		int firstDayOfWeek = DayOfWeek.of(1).getValue();
		LocalDate date = LocalDate.of(yearValue, JANUARY, 1);

		for (int month = 0; month < 12; month++) {
			int dayPointer = 0;

			for (int j = 0; j < date.getDayOfWeek().getValue() - firstDayOfWeek; j++) {
				dayPointer++;
			}

			for (int dayValue = 1; dayValue <= date.lengthOfMonth(); dayValue++, dayPointer++) {
				year[month][dayPointer / 7][dayPointer % 7] = dayValue;
			}

			date = date.plusMonths(1);
		}
		return year;
	}

	public void setFeedURL(URL feedURL) {
		this.feedURL = feedURL;
	}

	public SyndFeed getFeed() {
		return feed;
	}

	public static void main(String[] args) throws Exception {
		Calendar calendar = new Calendar(new URL(args[0]));
		calendar.parseFeed();
		calendar.processFeed();
	}
}
