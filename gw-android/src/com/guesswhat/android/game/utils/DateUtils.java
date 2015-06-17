package com.guesswhat.android.game.utils;

import java.util.Date;

public class DateUtils {

	public static long milliSecondsBetween(Date a, Date b) {
		return (a.getTime() - b.getTime());
	}
	
}
