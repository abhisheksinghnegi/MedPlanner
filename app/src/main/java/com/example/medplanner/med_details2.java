package com.example.medplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class med_details2 extends AppCompatActivity {
SQLHelper sqlHelper = new SQLHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_details2);
    }
}
