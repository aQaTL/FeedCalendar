package com.aqatl.feedcalendar.calendar;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * @author Maciej on 2017-07-04.
 */
public class Calendar {
	private URL feedURL;
	private SyndFeed feed;

	public Calendar(URL feedURL) {
		this.feedURL = feedURL;
	}

	public void parseFeed() throws IOException, FeedException {
		this.feed = new SyndFeedInput().build(new XmlReader(feedURL));
	}

	public void processFeed() {
		if (feed == null) {
			try {
				parseFeed();
			} catch (IOException | FeedException e) {
				e.printStackTrace();
			}
		}

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
