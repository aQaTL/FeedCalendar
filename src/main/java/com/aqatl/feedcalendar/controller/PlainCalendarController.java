package com.aqatl.feedcalendar.controller;

import com.aqatl.feedcalendar.legacycalendar.Calendar;
import com.aqatl.feedcalendar.legacycalendar.Order;
import com.rometools.rome.io.FeedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Maciej on 2017-05-20.
 */
@Controller
@RequestMapping("/old")
public class PlainCalendarController {

	@RequestMapping(method = RequestMethod.GET)
	public String oldCalendar() {
		return "old";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String calendar(
			@RequestParam(value = "url", defaultValue = "") String url,
			@RequestParam(value = "order", defaultValue = "descending") String order,
			Model model) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			Calendar calendar = new Calendar(Calendar.createFeed(url));
			calendar.printFeed(new PrintStream(baos), Enum.valueOf(Order.class, order.toUpperCase()), true);

			String printedCalendar = new String(baos.toByteArray(), StandardCharsets.UTF_8);
			model.addAttribute("calendar", printedCalendar);
			return "plain_calendar";
		} catch (IOException | FeedException e) {
			return "Error: " + e.getMessage();
		}
	}
}
