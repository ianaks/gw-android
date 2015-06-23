package com.guesswhat.android.game.utils;

import com.guesswhat.android.R;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerUtils {

	private static MediaPlayer mpMainTheme;
	
	public static void init(Context context) {
		mpMainTheme = MediaPlayer.create(context, R.raw.main_theme);        
    	mpMainTheme.setLooping(true);
    	mpMainTheme.start();
	}
	
	public static void play(boolean play) {
		if (mpMainTheme == null) {
			throw new RuntimeException("Player is not initialized");
		}
		
		if (play) {
			mpMainTheme.setVolume(1, 1);
		} else {
			mpMainTheme.setVolume(0, 0);
		}
	}
	
	public static void stop() {
		if (mpMainTheme != null) {
			mpMainTheme.stop();
		}
	}
	
}
