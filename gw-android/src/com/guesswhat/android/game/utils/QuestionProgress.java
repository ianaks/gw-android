package com.guesswhat.android.game.utils;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

import com.guesswhat.android.R;
import com.guesswhat.android.game.main.Game;
import com.guesswhat.android.system.utils.SystemProperties;

public class QuestionProgress {
	
	private static ProgressBar progressBar = null;
	private static CountDownTimer timer = null;
	private static boolean running = false;
	private static int timePassed = 0;
	
	public static void init(Activity activity) {
		progressBar = (ProgressBar) activity.findViewById(R.id.questionProgressBar);
	}

	public static void start() {
		running = true;
		timePassed = 0;
		progressBar.setProgress(0);
		timer = new CountDownTimer(SystemProperties.QUESTION_TIMER, 250) {

			private int ticks = 0;
			
			@Override
			public void onTick(long millisUntilFinished) {
				++ticks;
				if (ticks % 4 == 0) {
					progressBar.incrementProgressBy(1);
				}
				timePassed = (int) (SystemProperties.QUESTION_TIMER - millisUntilFinished);
			}

			@Override
			public void onFinish() {
				running = false;
				progressBar.setProgress(progressBar.getMax());
				Game.getInstance().giveAnswer(null, SystemProperties.QUESTION_TIMER);
				QuestionEditorsController.setQuestionTimeout();
			}
			
		}.start();
	}
	
	public static void cancel() {
		running = false;
		timer.cancel();
	}
	
	public static boolean isRunning() {
		return running;
	}

	public static int getTime() {
		return timePassed;
	}

}
