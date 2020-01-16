package com.example.medplanner;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Alarmbroadcast extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"ALARM TRIGGERED",Toast.LENGTH_SHORT).show();
        Log.d("BOSDKs","ALARM TRIGGERED");
        Calendar cal = Calendar.getInstance();
        Uri uri = RingtoneManager.getDefaultUri(Notification.DEFAULT_SOUND);
        PendingIntent content = PendingIntent.getActivity(context,5000,new Intent(context,Primary_Screen.class),0);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.vector);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, App.CHANNEL_1_ID);//making notification builder
        builder.setSmallIcon(R.drawable.ic_friday)
                .setLargeIcon(largeIcon)
                .setContentIntent(content)//the activity that will be shown on click of the notification
                .setAutoCancel(true)//cancel on click
                .setColor(Color.parseColor(intent.getStringExtra("MedColor")))
                .setContentTitle(intent.getStringExtra("MedName"))
                .setContentText(intent.getStringExtra("MedBio"))
                .setSound(uri)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(intent.getStringExtra("MedBio"))
                        .setSummaryText(intent.getStringExtra("MedType"))
                        .setBigContentTitle(intent.getStringExtra("MedName")));
                //used to set Action bar launcher icon , string that will be shown and pending intent
        //which points to the Broadcast class onclick the onrecieve method will be called

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context); //below oreo this is used
        notificationManagerCompat.notify(2, builder.build());//builder is set
        Intent x = new Intent(context,CustomAlarmScreen.class);
        context.startActivity(x);
        Intent i = new Intent(context,TunePlayer.class);
        i.putExtra("hello","hell");
        context.startForegroundService(i);

    }
}
