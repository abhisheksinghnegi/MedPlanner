package com.example.medplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.lang.reflect.Array;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class meddetails extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    Button button, set_remainder;
    FloatingActionButton mon, tue, wed, thr, fri, sat, sun;
    EditText medname, bio;
    SQLHelper sqlHelper;
    LinearLayout medicine_color, medicine_types;
    AlertDialog.Builder builder;
    Intent intent ;
    String getColor = "#ecf0f1",med_Type="Capsule";
    ImageView color;
    int number_of_alarms;
    RecyclerView recyclerView;
    TextView what_med;
    int checkeditem,startAlarm,prev,add;
    PendingIntent pend1;
    AlertDialog alertDialog,want_back;
    Intent alarmintent;
    Calendar calendar2 = Calendar.getInstance();
    RemainderAdapter adapter;
    ArrayList<Long> medsList = new ArrayList<>();
    int itemchecked;
    boolean bmon,btue,bwed,bthr,bfri,bsat,bsun;
    SharedPreferences sh;
    SharedPreferences.Editor edit;
    ArrayList<Long> dummy = new ArrayList<>();
    String timeTell="";
    SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
    Cursor cursor;
    //    Bundle b = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meddetails);
        button = findViewById(R.id.button);
        final String[] arr = {"Ampoule", "Capsule", "Drop", "Gram", "Injection", "Pills", "Puff", "Tablet", "Unit"};
        button.setOnClickListener(this);
        what_med = findViewById(R.id.what_medicine);
        set_remainder = findViewById(R.id.remainder);
        mon = findViewById(R.id.monday);
        tue = findViewById(R.id.tuesday);
        wed = findViewById(R.id.wednesday);
        thr = findViewById(R.id.thrusday);
        fri = findViewById(R.id.friday);
        sat = findViewById(R.id.saturady);
        sun = findViewById(R.id.sunday);
        mon.setOnClickListener(this);
        tue.setOnClickListener(this);
        wed.setOnClickListener(this);
        thr.setOnClickListener(this);
        fri.setOnClickListener(this);
        sat.setOnClickListener(this);
        sun.setOnClickListener(this);
        recyclerView = findViewById(R.id.remainderRecycle);
        set_remainder.setOnClickListener(this);
        sqlHelper = new SQLHelper(this);
        cursor = sqlHelper.onGet();
        medname = findViewById(R.id.text1);
        bio = findViewById(R.id.text2);
        medicine_color = findViewById(R.id.medicine_color);
        medicine_types = findViewById(R.id.medicine_types);
        color = findViewById(R.id.color);
        intent = new Intent(this,Alarmbroadcast.class);
        alarmintent = new Intent(this,Alarmbroadcast.class);
        SharedPreferences sharedPreferences = getSharedPreferences("ALARMS",MODE_PRIVATE);
        if(getIntent().getIntExtra("MODE",0)==1)
        {
            while(cursor.moveToNext()) {
                if(cursor.getString(cursor.getColumnIndex("MEDICINE_NAME")).equals(getIntent().getStringExtra("MEDICINE_N"))) {
                    medname.setText(cursor.getString(cursor.getColumnIndex("MEDICINE_NAME")));
                    medname.setEnabled(false);
                    bio.setText(cursor.getString(cursor.getColumnIndex("BIO")));
                    color.setBackgroundColor(Color.parseColor(cursor.getString(cursor.getColumnIndex("COLOR"))));
                    what_med.setText(cursor.getString(cursor.getColumnIndex("TYPE")));

                    if(cursor.getInt(cursor.getColumnIndex("TIME1"))!=0)
                    {
                        medsList.add(cursor.getLong(cursor.getColumnIndex("TIME1")));
                    }
                    if(cursor.getInt(cursor.getColumnIndex("TIME2"))!=0)
                    {
                        medsList.add(cursor.getLong(cursor.getColumnIndex("TIME2")));
                    }
                    if(cursor.getInt(cursor.getColumnIndex("TIME3"))!=0)
                    {
                        medsList.add(cursor.getLong(cursor.getColumnIndex("TIME3")));
                    }
                    if(cursor.getInt(cursor.getColumnIndex("TIME4"))!=0)
                    {
                        medsList.add(cursor.getLong(cursor.getColumnIndex("TIME4")));
                    }
                    if(cursor.getInt(cursor.getColumnIndex("TIME5"))!=0)
                    {
                        medsList.add(cursor.getLong(cursor.getColumnIndex("TIME5")));
                    }

                    bmon = inttoboolean(cursor.getInt(cursor.getColumnIndex("MONDAY")));
                    btue = inttoboolean(cursor.getInt(cursor.getColumnIndex("TUESDAY")));
                    bwed = inttoboolean(cursor.getInt(cursor.getColumnIndex("WEDNESDAY")));
                    bthr = inttoboolean(cursor.getInt(cursor.getColumnIndex("THRUSDAY")));
                    bfri = inttoboolean(cursor.getInt(cursor.getColumnIndex("FRIDAY")));
                    bsat = inttoboolean(cursor.getInt(cursor.getColumnIndex("SATURDAY")));
                    bsun = inttoboolean(cursor.getInt(cursor.getColumnIndex("SUNDAY")));
                    if(bmon)
                        mon.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));
                    if(btue)
                        tue.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));
                    if(bwed)
                        wed.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));
                    if(bthr)
                        thr.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));
                    if(bfri)
                        fri.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));
                    if(bsat)
                        sat.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));
                    if(bsun)
                        sun.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));
                }
            }
        }
        dummy.add(0L);
        dummy.add(0L);
        dummy.add(0L);
        dummy.add(0L);
        dummy.add(0L);
        number_of_alarms = sharedPreferences.getInt("numberOfAlarm",0);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("MEDICINE NAME(s)")
                .setCancelable(true)
                .setSingleChoiceItems(arr, checkeditem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemchecked = i;
                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkeditem = itemchecked;
                        what_med.setText(arr[itemchecked]);
                        med_Type = arr[itemchecked];

                    }
                })
                .setNegativeButton("LEAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);
        alertbuilder.setTitle("DISCARD ALL CHANGES")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(meddetails.this,Primary_Screen.class));
                            finish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                         want_back.dismiss();
                        }
                    })
                .setCancelable(false);
        want_back = alertbuilder.create();
        alertDialog = builder.create();
        medicine_types.setOnClickListener(this);
        medicine_color.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RemainderAdapter(medsList, this);
        recyclerView.setAdapter(adapter);


        //adapter.notifyDataSetChanged();


