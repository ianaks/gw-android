package com.guesswhat.android.view;

import com.guesswhat.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Iana on 27.04.2015.
 */
public class RecordsActivity extends Activity{
    String[] names = { "1", "2", "3", "4", "5°", "6",
            "7", "8", "9", "10", "11" };

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        ListView lstRecords;
        lstRecords = (ListView) findViewById(R.id.lstRecords);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item_records, names);

        lstRecords.setAdapter(adapter);

    }
}
