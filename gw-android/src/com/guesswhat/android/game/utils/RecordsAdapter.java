package com.guesswhat.android.game.utils;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guesswhat.android.R;
import com.guesswhat.android.system.utils.SystemProperties;

public class RecordsAdapter extends BaseAdapter {
	
	private List<String> points;
	private int userPlace;
	private Context context;
	private ViewHolder holder = null;
	
	public RecordsAdapter(List<String> points, int userPlace, Context context){
		super();
		this.points = points;
		this.userPlace = userPlace;
		this.context = context;
	}

	@Override
	public int getCount() {
		return points.size();
	}

	@Override
	public Object getItem(int position) {
		return points.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
	    if (convertView == null) {
	        // Inflate the layout according to the view type
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        holder = new ViewHolder();
	        
	        if (position + 1 != userPlace && position != 11) {
	        	convertView = inflater.inflate(R.layout.list_item_record, parent, false);
	        	holder.pointTxt = (TextView) convertView.findViewById(R.id.txtPoints);
	        	holder.rankTxt = (TextView) convertView.findViewById(R.id.txtRank);
	    	    if (position%2 == 0){
	    	    	holder.pointTxt.setBackgroundColor(Color.GRAY);
	    	    	holder.pointTxt.getBackground().setAlpha(50);
	    	    	holder.rankTxt.setBackgroundColor(Color.GRAY);
	    	    	holder.rankTxt.getBackground().setAlpha(50);
	    	    } else {
	    	    	holder.rankTxt.setBackgroundColor(Color.TRANSPARENT);
	    	    	holder.pointTxt.setBackgroundColor(Color.TRANSPARENT);
	    	    }
	    	    // set font size for common row
	    	    holder.pointTxt.setTextSize(SystemProperties.FONT_SIZE);
	    	    holder.rankTxt.setTextSize(SystemProperties.FONT_SIZE);
	        } else {
	        	convertView = inflater.inflate(R.layout.list_item_user_record, parent, false);
	        	holder.userPointTxt = (TextView) convertView.findViewById(R.id.txtUserPoints);
	        	holder.userRankTxt = (TextView) convertView.findViewById(R.id.txtUserRank);
	        	holder.userPointTxt.setBackgroundColor(Color.CYAN);
	        	holder.userPointTxt.getBackground().setAlpha(50);
	        	holder.userRankTxt.setBackgroundColor(Color.CYAN);
	        	holder.userRankTxt.getBackground().setAlpha(50);
	        	// set font size for user row
	        	holder.userPointTxt.setTextSize(SystemProperties.FONT_SIZE);
	    	    holder.userRankTxt.setTextSize(SystemProperties.FONT_SIZE);
	        }
	        
	        convertView.setTag(holder);
	    } else {
			holder = (ViewHolder)convertView.getTag();
		}
	    if(points != null)
	    	fillScoreList(position);
	    
	    return convertView;
	}
	
	private static class ViewHolder {
		private TextView pointTxt;
		private TextView userPointTxt;
		private TextView rankTxt;
		private TextView userRankTxt;
	}
	
	private void fillScoreList(int position){
		if(position<10 && userPlace!=position+1){
	    	holder.rankTxt.setText(String.valueOf(position+1));
	    	holder.pointTxt.setText(String.valueOf(points.get(position)));
	    }
	    else if(userPlace==position+1 || position==11){
	    	holder.userRankTxt.setText(String.valueOf(userPlace));
    		holder.userPointTxt.setText(String.valueOf(points.get(position)));
	    }
	    else if(userPlace>10 && position==10){
	    	holder.rankTxt.setText("...");
    		holder.pointTxt.setText("...");
	    }
	}
	
}
