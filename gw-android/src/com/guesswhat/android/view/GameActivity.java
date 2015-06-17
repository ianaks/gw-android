package com.guesswhat.android.view;

import com.guesswhat.android.R;
import com.guesswhat.android.game.main.Game;
import com.guesswhat.android.game.main.GameRound;
import com.guesswhat.android.game.main.Result;

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

public class GameActivity extends Activity {
	
	private String[] questions;
	
	EditText answer1;
	EditText answer2;
	EditText answer3;
	EditText answer4;
	ImageView question;
	GameDialogFragment dlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		init_layout();
	}
	
	private void init_layout(){
		answer1 = (EditText)findViewById(R.id.answer1);
		answer2 = (EditText)findViewById(R.id.answer2);
		answer3 = (EditText)findViewById(R.id.answer3);
		answer4 = (EditText)findViewById(R.id.answer4);
		question = (ImageView)findViewById(R.id.question);
		
		Game game = Game.getInstance();
		if (game.initialize()) {
			fillWidgets();
		} else {
			// notify user, that he hasn't enough hearts to play
		}
	}
	
	public void onClick(View v) {
		Game game = Game.getInstance();
		
		switch(v.getId()) {
        	case R.id.answer1:
	        	game.giveAnswer(answer1.getText().toString(), 0);
	        	break;
	        case R.id.answer2:
	        	game.giveAnswer(answer2.getText().toString(), 0);
	        	break;
	        case R.id.answer3:
	        	game.giveAnswer(answer3.getText().toString(), 0);
	        	break;
	        case R.id.answer4:
	        	game.giveAnswer(answer4.getText().toString(), 0);
	        	break;
		}
		fillWidgets();
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
			GameRound round = game.next();
			questions = round.getAnswers();
			answer1.setText(questions[0]);
			answer2.setText(questions[1]);
			answer3.setText(questions[2]);
			answer4.setText(questions[3]);
			
			Bitmap bmp = BitmapFactory.decodeByteArray(round.getImage(), 0,
					round.getImage().length);
			question.setImageBitmap(getRoundedCornerBitmap(bmp, 50));
			
			return true;
		   
		  } else{
			  Result result = game.getResult();
			  dlg = new GameDialogFragment();
			  dlg.setTextButton1("Ok");
			  dlg.setTextButton2("Best score");
			  dlg.setDialogType(GameDialogFragment.DIALOG_TYPE_SCORE);
			  dlg.setMessage("Your score: "
					  + result.getGamePoints() + " points!");
			  dlg.show(getFragmentManager(), "dlg");
			  
			  return false;
		  }
	}

}