//        Cursor cursor = sqlHelper.onGetTime();
//        while (cursor.moveToNext())
//        {
//            Log.d("BOSDK",String.valueOf(cursor.getLong(cursor.getColumnIndex("MINUTE"))));
//            medsList.add(cursor.getLong(cursor.getColumnIndex("MINUTE")));
//        }

    }

    @Override
    public void onBackPressed() {
        want_back.show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.monday:
                if(!bmon)
                {
                    bmon = true;
                    mon.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));

                }
                else
                {
                    bmon = false;
                    mon.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EED8D8D8")));

                }
                break;
            case R.id.tuesday:
                if(!btue)
                {
                    btue = true;
                    tue.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));

                }
                else
                {
                    btue = false;
                    tue.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EED8D8D8")));

                }
                break;
            case R.id.wednesday:
                if(!bwed)
                {
                    bwed = true;
                    wed.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));

                }
                else
                {
                    bwed = false;
                    wed.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EED8D8D8")));

                }
                break;
            case R.id.thrusday:
                if(!bthr)
                {
                    bthr = true;
                    thr.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));

                }
                else
                {
                    bthr = false;
                    thr.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EED8D8D8")));

                }
                break;
            case R.id.sunday:
                if(!bsun)
                {
                    bsun = true;
                    sun.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));

                }
                else
                {
                    bsun = false;
                    sun.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EED8D8D8")));

                }
                break;
            case R.id.friday:
                if(!bfri)
                {
                    bfri = true;
                    fri.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));

                }
                else
                {
                    bfri = false;
                    fri.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EED8D8D8")));

                }
                break;
            case R.id.saturady:
                if(!bsat)
                {
                    bsat = true;
                    sat.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE7E7E7E")));

                }
                else
                {
                    bsat = false;
                    sat.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EED8D8D8")));

                }
                break;

            case R.id.remainder:
                if (medsList.size() < 5) {
                    DialogFragment dialogFragment = new TImeFragment();
                    dialogFragment.show(getSupportFragmentManager(), "TAG");
                } else
                    Toast.makeText(meddetails.this, "MORE ALARMS CANNOT BE SET", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button:


                    String string1 = medname.getText().toString();
                    String string2 = bio.getText().toString();
                    startAlarm = number_of_alarms;
                    for (int j = 0; j < medsList.size(); j++) {
                        timeTell += sdf.format(medsList.get(j)) + "  ";

                        if (bmon)
                            alarmRepeat(2, medsList.get(j));
                        if (btue)
                            alarmRepeat(3, medsList.get(j));
                        if (bwed)
                            alarmRepeat(4, medsList.get(j));
                        if (bthr)
                            alarmRepeat(5, medsList.get(j));
                        if (bfri)
                            alarmRepeat(6, medsList.get(j));
                        if (bsat)
                            alarmRepeat(7, medsList.get(j));
                        if (bsun)
                            alarmRepeat(1, medsList.get(j));


                    }
                    medsList.addAll(dummy);
                    sh = getSharedPreferences("ALARMS", MODE_PRIVATE);
                    edit = sh.edit();
                    edit.putInt("numberOfAlarm", number_of_alarms);
                    edit.apply();
                    if(getIntent().getIntExtra("MODE",0)==1)
                    {
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        Cursor cursor1 = sqlHelper.onGet();
                        while (cursor1.moveToNext()) {

                            if (getIntent().getStringExtra("MEDICINE_N").equals(cursor1.getString(cursor1.getColumnIndex("MEDICINE_NAME")))) {
                                prev = cursor1.getInt(cursor1.getColumnIndex("FIRST"));
                                add = cursor1.getInt(cursor1.getColumnIndex("LAST"));
                                break;
                            }

                        }
                        Log.d("PREV", String.valueOf(prev + add));
                        for (int i = prev; i < add; i++) {
                            pend1 = PendingIntent.getBroadcast(meddetails.this, i, intent, 0);
                            if (alarmManager != null)
                                alarmManager.cancel(pend1);
                            Log.d("ALARM", "ALARMCANCELLED FOR " + i);

                        }

                        sqlHelper.update(getIntent().getStringExtra("MEDICINE_N"), string2, getColor, med_Type, timeTell, medsList.get(0), medsList.get(1), medsList.get(2), medsList.get(3), medsList.get(4), booleantoint(bmon), booleantoint(btue), booleantoint(bwed), booleantoint(bthr), booleantoint(bfri), booleantoint(bsat), booleantoint(bsun), startAlarm, number_of_alarms);
                            Intent intent = new Intent(meddetails.this, Primary_Screen.class);
                            startActivity(intent);
                            finish();


                    }
                    else {
                        if (sqlHelper.onInsert(string1, string2, getColor, med_Type, timeTell, medsList.get(0), medsList.get(1), medsList.get(2), medsList.get(3), medsList.get(4), booleantoint(bmon), booleantoint(btue), booleantoint(bwed), booleantoint(bthr), booleantoint(bfri), booleantoint(bsat), booleantoint(bsun), startAlarm, number_of_alarms)) {
                            Toast.makeText(meddetails.this, "PASSED", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(meddetails.this, Primary_Screen.class);
                            startActivity(intent);
                            finish();
                        } else
                            Toast.makeText(meddetails.this, "FAILED", Toast.LENGTH_SHORT).show();
                    }
                break;

            case R.id.medicine_color:
                ColorPickerDialogBuilder
                        .with(meddetails.this)
                        .setTitle("Choose color")
                        .initialColor(R.color.grey)
                        .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                                Toast.makeText(meddetails.this, "#" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                getColor = "#" + Integer.toHexString(selectedColor);
                                color.setBackgroundColor(Color.parseColor(getColor));
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
                break;

            case R.id.medicine_types:
                alertDialog.show();
                break;


        }



    }
    int booleantoint(boolean x)
    {
        if(x)
        {
            return 1;
        }
        else
            return 0;
    }

    boolean inttoboolean(int x)
    {
        return x == 1;
    }

    void alarmRepeat(int week,long time)
    {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        Log.d("xforce",calendar.getTime().toString());
        Calendar calen = Calendar.getInstance();
//        Log.d("xforce","calen is before"+calen.getTime().toString());
        calen.set(Calendar.DAY_OF_WEEK,week);
        calen.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY));
        calen.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE));
        calen.set(Calendar.SECOND,0);
