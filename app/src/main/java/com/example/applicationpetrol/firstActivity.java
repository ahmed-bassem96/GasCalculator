package com.example.applicationpetrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

public class firstActivity extends AppCompatActivity {
Button placeBtutton1,placeButton2,rideButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        placeBtutton1=findViewById(R.id.placeButton1);
        placeButton2=findViewById(R.id.placeButton2);
        rideButton=findViewById(R.id.rideButton);

//     notification
        Intent intent=new Intent(this,MyReceiver.class);
        PendingIntent pend= PendingIntent.getBroadcast(this,0,intent,0);
        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+12000,AlarmManager.INTERVAL_DAY,pend);

    }

    public void twoPlaces(View view) {
        Intent in=new Intent(this,MainActivity.class);
        startActivity(in);

    }

    public void manyplaces(View view) {
        Intent in=new Intent(this,MainActivity2.class);
        startActivity(in);
    }

    public void myrides(View view) {
        Intent in=new Intent(this,rideActivity.class);
        startActivity(in);
    }
}