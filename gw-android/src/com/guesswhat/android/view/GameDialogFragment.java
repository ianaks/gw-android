package com.guesswhat.android.view;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.guesswhat.android.R;

public class GameDialogFragment extends DialogFragment implements OnClickListener {

	  private String textButton1;
	  private String textButton2;
	  private String dialog;
	  private String message;
	  
	  public GameDialogFragment(String textButton1, String textButton2,
			  String dialog, String message){
		  super();
		  this.textButton1 = textButton1;
		  this.textButton2 = textButton2;
		  this.dialog = dialog;
		  this.message = message;
	  }

	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    
	    View v = inflater.inflate(R.layout.fragment_dialog, container);
	    EditText button1 = (EditText) v.findViewById(R.id.button1);
	    EditText button2 = (EditText) v.findViewById(R.id.button2);
	    TextView messageTxt = (TextView)v.findViewById(R.id.message);
	    button1.setOnClickListener(this);
	    button1.setText(textButton1);
	    button2.setText(textButton2);
	    button2.setOnClickListener(this);
	    messageTxt.setText(message);
	    
	    return v;
	  }

	  public void onClick(View v) {
			if(dialog.equals("exit")){
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
			} else if(dialog.equals("score")){
				switch(v.getId()) {
	        	case R.id.button1:
	        		GameDialogFragment.this.getActivity().finish();
		        	break;
	        	case R.id.button2:
	        		Intent intent = new Intent(v.getContext(), RecordsActivity.class);
	                startActivity(intent);
		        	break;
		        default:
		        	break;
				}
			}			
			
		}

	  public void onDismiss(DialogInterface dialog) {
	    super.onDismiss(dialog);
	  }

	  public void onCancel(DialogInterface dialog) {
	    super.onCancel(dialog);
	  }
	}
