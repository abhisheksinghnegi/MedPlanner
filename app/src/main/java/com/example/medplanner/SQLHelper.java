package com.example.medplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {
    private final static int VERSION = 1;
    private final static String DATABASE_NAME = "DATA";
    private final static String TABLE_NAME = "MEDS";
    private final static String MEDICINE_NAME = "MEDICINE_NAME";
    private final static String BIO = "BIO";//bio
    private final static String KEY = "ID";
    private final static String KEY1 = "ID1";
    private final static String COLOR = "COLOR";
    private final static String TYPE = "TYPE";
    private final static String Hour = "HOUR";
    private final static String MINUTE = "MINUTE";
    private final static String TIME_TABLE = "TIME";
    private final static String FIRST = "FIRST";
    private final static String LAST = "LAST";
    private final static String TIME1 = "TIME1";
    private final static String TIME2 = "TIME2";
    private final static String TIME3 = "TIME3";
    private final static String TIME4 = "TIME4";
    private final static String TIME5 = "TIME5";
    private final static String BOOLMON = "MONDAY";
    private final static String BOOLTUE = "TUESDAY";
    private final static String BOOLWED = "WEDNESDAY";
    private final static String BOOLTHR = "THRUSDAY";
    private final static String BOOLFRI = "FRIDAY";
    private final static String BOOLSAT = "SATURDAY";
    private final static String BOOLSUN = "SUNDAY";




    SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL = "CREATE TABLE " + TABLE_NAME + "(" + KEY + " INTEGER primary key," + MEDICINE_NAME + " TEXT, " + BIO + " TEXT," + COLOR + " TEXT, " + TYPE + " TEXT, " + Hour + " TEXT,"  + TIME1+ " INTEGER,"  + TIME2+ " INTEGER,"  + TIME3+ " INTEGER,"  + TIME4+ " INTEGER,"  + TIME5+ " INTEGER," + BOOLMON+ " INTEGER,"  + BOOLTUE+ " INTEGER,"  + BOOLWED+ " INTEGER,"  + BOOLTHR+ " INTEGER,"  + BOOLFRI+ " INTEGER,"  + BOOLSAT+ " INTEGER,"  + BOOLSUN+ " INTEGER," + FIRST+ " INTEGER," +LAST + " INTEGER);";
        sqLiteDatabase.execSQL(SQL);

        String sql = "CREATE TABLE " + TIME_TABLE + "(" + KEY1 + " INTEGER primary key," + MINUTE + " TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;

        sqLiteDatabase.execSQL(sql);
        String SQL = "DROP TABLE IF EXISTS " + TIME_TABLE;
        sqLiteDatabase.execSQL(SQL);

    }

    public boolean onInsert(String meds, String bio, String color, String type, String hour,long tim1,long tim2,long tim3,long tim4,long tim5,int mon,int tue,int wed,int thr,int fri,int sat,int sun,int first,int last) {
        Log.d("HELLO added on ", KEY);
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEDICINE_NAME, meds);
        contentValues.put(BIO, bio);
        contentValues.put(COLOR, color);
        contentValues.put(TYPE, type);
        contentValues.put(Hour, hour);
        contentValues.put(TIME1,tim1);
        contentValues.put(TIME2,tim2);
        contentValues.put(TIME3,tim3);
        contentValues.put(TIME4,tim4);
        contentValues.put(TIME5,tim5);
        contentValues.put(BOOLMON,mon);
        contentValues.put(BOOLTUE,tue);
        contentValues.put(BOOLWED,wed);
        contentValues.put(BOOLTHR,thr);
        contentValues.put(BOOLFRI,fri);
        contentValues.put(BOOLSAT,sat);
        contentValues.put(BOOLSUN,sun);
        contentValues.put(FIRST, first);
        contentValues.put(LAST, last);



        if (sqLiteDatabase.insert(TABLE_NAME, null, contentValues) == -1)
            return false;
        else {
            return true;
        }
    }

    public boolean onInsertTime(Long time)//,String HOUR,String MIN)
    {
//        Log.d("HELLO added on ", KEY);
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MINUTE, time);

        if (sqLiteDatabase.insert(TIME_TABLE, null, contentValues) == -1)
            return false;
        else {
            return true;
        }
    }

    public Cursor onGet() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);
        return cursor;
    }

    public Cursor onGetTime() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TIME_TABLE + ";", null);
        return cursor;
    }

    public boolean onDelete(String i) {
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        Log.d("HELLO","RUNNING"+sqLiteDatabase.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(i)}));
//        if(sqLiteDatabase.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(i)})>=0)
//            return true;
//        else
//            return false;


        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int x = sqLiteDatabase.delete(TABLE_NAME, "MEDICINE_NAME=?", new String[]{i});
        Log.d("HELLO", "RUNNING deleted on " + KEY + " " + sqLiteDatabase.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(i)}));
        if (x >= 0)
            return true;
        else
            return false;
    }

    public boolean onDeleteTime(Long j) {
        String n = String.valueOf(j);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int x = sqLiteDatabase.delete(TIME_TABLE, "MINUTE=?", new String[]{n});
        if (x >= 0)
            return true;
        else
            return false;
    }
    public boolean update(String med,String bio, String color, String type, String hour,long tim1,long tim2,long tim3,long tim4,long tim5,int mon,int tue,int wed,int thr,int fri,int sat,int sun,int first,int last)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BIO, bio);
        contentValues.put(COLOR, color);
        contentValues.put(TYPE, type);
        contentValues.put(Hour, hour);
        contentValues.put(TIME1,tim1);
        contentValues.put(TIME2,tim2);
        contentValues.put(TIME3,tim3);
        contentValues.put(TIME4,tim4);
        contentValues.put(TIME5,tim5);
        contentValues.put(BOOLMON,mon);
        contentValues.put(BOOLTUE,tue);
        contentValues.put(BOOLWED,wed);
        contentValues.put(BOOLTHR,thr);
        contentValues.put(BOOLFRI,fri);
        contentValues.put(BOOLSAT,sat);
        contentValues.put(BOOLSUN,sun);
        contentValues.put(FIRST, first);
        contentValues.put(LAST, last);
        int x = sqLiteDatabase.update(TABLE_NAME,contentValues,"MEDICINE_NAME=?",new String[]{med});
        return x <= 0;

    }
//    public int getStart(String name)
//    {
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//
//    }
}



