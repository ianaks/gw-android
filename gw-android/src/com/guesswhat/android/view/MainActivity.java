package com.guesswhat.android.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.guesswhat.android.R;
import com.guesswhat.android.system.utils.PrefsLoader;

public class MainActivity extends Activity {
	
	private boolean soundOn = true;
	
	Context context;
	
	MediaPlayer mpButtonClick;
	MediaPlayer mpMainTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        init_layout();
        initSystem();
    }

    private void init_layout(){
    	mpButtonClick = MediaPlayer.create(context, R.raw.button_click);
        ImageView buttonRecords = (ImageView)findViewById(R.id.button_records);
        buttonRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	mpButtonClick.start();
                Intent intent = new Intent(context, RecordsActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        ImageView buttonExit = (ImageView)findViewById(R.id.button_exit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	mpButtonClick.start();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                MainActivity.this);

                alertDialogBuilder.setMessage("Are you sure you want to exit game?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        MainActivity.this.finish();
                                    }
                                })
                        .setNegativeButton("No",
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog,
			                                        int id) {
			                        dialog.cancel();
			                    }
			                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
        final ImageView soundView = (ImageView)findViewById(R.id.button_sound_on_off);
        soundView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(soundOn){
					soundView.setImageResource(R.drawable.button_sound_off);
					mpButtonClick.setVolume(0, 0);
					mpMainTheme.setVolume(0, 0);
					soundOn = false;
				} else{
					soundView.setImageResource(R.drawable.button_sound);
					mpButtonClick.setVolume(1, 1);
					mpMainTheme.setVolume(1, 1);
					soundOn = true;
				}
			}
		});
        
        ImageView playView = (ImageView)findViewById(R.id.button_play);
        playView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mpButtonClick.start();
                Intent intent = new Intent(context, GameActivity.class);
                startActivityForResult(intent, 0);
			}
		});
        
        mpMainTheme = MediaPlayer.create(context, R.raw.main_theme);
        mpMainTheme.start();
    	mpMainTheme.setLooping(true);
    }
    
    private void initSystem() {
    	double density = getResources().getDisplayMetrics().density;	
    	PrefsLoader.load(density);
    }

}
