package com.example.applicationpetrol;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {


    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
       // Toast.makeText(context, "receiver called", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(context, firstActivity.class);
        PendingIntent pend=PendingIntent.getActivity(context,0,intent1,0);
        createNotificationChannel();
        NotificationManager manager= (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"com.petrolApp");
        builder.setTicker("Do you want to calculate the cost of today's rides ?").setSmallIcon(R.drawable.logo)
                .setContentTitle("Petroleum calculator").setContentText("Do you want to calculate the cost of today's rides ?")
                .setContentIntent(pend)
                .setAutoCancel(true);
        Notification notification= builder.build();
        manager.notify(1,notification);
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name ="com.petrolApp";
            String description = "com.petrolApp";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("com.petrolApp", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}