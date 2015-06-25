package com.guesswhat.android.game.utils;

import java.util.Date;

import android.app.Activity;
import android.graphics.Color;
import android.widget.ImageView;

import com.guesswhat.android.R;
import com.guesswhat.android.sqlite.helper.DatabaseHelper;
import com.guesswhat.android.system.utils.Properties;
import com.guesswhat.android.system.utils.SystemProperties;
import com.guesswhat.android.timer.TimeMaster;

public class HeartsController {

	private static Activity activity = null;
	
	private HeartsController(Activity activity) {}
	
	public static void init(Activity context) {
		activity = context;
		
		if (SystemProperties.HEARTS_COUNT < SystemProperties.HEARTS_MAXIMUM) {
			ImageView heart = null;
			switch (SystemProperties.HEARTS_COUNT) {
				case 0:
					ImageView buttonPlay = (ImageView) activity.findViewById(R.id.button_play);
					buttonPlay.setEnabled(false);
					buttonPlay.setColorFilter(Color.argb(200,50,50,50));
					heart = (ImageView)activity.findViewById(R.id.heart0);
					heart.setImageResource(R.drawable.button_empty_heart);
				case 1:
					heart = (ImageView)activity.findViewById(R.id.heart1);
					heart.setImageResource(R.drawable.button_empty_heart);
				case 2:
					heart = (ImageView)activity.findViewById(R.id.heart2);
					heart.setImageResource(R.drawable.button_empty_heart);
				case 3:
					heart = (ImageView)activity.findViewById(R.id.heart3);
					heart.setImageResource(R.drawable.button_empty_heart);
				case 4:
					heart = (ImageView)activity.findViewById(R.id.heart4);
					heart.setImageResource(R.drawable.button_empty_heart);
				default:
					return;
			}			
		}
	}
	
	public static void increaseHearts() {
		if (SystemProperties.HEARTS_COUNT == 0) {
			ImageView buttonPlay = (ImageView) activity.findViewById(R.id.button_play);
			buttonPlay.setEnabled(true);
			buttonPlay.setColorFilter(null);
		}
		if (SystemProperties.HEARTS_COUNT < SystemProperties.HEARTS_MAXIMUM) {
			ImageView heart = null;
			switch (SystemProperties.HEARTS_COUNT) {
				case 0:
					heart = (ImageView)activity.findViewById(R.id.heart0);
					break;
				case 1:
					heart = (ImageView)activity.findViewById(R.id.heart1);
					break;
				case 2:
					heart = (ImageView)activity.findViewById(R.id.heart2);
					break;
				case 3:
					heart = (ImageView)activity.findViewById(R.id.heart3);
					break;
				case 4:
					heart = (ImageView)activity.findViewById(R.id.heart4);
					break;
				default:
					return;
			}
			heart.setImageResource(R.drawable.button_full_heart);
			
			DatabaseHelper helper = DatabaseHelper.getHelper();
			helper.putProperty(Properties.HEARTS.toString(), String.valueOf(++SystemProperties.HEARTS_COUNT));
		}		
	}
	
	public static void decreaseHearts() {
		if (SystemProperties.HEARTS_COUNT > 0) {
			ImageView heart = null;
			switch (SystemProperties.HEARTS_COUNT) {
				case 5:
					heart = (ImageView)activity.findViewById(R.id.heart4);
					break;
				case 4:
					heart = (ImageView)activity.findViewById(R.id.heart3);
					break;
				case 3:
					heart = (ImageView)activity.findViewById(R.id.heart2);
					break;
				case 2:
					heart = (ImageView)activity.findViewById(R.id.heart1);
					break;
				case 1:
					heart = (ImageView)activity.findViewById(R.id.heart0);
					break;
				default:
					return;
			}
			heart.setImageResource(R.drawable.button_empty_heart);
			
			DatabaseHelper helper = DatabaseHelper.getHelper();
			helper.putProperty(Properties.HEARTS.toString(), String.valueOf(--SystemProperties.HEARTS_COUNT));
			if (SystemProperties.HEARTS_COUNT + 1 == SystemProperties.HEARTS_MAXIMUM) {
				helper = DatabaseHelper.getHelper();
				helper.putProperty(Properties.LAST_HEART_TIME.toString(), String.valueOf(new Date().getTime()));
				TimeMaster.getInstance().startTime();
			}
			
		} 
		if (SystemProperties.HEARTS_COUNT == 0) {
			ImageView buttonPlay = (ImageView) activity.findViewById(R.id.button_play);
			buttonPlay.setEnabled(false);
			buttonPlay.setColorFilter(Color.argb(200,50,50,50));
		}
	}
	
}
