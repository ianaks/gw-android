package com.guesswhat.android.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.guesswhat.android.R;
import com.guesswhat.android.system.utils.SystemProperties;

public class GameDialogFragment extends DialogFragment implements OnClickListener {

  private String textButton1;
  private String textButton2;
  private String message;
  private int dialogType;
  private Activity activity;
  
  public static final int DIALOG_TYPE_EXIT = 0;
  public static final int DIALOG_TYPE_SCORE = 1;
  public static final int DIALOG_TYPE_GAME_EXIT = 2;
  public static final int DIALOG_NO_INTERNET_ACCESS = 3;

  public GameDialogFragment() {}
  
  public GameDialogFragment(Activity activity) {
	  this.activity = activity;
  }
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {		    
	    View v = inflater.inflate(R.layout.fragment_dialog, container);
	    v.setAlpha(0.8f);
	    
	    Button button1 = (Button) v.findViewById(R.id.button1);
	    Button button2 = (Button) v.findViewById(R.id.button2);
	    
	    TextView messageTxt = (TextView)v.findViewById(R.id.message);
	    button1.setOnClickListener(this);
	    button1.setText(textButton1);
	    button1.setTextSize(SystemProperties.FONT_SIZE);
	    if (textButton2 != null) {
	    	button2.setVisibility(View.VISIBLE);
		    button2.setText(textButton2);
		    button2.setTextSize(SystemProperties.FONT_SIZE);
		    button2.setOnClickListener(this);
	    } else {
	    	button2.setVisibility(View.GONE);
	    }
	    messageTxt.setText(message);
	    messageTxt.setTextSize(SystemProperties.FONT_SIZE);
	    
	    getDialog().setCanceledOnTouchOutside(false);
	    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); 
	    
	    View decorView = getDialog().getWindow().getDecorView();  
	    decorView.setBackgroundResource(android.R.color.transparent);
	    
	    return v;
	}
	
	public void onClick(View v) {
		if(dialogType==DIALOG_TYPE_EXIT){
			switch(v.getId()) {
		    	case R.id.button1:
		    		GameDialogFragment.this.getActivity().finish();
		        	break;
		    	case R.id.button2:
		    		getDialog().cancel();	    		
		        	break;
		        default:
		        	break;
			}
		} else if(dialogType==DIALOG_TYPE_SCORE){
			GameDialogFragment.this.getActivity().finish();
			if (v.getId() == R.id.button2) {
	    		Intent intent = new Intent(v.getContext(), RecordsActivity.class);
	            startActivity(intent);
			}
		} else if(dialogType==DIALOG_TYPE_GAME_EXIT){
			switch(v.getId()) {
		    	case R.id.button1:
		    		GameDialogFragment.this.getActivity().finish();
		        	break;
		    	case R.id.button2:
		    		getDialog().cancel();
		            break;
		        default:
		        	break;
			}
		} else if(dialogType == DIALOG_NO_INTERNET_ACCESS){
			getDialog().cancel();
			activity.finish();
		}			
		
	}

	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
	}

	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}

	public String getTextButton1() {
		return textButton1;
	}

	public void setTextButton1(String textButton1) {
		this.textButton1 = textButton1;
	}

	public String getTextButton2() {
		return textButton2;
	}

	public void setTextButton2(String textButton2) {
		this.textButton2 = textButton2;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getDialogType() {
		return dialogType;
	}

	public void setDialogType(int dialogType) {
		this.dialogType = dialogType;
	}
	
}
