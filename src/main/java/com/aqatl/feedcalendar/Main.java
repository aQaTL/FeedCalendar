package com.aqatl.feedcalendar;


import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Maciej on 2017-05-20.
 */

@SpringBootApplication
@Logger
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
