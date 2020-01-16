package com.example.medplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomAlarmScreen extends AppCompatActivity {
    Button button;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_alarm_screen);
        button = findViewById(R.id.button2);
        txt = findViewById(R.id.textView3);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.ENGLISH);

        txt.setText(sdf.format(Calendar.getInstance().getTime()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),TunePlayer.class);
                stopService(i);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
