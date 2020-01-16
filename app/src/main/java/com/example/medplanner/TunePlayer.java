package com.example.medplanner;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class TunePlayer extends Service {
    static final String Tag = "MUSIC";
    Calendar cal,pal;

    MediaPlayer mediaPlayer;
    @Override
    public IBinder onBind(Intent intent) {
        return null;


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent i = new Intent(this,TunePlayer.class);


        Log.d("bitch","STARTED");
        final SimpleDateFormat sdf = new SimpleDateFormat("h:mm a",Locale.ENGLISH);
        cal = Calendar.getInstance();
        pal = Calendar.getInstance();
        pal.set(Calendar.MINUTE,cal.get(Calendar.MINUTE)+1);
        i.putExtra("hello","hello");

        PendingIntent content = PendingIntent.getActivity(this,100000,new Intent(this,CustomAlarmScreen.class),0);
        Notification notification = new NotificationCompat.Builder(this,App.CHANNEL_2_ID)//used more above api 21
                .setContentTitle("ALARM")
                .setContentText(sdf.format(cal.getTime()))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(content)
                .build();

        startForeground(1,notification);
        mediaPlayer= MediaPlayer.create(this,R.raw.awp);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();



        return START_NOT_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null)
             mediaPlayer.release();
        mediaPlayer = null;

    }
}
