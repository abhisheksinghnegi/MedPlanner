package com.example.medplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Contact_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
