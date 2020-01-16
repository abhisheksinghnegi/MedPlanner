package com.example.medplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

public class Mydbhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_aqua";
    private static final String TABLE_NAME = "user";
    private static final String KEY = "ID";
    private static final String USER_NAME = "person";
    private static final String USER_MAIL = "mail";
    private static final String USER_PHONE = "phone";
    private static final String USER_PASS= "password";

    Mydbhelper(Context context) {

        super(context,DATABASE_NAME,null,6);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table user(ID INTEGER primary key autoincrement,person TEXT,mail TEXT,phone TEXT,password TEXT );";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
    }

    public boolean insertdata(String name,String mail,String phone,String pass)
    {
        SQLiteDatabase sd = this.getWritableDatabase();
        ContentValues cn = new ContentValues();
        cn.put(USER_NAME,name);
        cn.put(USER_MAIL,mail);
        cn.put(USER_PHONE,phone);
        cn.put(USER_PASS,pass);
        long s = sd.insert(TABLE_NAME,null,cn);
        if (s==-1)
        {
            return false;
        }
        else
            return true;
    }
    public boolean check(String username,String password)
    {
        SQLiteDatabase sd = this.getWritableDatabase();

        Cursor cursor = sd.rawQuery("Select * from "+TABLE_NAME,null);
        while(cursor.moveToNext())
        {
            String s = cursor.getString(cursor.getColumnIndex("mail"));
            String d = cursor.getString(cursor.getColumnIndex("password"));
            if(s.equals(username)&d.equals(password))
                return true;
        }
        return false;
    }

}
