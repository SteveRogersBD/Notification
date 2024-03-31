package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "My_Channel";
    private static final int NOTIFICATION_ID = 100;
    Button btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        btn = findViewById(R.id.notify_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNotification();
            }
        });

    }
    public void makeNotification() {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_IMMUTABLE);

// Create an intent for the button action
        Intent buttonIntent = new Intent(this, MainActivity2.class);
        buttonIntent.putExtra("notificationId", NOTIFICATION_ID);
        PendingIntent buttonPendingIntent = PendingIntent.getActivity(this, 0, buttonIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),
                CHANNEL_ID);
        builder.setSmallIcon(R.drawable.cap)
                .setContentTitle("New Message")
                .setContentText("Hi this is your captain Steve Rogers.")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                // Add a button to the notification
                .addAction(R.drawable.send, "Button Text", buttonPendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Creating a channel for notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nc = nm.getNotificationChannel(CHANNEL_ID);
            if (nc == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                nc = new NotificationChannel(CHANNEL_ID, "Another new", importance);
                nc.setLightColor(Color.GREEN);
                nc.enableVibration(true);
                nm.createNotificationChannel(nc);
            }
        }
        nm.notify(NOTIFICATION_ID, builder.build());
    }

}
