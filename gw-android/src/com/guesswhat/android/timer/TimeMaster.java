package com.guesswhat.android.timer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.guesswhat.android.R;
import com.guesswhat.android.game.utils.DateUtils;
import com.guesswhat.android.game.utils.HeartsController;
import com.guesswhat.android.sqlite.helper.DatabaseHelper;
import com.guesswhat.android.system.utils.Properties;
import com.guesswhat.android.system.utils.SystemProperties;

public class TimeMaster {
	
	private TextView heartTimer = null;
	
	private static TimeMaster instance;
	
	private TimeMaster(Activity activity) {
		heartTimer = (TextView) activity.findViewById(R.id.heartTimer);
		heartTimer.setTextSize(SystemProperties.FONT_SIZE);
	}
	
	public static TimeMaster getInstance(Activity activity) {
		if (instance == null) {
			instance = new TimeMaster(activity);
		}
		return instance;
	}
	
	public static TimeMaster getInstance() {
		return instance;
	}
	
	public void startTime() {
		DatabaseHelper helper = DatabaseHelper.getHelper();
		String heartTimerProperty = helper.getProperty(Properties.LAST_HEART_TIME.toString());
		if (heartTimerProperty == null) {
			helper.putProperty(Properties.HEARTS.toString(), String.valueOf(SystemProperties.HEARTS_MAXIMUM));
			SystemProperties.HEARTS_COUNT = SystemProperties.HEARTS_MAXIMUM;
			helper.putProperty(Properties.LAST_HEART_TIME.toString(), String.valueOf(new Date().getTime()));
		} else {
			Date previousDate = new Date(Long.valueOf(heartTimerProperty));
			long milliseconds = DateUtils.milliSecondsBetween(new Date(), previousDate);
			
			String heartsProperty = helper.getProperty(Properties.HEARTS.toString());
			SystemProperties.HEARTS_COUNT = Integer.valueOf(heartsProperty);
			if (milliseconds > SystemProperties.HEART_TIMER && SystemProperties.HEARTS_COUNT < SystemProperties.HEARTS_MAXIMUM) {
				milliseconds /= SystemProperties.HEART_TIMER;
				int newHearts = (int) (milliseconds / SystemProperties.HEART_TIMER);
				SystemProperties.HEARTS_COUNT = Math.min(SystemProperties.HEARTS_MAXIMUM, SystemProperties.HEARTS_COUNT + newHearts);
				helper.putProperty(Properties.HEARTS.toString(), String.valueOf(SystemProperties.HEARTS_COUNT));
				helper.putProperty(Properties.LAST_HEART_TIME.toString(), String.valueOf(new Date().getTime()));
			} else if (SystemProperties.HEARTS_COUNT < SystemProperties.HEARTS_MAXIMUM) {
				long restMilliseconds = SystemProperties.HEART_TIMER - milliseconds;
				new HeartTimer(restMilliseconds, 1000).start();
	
				Date date = new Date(restMilliseconds);
				DateFormat formatter = new SimpleDateFormat("mm:ss", Locale.US);
				String dateFormatted = formatter.format(date);
				heartTimer.setText(dateFormatted);
				heartTimer.setVisibility(View.VISIBLE);
			}
		}
	}
	
	private class HeartTimer extends CountDownTimer {

		public HeartTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {			
			HeartsController.increaseHearts();
			if (SystemProperties.HEARTS_COUNT < SystemProperties.HEARTS_MAXIMUM) {
				new HeartTimer(SystemProperties.HEART_TIMER, 1000).start();
			} else {
				heartTimer.setText("");
				heartTimer.setVisibility(View.INVISIBLE);
			}
			
			DatabaseHelper helper = DatabaseHelper.getHelper();
			helper.putProperty(Properties.LAST_HEART_TIME.toString(), String.valueOf(new Date().getTime()));
		}

		@Override
		public void onTick(long millisUntilFinished) {
			Date date = new Date(millisUntilFinished);
			DateFormat formatter = new SimpleDateFormat("mm:ss", Locale.US);
			String dateFormatted = formatter.format(date);
			heartTimer.setText(dateFormatted);
		}
		
	}
	
}
