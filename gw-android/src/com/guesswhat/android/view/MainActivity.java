package com.guesswhat.android.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.guesswhat.android.R;
import com.guesswhat.android.game.utils.AdController;
import com.guesswhat.android.game.utils.HeartsController;
import com.guesswhat.android.game.utils.ImageDownloadProgress;
import com.guesswhat.android.game.utils.MediaPlayerUtils;
import com.guesswhat.android.sqlite.helper.DatabaseHelper;
import com.guesswhat.android.system.utils.PropertiesLoader;
import com.guesswhat.android.system.utils.SystemProperties;
import com.guesswhat.android.timer.TimeMaster;

public class MainActivity extends Activity {
	
	private boolean soundOn = SystemProperties.SOUND;
	
	Context context;
	
	MediaPlayer mpButtonClick;
	GameDialogFragment dlg;
	AddHeartFragment addFragment;
	Intent intent;
	ImageView soundView;
	TextView pointsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initSystem();
        init_layout();
    }

    private void init_layout() {
    	mpButtonClick = MediaPlayer.create(context, R.raw.button_click);

        dlg = new GameDialogFragment();
        dlg.setTextButton1("Yes");
        dlg.setTextButton2("Cancel");
        dlg.setDialogType(GameDialogFragment.DIALOG_TYPE_EXIT);
        dlg.setMessage("Are you sure you want to exit game?");
        
        addFragment = new AddHeartFragment();
        
        soundView = (ImageView)findViewById(R.id.button_sound_on_off); 
        
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.imageDownloadProgressBar);
		progressBar.setMax(SystemProperties.QUESTIONS_COUNT);
		
		pointsTxt = (TextView)findViewById(R.id.pointsTxt); 
		pointsTxt.setText(Integer.toString(SystemProperties.TOTAL_POINTS));
		pointsTxt.setTextSize(SystemProperties.FONT_SIZE/0.7f);
//		pointsTxt.setTypeface(Typeface., Typeface.BOLD);
    }
    
    public void onClick(View v) {    	
        switch (v.getId()) {        
	        case R.id.button_play:
	        	if (SystemProperties.HEARTS_COUNT > 0) {
	        		intent = new Intent(context, GameActivity.class);
	        		startActivityForResult(intent, 0);
	        	}
	        break;
	        case R.id.button_records:
	        	intent = new Intent(context, RecordsActivity.class);
	            startActivityForResult(intent, 0);
	            break;
	        case R.id.button_exit:
	        	dlg.show(getFragmentManager(), "dlg");
	        	break;
	        case R.id.button_plus:
	        	addFragment.show(getFragmentManager(), "addFragment");
	        	break;
	        case R.id.button_sound_on_off:
	        	if (soundOn) {
	        		soundView.setImageResource(R.drawable.button_sound_off);
					soundOn = false;
				} else {
					soundView.setImageResource(R.drawable.button_sound);
					soundOn = true;
				}
	        	MediaPlayerUtils.sound(soundOn);
				MediaPlayerUtils.play(soundOn);
	        	break;
	        default:
	        	break;
        }
        
        if (SystemProperties.SOUND) {
    		mpButtonClick.start();
    	}
      }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	MediaPlayerUtils.play(false);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	MediaPlayerUtils.stop();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	pointsTxt.setText(Integer.toString(SystemProperties.TOTAL_POINTS));
    	MediaPlayerUtils.play(true);
    }
    
    private void initSystem() {
    	DatabaseHelper.init(this);
    	String deviceId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
    	double density = getResources().getDisplayMetrics().density;
    	int width = getResources().getDisplayMetrics().widthPixels;
    	try {
    		PropertiesLoader.loadSystemProperties(deviceId, density, width);
    	} catch (Exception e) {
			GameDialogFragment dlg = new GameDialogFragment(this);
	        dlg.setTextButton1("Ok");
	        dlg.setDialogType(GameDialogFragment.DIALOG_NO_INTERNET_ACCESS);
	        dlg.setMessage("Check your Internet connection");
	        dlg.show(getFragmentManager(), "dlg");
		}
    	TimeMaster.getInstance(this).startTime();
    	HeartsController.init(this);
    	MediaPlayerUtils.init(this);
    	ImageDownloadProgress.init(this);
    	
    	AdController.init(this);
    }
    
}
