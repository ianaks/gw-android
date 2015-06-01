package com.guesswhat.android.view;

import com.guesswhat.android.R;
import com.guesswhat.android.game.main.Game;
import com.guesswhat.android.game.main.GameRound;
import com.guesswhat.android.game.main.Result;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class GameActivity extends Activity {
	
	private Context context;
	private String[] questions;
	
	EditText answer1;
	EditText answer2;
	EditText answer3;
	EditText answer4;
	ImageView question;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		context = this;
		init_layout();
	}
	
	private void init_layout(){
		answer1 = (EditText)findViewById(R.id.answer1);
		answer2 = (EditText)findViewById(R.id.answer2);
		answer3 = (EditText)findViewById(R.id.answer3);
		answer4 = (EditText)findViewById(R.id.answer4);
		question = (ImageView)findViewById(R.id.question);
		
		Game game = Game.getInstance();
		game.initialize();
		while (game.hasNext()) {
			GameRound round = game.next();
			questions = round.getAnswers();
			answer1.setText(questions[0]);
			answer2.setText(questions[1]);
			answer3.setText(questions[2]);
			answer4.setText(questions[3]);
//			game.giveAnswer(answer, time);
		   
		  }
		  
		  Result result = game.getResult();
	}
	
	private class OnAnswerClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Game game = Game.getInstance();
			
		}
		
	}

}
