package com.guesswhat.android.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.ImageView;

import com.guesswhat.android.R;
import com.guesswhat.android.game.utils.HeartsController;
import com.guesswhat.android.game.utils.MediaPlayerUtils;
import com.guesswhat.android.sqlite.helper.DatabaseHelper;
import com.guesswhat.android.system.utils.PropertiesLoader;
import com.guesswhat.android.system.utils.SystemProperties;
import com.guesswhat.android.timer.TimeMaster;

public class MainActivity extends Activity {
	
	private boolean soundOn = true;
	
	Context context;
	
	MediaPlayer mpButtonClick;
	GameDialogFragment dlg;
	Intent intent;
	ImageView soundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        init_layout();
        initSystem();
    }

    private void init_layout() {
    	mpButtonClick = MediaPlayer.create(context, R.raw.button_click);

        dlg = new GameDialogFragment();
        dlg.setTextButton1("Yes");
        dlg.setTextButton2("Cancel");
        dlg.setDialogType(GameDialogFragment.DIALOG_TYPE_EXIT);
        dlg.setMessage("Are you sure you want to exit game?");
        
        soundView = (ImageView)findViewById(R.id.button_sound_on_off);
    }
    
    public void onClick(View v) {
    	mpButtonClick.start();
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
        case R.id.button_sound_on_off:
        	if (soundOn) {
        		soundView.setImageResource(R.drawable.button_sound_off);
				mpButtonClick.setVolume(0, 0);
				MediaPlayerUtils.play(false);
				soundOn = false;
			} else {
				soundView.setImageResource(R.drawable.button_sound);
				mpButtonClick.setVolume(1, 1);
				MediaPlayerUtils.play(true);
				soundOn = true;
			}
        	break;
        default:
        	break;
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
    	MediaPlayerUtils.play(true);
    }
    
    private void initSystem() {
    	DatabaseHelper.init(this);
    	MediaPlayerUtils.init(context);
    	String deviceId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
    	double density = getResources().getDisplayMetrics().density;
    	int width = getResources().getDisplayMetrics().widthPixels;
    	PropertiesLoader.loadSystemProperties(deviceId, density, width);
    	TimeMaster.getInstance(this).startTime();
    	HeartsController.init(this);
    }
    
}
