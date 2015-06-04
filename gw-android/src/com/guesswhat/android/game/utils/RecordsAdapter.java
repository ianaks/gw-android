package com.guesswhat.android.game.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guesswhat.android.R;

public class RecordsAdapter extends BaseAdapter{
	
	private List<Integer> points;
	private int userPlace;
	private Context context;
	
	public RecordsAdapter(List<Integer> points, int userPlace, Context context){
		this.points = points;
		this.userPlace = userPlace;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
	    if (v == null) {
	        // Inflate the layout according to the view type
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        if (position!=userPlace) {
	            v = inflater.inflate(R.layout.list_item_record, parent, false);
	        }
	        else {
	            v = inflater.inflate(R.layout.list_item_user_record, parent, false);
	        }
	    }
	 
	    TextView pointTxt = (TextView) v.findViewById(R.id.textViewRecord);
	    TextView userPointTxt = (TextView) v.findViewById(R.id.textViewUserRecord);
	    	    
	    if(position<10 && userPlace!=position)
	    	pointTxt.setText(userPlace + ". " + points.get(position));
	    else if(userPlace==position)
	    	userPointTxt.setText(userPlace + ". " + points.get(position));
	    else if(userPlace>9 && position==10)
	    	pointTxt.setText("...");
	    else if(userPlace>9 && position==11)
	    	userPointTxt.setText(userPlace + ". " + points.get(position));
	    
	    return v;
	}
	
}
