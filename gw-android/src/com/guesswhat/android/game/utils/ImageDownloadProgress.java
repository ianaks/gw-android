package com.guesswhat.android.game.utils;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.guesswhat.android.R;

public class ImageDownloadProgress {
	
	private static ProgressBar progressBar = null;
	
	public static void init(Activity activity) {
		progressBar = (ProgressBar) activity.findViewById(R.id.imageDownloadProgressBar);
	}

	public static void start() {
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setProgress(0);	
	}
	
	public static void increment() {
		progressBar.incrementProgressBy(1);	
	}
	
	public static void finish() {
		progressBar.setProgress(progressBar.getMax());
		progressBar.setVisibility(View.GONE);
	}

}
