package com.aqatl.feedcalendar.config;

import com.aqatl.feedcalendar.utils.ThymeleafExpressionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Maciej on 2017-07-16.
 */
@Configuration
@ComponentScan(
		basePackages = {"com.aqatl.feedcalendar"},
		excludeFilters = {
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
		}
)
public class RootConfig {
	@Bean
	ThymeleafExpressionUtils thymeleafUtils() {
		return new ThymeleafExpressionUtils();
	}

}
