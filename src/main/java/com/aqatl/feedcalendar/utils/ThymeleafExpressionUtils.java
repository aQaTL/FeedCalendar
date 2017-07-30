package com.aqatl.feedcalendar.utils;

/**
 * @author Maciej on 2017-07-30.
 */
public class ThymeleafExpressionUtils {

	public boolean isArrayEmpty(int[] array) {
		for (int i : array) {
			if (i != 0) {
				return false;
			}
		}
		return true;
	}
}
