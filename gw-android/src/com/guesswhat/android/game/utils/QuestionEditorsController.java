package com.guesswhat.android.game.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.EditText;
import android.widget.ImageView;

import com.guesswhat.android.R;
import com.guesswhat.android.system.utils.SystemProperties;
import com.guesswhat.android.view.GameActivity;

public class QuestionEditorsController {
	
	private static EditText answer1;
	private static EditText answer2;
	private static EditText answer3;
	private static EditText answer4;
	private static ImageView question;

	public static void init(Activity activity) {
		answer1 = (EditText) activity.findViewById(R.id.answer1);
		answer2 = (EditText) activity.findViewById(R.id.answer2);
		answer3 = (EditText) activity.findViewById(R.id.answer3);
		answer4 = (EditText) activity.findViewById(R.id.answer4);
		question = (ImageView) activity.findViewById(R.id.question);
		
		answer1.setTextSize(SystemProperties.FONT_SIZE);
		answer2.setTextSize(SystemProperties.FONT_SIZE);
		answer3.setTextSize(SystemProperties.FONT_SIZE);
		answer4.setTextSize(SystemProperties.FONT_SIZE);		
	}
	
	public static void setWrong(int id) {
		EditText answerEditText = findEditorById(id);		
		GradientDrawable curBackground = (GradientDrawable) answerEditText.getBackground();
		curBackground.setColors(new int [] {Color.TRANSPARENT, Color.RED});
	}
	
	public static void setCorrect(int id) {
		EditText answerEditText = findEditorById(id);		
		GradientDrawable curBackground = (GradientDrawable) answerEditText.getBackground();
		curBackground.setColors(new int [] {Color.TRANSPARENT, Color.GREEN});
	}
	
	private static EditText findEditorById(int id) {
		switch(id) {
	    	case R.id.answer1:
	    		return answer1;
	        case R.id.answer2:
	        	return answer2;
	        case R.id.answer3:
	        	return answer3;
	        case R.id.answer4:
	        	return answer4;
	        default:
	        	return null;
		}
	}
	
	public static void setDefaults() {
		GradientDrawable curBackground = (GradientDrawable) answer1.getBackground();
		curBackground.setColors(new int [] {Color.TRANSPARENT, Color.parseColor("#66FFFF")});
		
		curBackground = (GradientDrawable) answer2.getBackground();
		curBackground.setColors(new int [] {Color.TRANSPARENT, Color.parseColor("#66FFFF")});
		
		curBackground = (GradientDrawable) answer3.getBackground();
		curBackground.setColors(new int [] {Color.TRANSPARENT, Color.parseColor("#66FFFF")});
		
		curBackground = (GradientDrawable) answer4.getBackground();
		curBackground.setColors(new int [] {Color.TRANSPARENT, Color.parseColor("#66FFFF")});
	}
	
//	public static void setQuestionTimeout() {
//		setCorrect(GameActivity.correctButton);
//	}
	
	public static void setQuestionDefault() {
		question.setImageBitmap(BitmapEditor.getDefaultBitmap());
	}
	
}