//        Log.d("xforce","calen is after"+calen.getTime().toString());
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intent.putExtra("MedName",medname.getText().toString());
        intent.putExtra("MedBio",bio.getText().toString());
        intent.putExtra("MedColor",getColor);
        intent.putExtra("MedType",med_Type);
        PendingIntent pend = PendingIntent.getBroadcast(meddetails.this,number_of_alarms,intent,0);
        if(calen.before(Calendar.getInstance()))
        {
            calen.add(Calendar.DATE,7);
        }
//        Log.d("xforce","calen is after adding"+calen.getTime().toString());
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calen.getTimeInMillis(),1000*60*60*24*7,pend);
        Log.d("ALARM SET FOR " , String.valueOf(number_of_alarms));
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calen.getTimeInMillis(),pend);
        number_of_alarms++;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, i);
        cal.set(Calendar.MINUTE, i1);
        cal.set(Calendar.SECOND, 0);
        medsList.add(cal.getTimeInMillis());
        adapter.notifyDataSetChanged();
        calendar2 = cal;
        if (sqlHelper.onInsertTime(cal.getTimeInMillis())) {
            Toast.makeText(meddetails.this, "PASSED", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(meddetails.this, "FAILED", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        if(want_back!=null)
        {
            want_back.dismiss();
            want_back=null;
        }
        super.onDestroy();
    }
}

