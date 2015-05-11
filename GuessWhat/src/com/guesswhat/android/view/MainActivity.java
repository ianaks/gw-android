package com.guesswhat.android.view;

import com.guesswhat.android.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_layout();
    }

    private void init_layout(){
        final Context context = this.getApplicationContext();
        ImageView buttonRecords = (ImageView)findViewById(R.id.button_records);
        buttonRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecordsActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        ImageView buttonExit = (ImageView)findViewById(R.id.button_exit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                MainActivity.this);

                alertDialogBuilder.setMessage("Are you sure you want to exit game?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        MainActivity.this.finish();
                                    }
                                })
                        .setNegativeButton("No",
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog,
			                                        int id) {
			                        dialog.cancel();
			                    }
			                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }
}
