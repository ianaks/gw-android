package com.guesswhat.android.view;

import java.util.List;

import com.guesswhat.android.R;
import com.guesswhat.android.game.utils.RecordsAdapter;
import com.guesswhat.android.service.cfg.ServiceFactory;
import com.guesswhat.android.service.rs.face.RecordService;
import com.guesswhat.android.system.utils.SystemProperties;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class RecordsActivity extends Activity{
	
    private List<Integer> points;
    private int userPlace;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        ListView lstRecords;
        lstRecords = (ListView) findViewById(R.id.lstRecords);
        
        getPoints();
        
        int listSize = points.size();
        
        if(listSize>=10){
	        if(userPlace<10)
	        	points = points.subList(0, 9);
	        else{ 
	        	points.add(11, points.get(userPlace));
	        	points = points.subList(0, 11);
	        }
        }
        RecordsAdapter recordsAdapter = new RecordsAdapter(points, userPlace, this);
        lstRecords.setAdapter(recordsAdapter);
        

    }
    
    private void getPoints(){
    	RecordService recordService = ServiceFactory.getServiceFactory().getRecordService();
    	points = recordService.findTopRecords();

    	userPlace = recordService.findUserPlace(SystemProperties.USER_ID);
    }
    
}
