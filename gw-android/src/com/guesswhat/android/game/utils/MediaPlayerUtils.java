package com.guesswhat.android.game.utils;

import com.guesswhat.android.R;
import com.guesswhat.android.sqlite.helper.DatabaseHelper;
import com.guesswhat.android.system.utils.Properties;
import com.guesswhat.android.system.utils.SystemProperties;

import android.app.Activity;
import android.media.MediaPlayer;
import android.widget.ImageView;

public class MediaPlayerUtils {

	private static MediaPlayer mpMainTheme;
	
	public static void init(Activity activity) {
		mpMainTheme = MediaPlayer.create(activity, R.raw.main_theme);        
    	mpMainTheme.setLooping(true);
    	if (SystemProperties.SOUND) {
    		mpMainTheme.start();
    	} else {
    		ImageView soundView = (ImageView) activity.findViewById(R.id.button_sound_on_off);
    		soundView.setImageResource(R.drawable.button_sound_off);
    	}
	}
	
	public static void start() {
		if (mpMainTheme == null) {
			throw new RuntimeException("Player is not initialized");
		}
		
		mpMainTheme.start();
	}
	
	public static void play(boolean play) {
		if (mpMainTheme == null) {
			throw new RuntimeException("Player is not initialized");
		}
		
		if (play && SystemProperties.SOUND) {
			mpMainTheme.setVolume(1, 1);
		} else {
			mpMainTheme.setVolume(0, 0);
		}
		
		if (play && !mpMainTheme.isPlaying()) {
			mpMainTheme.start();
		}
	}
	
	public static void sound(boolean sound) {
		SystemProperties.SOUND = sound;
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper();
		databaseHelper.putProperty(Properties.SOUND.toString(), String.valueOf(SystemProperties.SOUND));
	}
	
	public static void stop() {
		if (mpMainTheme == null) {
			throw new RuntimeException("Player is not initialized");
		}
		
		mpMainTheme.stop();
	}
	
}
