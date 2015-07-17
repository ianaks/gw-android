package com.guesswhat.android.view;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guesswhat.android.R;
import com.guesswhat.android.system.utils.SystemProperties;

public class AddHeartFragment extends DialogFragment implements OnClickListener {

  private String txt1;
  private String txt2;
  private String txt3;

  public AddHeartFragment() {}
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {		    
	    View v = inflater.inflate(R.layout.fragment_add_heart, container);
	    v.setAlpha(0.8f);
	    
	    Button button1 = (Button) v.findViewById(R.id.button1);
	    Button button2 = (Button) v.findViewById(R.id.button2);
	    Button button3 = (Button) v.findViewById(R.id.button3);
	    
	    TextView txt1 = (TextView) v.findViewById(R.id.txt1);
	    TextView txt2 = (TextView) v.findViewById(R.id.txt2);
	    TextView txt3 = (TextView) v.findViewById(R.id.txt3);
	    
	    button1.setOnClickListener(this);
	    button1.setTextSize(SystemProperties.FONT_SIZE);
	    button2.setOnClickListener(this);
	    button2.setTextSize(SystemProperties.FONT_SIZE);
	    button3.setOnClickListener(this);
	    button3.setTextSize(SystemProperties.FONT_SIZE);
	    txt1.setTextSize(SystemProperties.FONT_SIZE);
	    txt2.setTextSize(SystemProperties.FONT_SIZE);
	    txt3.setTextSize(SystemProperties.FONT_SIZE);
	    
	    getDialog().setCanceledOnTouchOutside(true);
	    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); 
	    
	    View decorView = getDialog().getWindow().getDecorView();  
	    decorView.setBackgroundResource(android.R.color.transparent);
	    
	 // retrieve display dimensions
	    Rect displayRectangle = new Rect();
	    getDialog().getWindow().getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
	    
	    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(getDialog().getWindow().getAttributes());
	    lp.width = (int)(displayRectangle.width()*0.7f);
	    lp.height = (int)(displayRectangle.height()*0.7);
	    getDialog().getWindow().setAttributes(lp);
	    ((LinearLayout) v.findViewById(R.id.LinearLayout1)).getLayoutParams().height = lp.height/3;
	    ((LinearLayout) v.findViewById(R.id.LinearLayout2)).getLayoutParams().height = lp.height/3;
	    ((LinearLayout) v.findViewById(R.id.LinearLayout3)).getLayoutParams().height = lp.height/3;
	    
	    return v;
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
	    	case R.id.button1:
	    		getDialog().cancel();	 
	        	break;
	    	case R.id.button2:
	        	break;
	    	case R.id.button3:
	        	break;
	        default:
	        	break;
		}
	}

	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
	}

	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}

	public String getTxt1() {
		return txt1;
	}

	public void setTxt1(String txt1) {
		this.txt1 = txt1;
	}

	public String getTxt2() {
		return txt2;
	}

	public void setTxt2(String txt2) {
		this.txt2 = txt2;
	}

	public String getTxt3() {
		return txt3;
	}

	public void setTxt3(String txt3) {
		this.txt3 = txt3;
	}

}
