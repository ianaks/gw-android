package com.guesswhat.android.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.guesswhat.android.game.utils.DateUtils;
import com.guesswhat.android.sqlite.helper.DatabaseHelper;
import com.guesswhat.android.system.utils.Properties;
import com.guesswhat.android.system.utils.SystemProperties;

import android.os.CountDownTimer;

public class TimeMaster {	
	
	private List<HeartListener> heartListeners = new ArrayList<HeartListener>();
	
	private static TimeMaster instance;
	
	public static TimeMaster getInstance() {
		if (instance == null) {
			instance = new TimeMaster();
		}
		return instance;
	}
	
	public void startTime() {
		DatabaseHelper helper = DatabaseHelper.getHelper();
		String heartTimerProperty = helper.getProperty(Properties.LAST_HEART_TIME.toString());
		if (heartTimerProperty == null) {
			helper.putProperty(Properties.HEARTS.toString(), String.valueOf(SystemProperties.HEARTS_MAXIMUM));
		} else {
			Date previousDate = new Date(Long.valueOf(heartTimerProperty));
			Date now = Calendar.getInstance().getTime();
			long milliseconds = DateUtils.milliSecondsBetween(now, previousDate);
			
			String heartsProperty = helper.getProperty(Properties.HEARTS.toString());
			int hearts = Integer.valueOf(heartsProperty);
			if (milliseconds > SystemProperties.HEART_TIMER && hearts < SystemProperties.HEARTS_MAXIMUM) {
				milliseconds /= SystemProperties.HEART_TIMER;
				int newHearts = (int) (milliseconds / SystemProperties.HEART_TIMER);
				hearts = Math.min(SystemProperties.HEARTS_MAXIMUM, hearts + newHearts);
				helper.putProperty(Properties.LAST_HEART_TIME.toString(), String.valueOf(now.getTime()));
			}
			new HeartTimer(milliseconds % SystemProperties.HEART_TIMER, 1000).start();
		}
	}
	
	public void subscribeOnHeartTimer(HeartListener listener) {
		heartListeners.add(listener);
	}
	
	private class HeartTimer extends CountDownTimer {

		public HeartTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			// notify listeners
			for (HeartListener listener : heartListeners) {
				listener.onFinish();
			}
			
			incrementHearts();
		}
		
		private void incrementHearts() {
			DatabaseHelper helper = DatabaseHelper.getHelper();
			String heartsProperty = helper.getProperty(Properties.HEARTS.toString());
			int hearts = Integer.valueOf(heartsProperty);
			helper.putProperty(Properties.HEARTS.toString(), String.valueOf(++hearts));
			helper.putProperty(Properties.LAST_HEART_TIME.toString(), String.valueOf(Calendar.getInstance().getTime().getTime()));
			if (hearts < SystemProperties.HEARTS_MAXIMUM) {
				start();
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// notify listeners
			for (HeartListener listener : heartListeners) {
				listener.onTick();
			}
		}
		
	}
	
}
