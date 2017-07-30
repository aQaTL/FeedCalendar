package com.aqatl.feedcalendar.controller;

import com.aqatl.feedcalendar.calendar.Calendar;
import com.aqatl.feedcalendar.utils.ThymeleafExpressionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Maciej on 2017-07-09.
 */

@Controller
@RequestMapping("/")
public class CalendarController {
	@Autowired
	private ThymeleafExpressionUtils thymeleafUtils;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String calendar(
			@RequestParam(value = "url", defaultValue = "") String url,
			@RequestParam(value = "order", defaultValue = "descending") String order,
			Model model) throws MalformedURLException {

		Calendar calendar = new Calendar(new URL(url));
		calendar.parseFeed();
		Map<Year, int[][][]> yearMap = calendar.processFeed();

		model.addAttribute("years", yearMap.values().toArray(new int[0][][][]));
		model.addAttribute("thymeleafUtils", thymeleafUtils);

		return "calendar";
	}


	@ModelAttribute(name = "weekdays")
	public List<String> weekdays() {
		return Stream.of(DayOfWeek.values()).
				map(dayOfWeek -> dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("en"))).
				collect(Collectors.toList());
	}
}
