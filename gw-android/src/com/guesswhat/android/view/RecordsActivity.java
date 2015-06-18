package com.guesswhat.android.view;

import java.util.List;

import com.guesswhat.android.R;
import com.guesswhat.android.game.utils.RecordsAdapter;
import com.guesswhat.android.service.cfg.ServiceFactory;
import com.guesswhat.android.service.rs.face.RecordService;
import com.guesswhat.android.system.utils.SystemProperties;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class RecordsActivity extends Activity{
	
    private List<Integer> points;
    private int userPlace;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        ListView lstRecords;
        lstRecords = (ListView) findViewById(R.id.lstRecords);
        
        getPoints();
        
//        int listSize = points.size();
        int listSize = 10;
        
        points.add(43523);
        points.add(45);
        points.add(5325);
        points.add(23523);
        points.add(63);
        points.add(12);
        points.add(4567);
        points.add(32423);
        points.add(2340);
        points.add(2341);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        points.add(2342);
        userPlace = 23;
        
        if(listSize>=10){
	        if(userPlace<=10)
	        	points = points.subList(0, 10);
	        else if(userPlace>11){ 
	        	points.set(11, points.get(userPlace-1));
	        	points = points.subList(0, 12);
	        } else if(userPlace==11){
	        	points = points.subList(0, 11);
	        }
        }
        RecordsAdapter recordsAdapter = new RecordsAdapter(points, userPlace, this);
        lstRecords.setEnabled(false);
        LayoutInflater inflater = getLayoutInflater();
        lstRecords.addHeaderView(inflater.inflate(R.layout.list_item_header, lstRecords, false));
        lstRecords.setAdapter(recordsAdapter);
        justifyListViewHeightBasedOnChildren(lstRecords);
        

    }
    
    private void getPoints(){
    	RecordService recordService = ServiceFactory.getServiceFactory().getRecordService();
    	points = recordService.findTopRecords();

    	userPlace = recordService.findUserPlace(SystemProperties.USER_ID);
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
    public void onBackPressed()
    {
    	Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
}
