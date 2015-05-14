package com.guesswhat.android.view;

import com.guesswhat.android.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class GameActivity extends Activity {
	
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		context = this;
		init_layout();
	}
	
	private void init_layout(){
		
	}

}
