package com.aqatl.feedcalendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Maciej on 2017-07-09.
 */

@Controller
@RequestMapping("/")
public class CalendarController {

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String calendar(
			@RequestParam(value = "url", defaultValue = "") String url,
			@RequestParam(value = "order", defaultValue = "descending") String order,
			Model model) {

		throw new UnsupportedOperationException("Not implemented");
	}

}
