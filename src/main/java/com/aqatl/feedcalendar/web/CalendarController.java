package com.aqatl.feedcalendar.web;

import com.aqatl.feedcalendar.calendar.Calendar;
import com.aqatl.feedcalendar.calendar.Order;
import com.rometools.rome.io.FeedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Maciej on 2017-05-20.
 */
@Controller
@RequestMapping("/")
public class CalendarController {

	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public String calendar(
			@RequestParam(name = "url", defaultValue = "") String url,
			@RequestParam(name = "order", defaultValue = "descending") String order,
			Model model) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			Calendar calendar = new Calendar(Calendar.createFeed(url));
			calendar.printFeed(new PrintStream(baos), Enum.valueOf(Order.class, order.toUpperCase()), true);

			String printedCalendar = new String(baos.toByteArray(), StandardCharsets.UTF_8);
			model.addAttribute("calendar", printedCalendar);
			return "calendar";
		} catch (IOException | FeedException e) {
			return "Error: " + e.getMessage();
		}
	}
}
