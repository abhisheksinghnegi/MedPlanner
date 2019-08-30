package com.example.medplanner;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class Primary_Screen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton fab;

RecyclerView recyclerView;
ArrayList<MODEL> arrayList = new ArrayList<>();
Asnadapter asnadapter;
SQLHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary__screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //write code
        fab = findViewById(R.id.add_button);
        sqlHelper = new SQLHelper(Primary_Screen.this);
        Cursor cursor = sqlHelper.onGet();
        while (cursor.moveToNext())
        {
            MODEL m = new MODEL();
            m.setAlarm(cursor.getString(cursor.getColumnIndex("HOUR")));
            m.setMedname(cursor.getString(cursor.getColumnIndex("MEDICINE_NAME")));
            m.setColor(cursor.getString(cursor.getColumnIndex("COLOR")));
            m.setMedType(cursor.getString(cursor.getColumnIndex("TYPE")));
            m.setbIO(cursor.getString(cursor.getColumnIndex("BIO")));
            arrayList.add(m);
        }
        recyclerView = findViewById(R.id.recycler);
        Log.d("YES", String.valueOf(arrayList.size()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        asnadapter = new Asnadapter(this,arrayList);
        recyclerView.setAdapter(asnadapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Primary_Screen.this,meddetails.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.primary__screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(this,meddetails.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent();
            intent.setAction("com.abhishek.singh.negi");
            intent.setComponent(new ComponentName("com.example.asnbroadcastreciever","com.example.asnbroadcastreciever.Mybroadcast"));//very important line moving from one activity to another just like normal intent
            sendBroadcast(intent);//sending broadcast at a click

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
