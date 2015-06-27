package com.guesswhat.android.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.appodeal.ads.Appodeal;
import com.guesswhat.android.R;
import com.guesswhat.android.game.main.Game;
import com.guesswhat.android.game.main.GameRound;
import com.guesswhat.android.game.main.Result;
import com.guesswhat.android.game.utils.MediaPlayerUtils;
import com.guesswhat.android.game.utils.QuestionEditorsController;
import com.guesswhat.android.game.utils.QuestionProgress;
import com.guesswhat.android.system.utils.SystemProperties;

public class GameActivity extends Activity {
	
	private String[] questions;
	
	private EditText answer1;
	private EditText answer2;
	private EditText answer3;
	private EditText answer4;
	private ImageView question;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MediaPlayerUtils.play(true);
		setContentView(R.layout.activity_game);
		init_layout();
		startGameProcess();
	}
	
	private void init_layout() {
		QuestionProgress.init(this);
		QuestionEditorsController.init(this);
		ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
		progressBar.setMax(SystemProperties.QUESTION_TIMER / 1000);
				
		answer1 = (EditText)findViewById(R.id.answer1);
		answer2 = (EditText)findViewById(R.id.answer2);
		answer3 = (EditText)findViewById(R.id.answer3);
		answer4 = (EditText)findViewById(R.id.answer4);
		question = (ImageView)findViewById(R.id.question);						
	}
	
	private void startGameProcess() {
		Game game = Game.getInstance();
		if (game.initialize()) {
			fillWidgets();
		} else {
			// notify user, that he hasn't enough hearts to play
		}
	}

	public void onClick(View v) {
		if (QuestionProgress.isRunning()) {
			Game game = Game.getInstance();
			int time = QuestionProgress.getTime();
			String answer = null;
			switch(v.getId()) {
	        	case R.id.answer1:
	        		answer = answer1.getText().toString();
		        	break;
		        case R.id.answer2:
		        	answer = answer2.getText().toString();
		        	break;
		        case R.id.answer3:
		        	answer = answer3.getText().toString();
		        	break;
		        case R.id.answer4:
		        	answer = answer4.getText().toString();
		        	break;
			}
			boolean correct = game.giveAnswer(answer, time);
			if (correct) {
				QuestionEditorsController.setCorrect(v.getId());
			} else {
				QuestionEditorsController.setWrong(v.getId());
			}
			QuestionProgress.cancel();
		} else if (v.getId() == R.id.question) {			
			fillWidgets();
		}
	}
		
	private static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
	
	private boolean fillWidgets(){
		Game game = Game.getInstance();
		
		if (game.hasNext()) {
			QuestionEditorsController.setDefaults();
			QuestionEditorsController.setQuestionDefault();
			
			GameRound round = game.next();
			questions = round.getAnswers();
			answer1.setText(questions[0]);
			answer2.setText(questions[1]);
			answer3.setText(questions[2]);
			answer4.setText(questions[3]);
			
			Bitmap bmp = BitmapFactory.decodeByteArray(round.getImage(), 0,
					round.getImage().length);
			question.setImageBitmap(getRoundedCornerBitmap(bmp, bmp.getWidth() / 20));
			
			QuestionProgress.start();
			
			return true;		   
		  } else{
			  Result result = game.getResult();
			  GameDialogFragment dlg = new GameDialogFragment();
			  dlg.setTextButton1("Ok");
			  dlg.setTextButton2("Best score");
			  dlg.setDialogType(GameDialogFragment.DIALOG_TYPE_SCORE);
			  dlg.setMessage("Your score: "
					  + result.getGamePoints() + " points!");
			  dlg.show(getFragmentManager(), "dlg");
			  
			  // Ads
			  Appodeal.show(this, Appodeal.INTERSTITIAL);
			  
			  return false;
		  }
	}
	
	@Override
    public void onBackPressed() {
		GameDialogFragment dlg = new GameDialogFragment();
		dlg.setTextButton1("Ok");
		dlg.setTextButton2("Cancel");
		dlg.setDialogType(GameDialogFragment.DIALOG_TYPE_GAME_EXIT);
		dlg.setMessage("Do you want to quit?");
		dlg.show(getFragmentManager(), "dlg");
    }

}
