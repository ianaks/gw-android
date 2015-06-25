package com.guesswhat.android.view;

import java.util.ArrayList;
import java.util.List;

import com.guesswhat.android.R;
import com.guesswhat.android.game.utils.MediaPlayerUtils;
import com.guesswhat.android.game.utils.RecordsAdapter;
import com.guesswhat.android.service.cfg.ServiceFactory;
import com.guesswhat.android.service.rs.face.RecordService;
import com.guesswhat.android.sqlite.helper.DatabaseHelper;
import com.guesswhat.android.system.utils.Properties;
import com.guesswhat.android.system.utils.SystemProperties;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RecordsActivity extends Activity {
	
    private List<String> points;
    private int userPlace;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MediaPlayerUtils.play(true);
        setContentView(R.layout.activity_records);        
        buildListView();        
    }
    
    private void buildListView() {
    	getPoints();        
        String totalPoints = DatabaseHelper.getHelper().getProperty(Properties.TOTAL_POINTS.toString());
        if (userPlace > 10) {
	        if (userPlace > 11) {
	        	points.add("...");
	        }
	        points.add(totalPoints);
        }
        
    	ListView lstRecords;
        lstRecords = (ListView) findViewById(R.id.lstRecords);

        RecordsAdapter recordsAdapter = new RecordsAdapter(points, userPlace, this);
        lstRecords.setEnabled(false);
        
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.list_item_header, lstRecords, false);
    	TextView headerPoints = (TextView) convertView.findViewById(R.id.headerPoints);
    	TextView headerRank = (TextView) convertView.findViewById(R.id.headerRank);
    	headerPoints.setTextSize(SystemProperties.FONT_SIZE);
    	headerRank.setTextSize(SystemProperties.FONT_SIZE);
    	
        lstRecords.addHeaderView(convertView);
        lstRecords.setAdapter(recordsAdapter);
        
        justifyListViewHeightBasedOnChildren(lstRecords);
	}

	private void getPoints(){
    	RecordService recordService = ServiceFactory.getServiceFactory().getRecordService();
    	userPlace = recordService.findUserPlace(SystemProperties.USER_ID);
    	List<Integer> topPoints = recordService.findTopRecords();
    	points = new ArrayList<String>();
    	for (Integer topPoint : topPoints) {
    		points.add(String.valueOf(topPoint));
    	}
    }
    
    private void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
    
    @Override
    public void onBackPressed() {
    	finish();
    }
    
}
